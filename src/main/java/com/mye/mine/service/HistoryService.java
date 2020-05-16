package com.mye.mine.service;

import com.mye.mine.entity.History;
import com.mye.mine.mapper.HistoryMapper;
import com.mye.mine.utils.DateUtil;
import com.mye.mine.utils.ExcelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HistoryService {
    private static Logger logger = LoggerFactory.getLogger(HistoryService.class);

    public static DateTimeFormatter YEAR_MONTH_DAY = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final int OFFLINE_TIME = 60 * 60 * 1000;
    @Autowired
    HistoryMapper historyMapper;


    public List<Map<String, Object>> findTidByParams(Map<String, Object> params) {
        return historyMapper.findTidByParams(params);
    }

    public List<History> findHistoryByParams(Map<String, Object> params) {
        return historyMapper.findHistoryByTid(params);
    }

    /**
     * 每一行的格式 14137,V28 莎翁楼,732,EC-B1-00-00-00-00-C9-31,112,2020-05-12 10:29:13
     * @param tidList
     * @return
     */
    public void  findLastOfflineByTidList(List<String> tidList) {
        List<String> data = tidList;//List.of(tidList.get(0));
        data.parallelStream().forEach(e -> {
            String[] params = e.split(",");
            String tid = params[3];
            String from = params[5];

            History history = findLastOfflineByTid(from, tid);
            Date d = null;
            String str = null;
            if (history != null) {
                d = history.getCollectTime();
                str = df.format(d);
            }
            System.out.println(e + "," + str);
        });
    }

    public History findLastOfflineByTid(String from, String tid) {
        LocalDate start = LocalDate.parse(from.split(" ")[0]);
        boolean found = false;
        History last = null; // 上一条记录
        History current = null; // 当前记录 如果当前记录和上一条记录的时间大于30分钟，我们就认为他离线了

        while (!found && start.getYear() > 2018) {
            Map para = Map.of("day", start.format(YEAR_MONTH_DAY), "t_id", tid);
            List<History> historyList = this.findHistoryByParams(para);
            // 比较两条记录时间差

            for(History history : historyList) {
                if (last == null) {
                    last = history;
                    continue;
                }else {
                    current = history;

                    // diff = current.collecttime - last.collectime
                    long diff = last.getCollectTime().getTime() - current.getCollectTime().getTime();
                    if (diff > OFFLINE_TIME) {
                        // 找到了break;
//                        logger.info("found diff min is {}", diff / ( 60 * 1000 ));
                        found = true;
                        break;
                    }else {
//                        logger.info("diff min is {}", diff / ( 60 * 1000 ));
                    }


                    last = current;
                }
            }

            if (found) {
                break;
            }else {
                // 没有找到在上一天查找
                start = start.plusDays(-1);
            }
        }

        // 返回结果
        return current;
    }
    /**
     * 查询一个酒店和房间的最后登录时间
     * @param params hotel_name room_names
     * @return
     */
    public List<Map<String, Object>> findLastReportTime(Map<String, String> params) {
        return historyMapper.findLastReportTime(params);
    }

    /**
     * 根据酒店和房间名字批量查询数据
     * @param params
     * @return
     */
    public List<Map<String, Object>> findAllLastReportTimeByHotelRoomName(List<Map<String, String>> params) {
        List tmp = params.parallelStream().map(e -> {
            return findLastReportTime(e);
        }).collect(Collectors.toList());

        List<Map<String, Object>> ret = new LinkedList<>();
        ret.addAll(tmp);

        return ret;
    }

    /**
     * 传入一个时间查出所有 大于指定延迟没有传回的数据
     * findOfflineTerminal
     * @return
     */
    public List<Map<String, Object>> findOfflineTerminal(int offline) {
        return this.historyMapper.findOfflineTerminal(offline);
    }

    /**
     * 传入一个时间查出所有 大于指定延迟没有传回数据的关机空调
     * findOfflineTerminal
     * @return
     */
    public List<Map<String, Object>> findShutdownAir(int offline) {
        return this.historyMapper.findShutdownAir(offline);
    }

    /**
     * 查询开机的空调
     * @param offline
     * @return
     */
    public List<Map<String, Object>> findOpenAir(int offline) {
        return this.historyMapper.findOpenAir(offline);
    }

    /**
     * 查出指定设备的故障数据
     * 1 如果 repairDate 不存在，就查出 createDate-updateDate的数据
     * 2 如果repairDate 存在，就查出 createDate-repairDate 数据 再查出 repairDate+1 -repairDate+11
     * @return
     */
    public void exportDiagnose(String tid, String createDate, String repairDate, String updateDate, String faultCode ) {
        logger.info("start export data: tid->{} createDate->{} repaireDate->{} updateDate->{}", tid, createDate, repairDate, updateDate);
        createDate = extractDay(createDate);
        repairDate = extractDay(repairDate);
        updateDate = extractDay(updateDate);


        // 正常数据
        List<String> days = null;
        // 非正常数据
        List<String> diagnoseDays = null;

        if (repairDate != null && repairDate.length() >= 10) {
            diagnoseDays = DateUtil.days(DateUtil.parse(createDate), DateUtil.parse(repairDate));

            days = new LinkedList<>();
            LocalDate date = DateUtil.parse(repairDate);
            for (int i = 1; i <= 10; i++) {
                date = DateUtil.nextDay(date);

                days.add(DateUtil.format(date));
            }
        }else {
            days = DateUtil.days(DateUtil.parse(createDate), DateUtil.parse(updateDate));
        }

        if (days != null) {
            days.parallelStream().forEach(e -> {
                exportData(tid, e, "diagnose_normal/" + faultCode + "_" + tid + "_" + e + ".xls");
            });
        }

        if (diagnoseDays != null) {
            diagnoseDays.parallelStream().forEach(e -> {
                exportData(tid, e, "diagnose/" + faultCode + "_"+ tid + "_" + e + ".xls");
            });
        }

        logger.info("end export data: tid->{} createDate->{} repaireDate->{} updateDate->{}", tid, createDate, repairDate, updateDate);
    }

    public String extractDay(String date) {
        if (date != null && date.length() > 10) {
            date = date.split(" ")[0];
        }
        return date;
    }


    /**
     * 导出数据
     * @param tid 设备的ID
     * @param date 导出的日期
     */
    private void exportData(String tid, String date, String filename) {
        String[] headers = ("freq_conversion, cooling_input_power,  heading_input_power,  ehtrp,  0,  0,  max_input_power, 0,"+
                "tid,currentPower,collectTime,switch_,runModel,tempratureSet,temperature_wireless,humidityReal,temperatureOut, 0,"+
                "fan_power").split(",");

        for(int i = 0; i < headers.length; i++) {
            headers[i] = headers[i].trim();
        }
        String[] fields = headers;

        List<Map<String, Object>> data = this.historyMapper.findOneDayDiagnoseByTid(Map.of("tid", tid, "day", date));

        try {
            ExcelUtil.createExcel(filename, headers, fields, data);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
