package com.mye.mine.controller;

import com.mye.mine.service.HistoryService;
import com.mye.mine.utils.DateUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class SaveController {
    private static Logger logger = LoggerFactory.getLogger(SaveController.class);

    @Autowired
    HistoryService historyService;

    @GetMapping("/findDeviceByRoom")
    public Object findDeviceByRoom(String hotelName, String roomName){
        HashMap ret = new HashMap();
        ret.put("key", "value");

        return historyService.findTidByParams(ret);
    }

    /**
     * 根据日期查询出 电压为0的设备列表
     * @param start
     * @param end
     * @return
     */
    @ApiOperation("根据日期查询出电压为0的设备列表")
    @GetMapping("/findTidByParams")
    public Object findTidByParams(@RequestParam("start") String start, @RequestParam("end") String end){
        LocalDate startDate = DateUtil.parse(start);
        LocalDate endDate = DateUtil.parse(end);

        List<Map<String, Object>> ret = new LinkedList<>();
        LocalDate current = startDate;
        while (!current.isAfter(endDate)) {

            String str = DateUtil.format(current);
            HashMap param = new HashMap();
            param.put("dstr", str);

            logger.info("start query:" + str);
            List<Map<String, Object>> data = historyService.findTidByParams(param);
            logger.info("end query:" + str);

            List<Object> tids = data.stream().map( e -> e.get("tid")).collect(Collectors.toList());

            Map tmp = new HashMap();
            tmp.put("date", str);
            tmp.put("tids", tids);
            ret.add(tmp);

            current = DateUtil.nextDay(current);
        }

       return ret;
    }

    /**
     * 查询指定设备最近的登录时间
     *
     * @param params GetMapping 不支持RequestBody hotel_name room_names
     * @return
     */
    @ApiOperation("根据给出的酒店和房间信息查询出所有的登录时间 [{hotel_name:'', room_names: ''}, {hotel_name:'', room_names:''}]")
    @PostMapping("/findAllLastReportTime")
    public Object findLastReportTime(@RequestBody List<Map<String, String>> params) {
        return historyService.findAllLastReportTimeByHotelRoomName(params);
    }

    /**
     * 1 扫描所有设备；
     * 2 比较最近登录时间超过指定参数的设备
     * 3 结果按离线长短排序；
     * 4 去掉无效设备：
     * 5 设备mye_tss
     * @param offlineTimes 时间 分钟计算
     * @return
     */
    @ApiOperation("查询平台所有离线设备")
    @PostMapping("/findOfflineTerminal")
    public Object findOfflineTerminal(@RequestParam(name = "offline", defaultValue = "15") int offlineTimes) {
        return historyService.findOfflineTerminal(offlineTimes);
    }

    /**
     * 查出所有处于关机的设备
     * @param offlineTimes 时间 分钟计算
     * @return
     */
    @ApiOperation("查询平台所有关机的空调")
    @PostMapping("/findShutdownAir")
    public Object findShutdownAir(@RequestParam(name = "offline", defaultValue = "15") int offlineTimes) {
        return historyService.findShutdownAir(offlineTimes);
    }


    @ApiOperation("excel导出")
    @RequestMapping(value = "downLoadStuInfoExcel", produces = "text/html;charset=UTF-8")
    public void downLoadStuInfoExcel(HttpServletResponse response, HttpServletRequest request) throws Exception {
        JSONObject rt = new JSONObject();
        //json对象，用来记录下载状态值，写入log中，也可以把状态值返回到前台，这一部分可有可无。
        rt.put("status", "1");
        rt.put("message", "");
        rt.put("result", "");
        //学生新建excel下载模板保存地址从配置文件中读取
        String folderPath = ResourceBundle.getBundle("systemconfig").getString("stuExcelDownLoadPath") + File.separator + "stuTemplateExcel.xlsx";
        File excelFile = new File(folderPath);
        //判断模板文件是否存在
        if (!excelFile.exists() || !excelFile.isFile()) {
            rt.put("status", "0");
            rt.put("message", "模板文件不存在");

            //  return rt.toJSONString();
        }
        //文件输入流
        FileInputStream fis = null;
        XSSFWorkbook wb = null;
        //使用XSSFWorkbook对象读取excel文件
        try {
            fis = new FileInputStream(excelFile);
            wb = new XSSFWorkbook(fis);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
            rt.put("status", "0");
            rt.put("message", "模板文件读取失败");
            // return rt.toJSONString();
        }
        //设置contentType为vnd.ms-excel
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setCharacterEncoding("utf-8");

        // 对文件名进行处理。防止文件名乱码，这里前台直接定义了模板文件名，所以就不再次定义了
        //String fileName = CharEncodingEdit.processFileName(request, "stuTemplateExcel.xlsx");
        // Content-disposition属性设置成以附件方式进行下载
        response.setHeader("Content-disposition", "attachment;filename=stuTemplateExcel.xlsx");
        //调取response对象中的OutputStream对象
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
            rt.put("status", "0");
            rt.put("message", "模板文件下载失败");
        }
        logger.info("下载学生模板文件结果:" + rt.toString());
        //return rt.toJSONString();
    }
}
