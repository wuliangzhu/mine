package com.mye.mine.quartz;

import com.mye.mine.service.HistoryService;
import com.mye.mine.utils.ExcelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.SQLTransientConnectionException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * 所有定时任务都在这里触发
 */
@Component
public class Jobs {
    private static Logger logger = LoggerFactory.getLogger(Jobs.class);

    @Autowired
    private HistoryService historyService;
    /**
     * 1 早上6点30分 导出 开机的房间
     * 2 12点40 导出 关机的房间
     * 3 14点20 导出 开机的房间
     *
     *
     */
    @Scheduled(cron = "0 30 6 * * *")
    public void queryShutdownAir() {
        logger.info("开始查询关闭的空调");
        this.exportShutDownAir(15);
    }

    @Scheduled(cron = "0 40 12 * * *")
    public void queryShutdownAir2() {
        logger.info("开始查询开机的空调");
        this.exportOpenAir(15);
    }

    @Scheduled(cron = "0 20 14 * * *")
    public void queryShutdownAir3() {
        logger.info("开始查询关闭的空调");
        this.exportShutDownAir(15);
    }

    /**
     *      * 1 早上6点30分 导出 离线
     *      * 2 12点40 导出 关机的房间
     *      * 3 14点20 导出 开机的房间
     */
    @Scheduled(cron = "0 30 6 * * *")
    public void queryOfflineTerminal() {
        logger.info("开始查询离线的设备");
        this.exportOfflineTerminal(15);
    }

    @Scheduled(cron = "0 40 12 * * *")
    public void queryOfflineTerminal2() {
        logger.info("开始查询离线的设备");
        this.exportOfflineTerminal(15);
    }

    @Scheduled(cron = "0 20 14 * * *")
    public void queryOfflineTerminal3() {
        logger.info("开始查询离线的设备");
        this.exportOfflineTerminal(15);
    }

    private void exportShutDownAir(int offline) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String filename = simpleDateFormat.format(calendar.getTime());

//        List<Map<String, Object>> data = this.historyService.findShutdownAir(offline);
//
//        try {
//            ExcelUtil.createExcel("shutdownAir_" + filename,
//                    new String[]{"TID", "上报时间", "设备号", "房间号", "楼栋名字"},
//                    new String[]{"t_id", "update_time", "ac_name", "name", "hotel_name"},
//                    data);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        while (true) {
            try {
                List<Map<String, Object>> data = this.historyService.findShutdownAir(offline);

                ExcelUtil.createExcel("shutdownAir_" + filename + ".xls",
                        new String[]{"TID", "上报时间", "设备号", "房间号", "楼栋名字"},
                        new String[]{"t_id", "update_time", "ac_name", "name", "hotel_name"},
                        data);
                break;
            } catch (SQLTransientConnectionException e) {
                e.printStackTrace();
                try {
                    Thread.sleep(1000);
                }catch (Exception e1) {
                    e.printStackTrace();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void exportOpenAir(int offline) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String filename = simpleDateFormat.format(calendar.getTime());

//        List<Map<String, Object>> data = this.historyService.findOpenAir(offline);
//
//        try {
//            ExcelUtil.createExcel("shutdownAir_" + filename,
//                    new String[]{"TID", "上报时间", "设备号", "房间号", "楼栋名字"},
//                    new String[]{"t_id", "update_time", "ac_name", "name", "hotel_name"},
//                    data);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        while (true) {
            try {
                List<Map<String, Object>> data = this.historyService.findOpenAir(offline);

                ExcelUtil.createExcel("openAir_" + filename + ".xls",
                        new String[]{"TID", "上报时间", "设备号", "房间号", "楼栋名字"},
                        new String[]{"t_id", "update_time", "ac_name", "name", "hotel_name"},
                        data);
                break;
            } catch (SQLTransientConnectionException e) {
                e.printStackTrace();
                try {
                    Thread.sleep(1000);
                }catch (Exception e1) {
                    e.printStackTrace();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void exportOfflineTerminal(int offline) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String filename = simpleDateFormat.format(calendar.getTime());

        while (true) {
            try {
                List<Map<String, Object>> data = this.historyService.findOfflineTerminal(offline);

                ExcelUtil.createExcel("offline_" + filename + ".xls",
                        new String[]{"TID", "上报时间", "设备号", "房间号", "楼栋名字"},
                        new String[]{"t_id", "update_time", "ac_name", "name", "hotel_name"},
                        data);
                break;
            } catch (SQLTransientConnectionException e) {
                e.printStackTrace();
                try {
                    Thread.sleep(1000);
                }catch (Exception e1) {
                    e.printStackTrace();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
