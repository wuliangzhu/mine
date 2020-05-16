package com.mye.mine.service;

import com.alibaba.fastjson.JSONObject;
import com.mye.mine.mapper.HistoryMapper;
import com.mye.mine.utils.DateUtil;
import com.mye.mine.utils.ExcelUtil;
import com.mye.mine.utils.HttpUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class ElecService {

    @Autowired
    private HistoryMapper saveMapper;

    /**
     * 查询指定日期的每一天的数据
     * @param beginDay
     * @param endDay
     */
    public void queryUseElecEveryDay(String beginDay, String endDay, String tidstr) {
        List<String> days = DateUtil.days(DateUtil.parse(beginDay), DateUtil.parse(endDay));
        String begin = null;
        String end = null;

        for(String day : days) {
            begin = end;
            end = day + " 12:00:00";
            if (begin != null && end != null) {
                this.sendPost(begin, end, tidstr);
            }
        }

        return;
    }

    /**
     * 查询指定日期区间的数据
     *
     * @param beginDay
     * @param endDay
     */
    public void queryUseElecByRangeDay(String beginDay, String endDay, String tidstr) {

        this.sendPost(beginDay, endDay, tidstr);

        return;
    }

    public void queryRuntimeEveryDay(String beginDay, String endDay, String[] tids) {
        List<String> days = DateUtil.days(DateUtil.parse(beginDay), DateUtil.parse(endDay));
        for(String day : days) {
            this.queryRuntimeByRangeDay(day, day, tids);
        }
    }

    public void queryRuntimeByRangeDay(String beginDay, String endDay, String[] tids) {
        StringBuffer sb = new StringBuffer();
        for (String tid : tids) {
            sb.append('"');
            sb.append(tid);
            sb.append('"');
            sb.append(',');
        }

        Map<String, Object> params = new HashMap<>();
        params.put("begin", beginDay);
        params.put("end", endDay);
        params.put("tids", sb.substring(0, sb.length() - 1));

        // device_id, t_id, sum(runtime_12), name, hotel_name
        List<Map<String, Object>> data = this.saveMapper.findRuntimeByDay(params);

        try {
            ExcelUtil.createExcel(beginDay+ "_" + endDay + "运行时长.xls",
                    new String[]{"DEVICEID", "TID", "运行时长", "房间名", "楼栋名"},
                    new String[]{"device_id", "t_id", "sum(runtime_12)", "name", "hotel_name"},
                    data);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * "ac_name":"504",
     * "useElec":12.400002,
     * "hotelName":"教师宿舍",
     * "tid":"EC-B1-00-00-00-00-D3-A5",
     * "roomName":"504",
     * "hotelGid"
     * @param beginTime
     * @param endTime
     * @return
     */
    private String sendPost(String beginTime, String endTime, String tidstr) {
        Map<String, Object> obj = new HashMap<String, Object>();

        String url = "elec_findAcDeviceByTids.jhtml";

        obj.put("token", HttpUtil.TOKEN);
        obj.put("beginTime", beginTime);
        obj.put("endTime", endTime);
        obj.put("tidstr", tidstr);

        String result = HttpUtil.sendPostHttpRequest(url, obj);
        JSONObject jso = JSON.parseObject(result);
        List<HashMap> list = JSON.parseArray(jso.getString("list"), HashMap.class);

        List<Map<String, Object>> data = new LinkedList<>();
        for(HashMap hm : list) {
            data.add(hm);
        }

        try {
            ExcelUtil.createExcel(beginTime + "_" + endTime + "用电量.xls",
                    new String[]{"设备名字", "用电量", "楼栋名", "TID", "房间名", "楼栋ID"},
                    new String[]{"ac_name", "useElec", "hotelName", "tid", "roomName", "hotelGid"},
                    data);
        }catch (Exception e) {
            e.printStackTrace();
        }

        return "success";
    }
}
