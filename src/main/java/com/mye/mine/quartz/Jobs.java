package com.mye.mine.quartz;

import com.mye.mine.service.HistoryService;
import com.mye.mine.service.MailService;
import com.mye.mine.utils.ExcelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.SQLTransientConnectionException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 所有定时任务都在这里触发
 */
@Service
@Configuration
public class Jobs {
    private static Logger logger = LoggerFactory.getLogger(Jobs.class);

    @Autowired
    private HistoryService historyService;

    @Autowired
    private MailService mailService;

    @Value("${myapp.job.toSend}")
    private List<String> to;

    private String dir = "./data/";

    /**
     * 1 早上6点30分 导出 开机的房间
     * 2 12点40 导出 关机的房间
     * 3 14点20 导出 开机的房间
     * 4 22点10 导出 关机的房间
     *
     */
    @Scheduled(cron = "0 30 6 * * *")
    public void queryOpenAir() {
        logger.info("开始查询开机的空调");
        String filename = this.exportOpenAir(15);

        this.sendMail(filename, "6:30空调开机数据");
    }

    @Scheduled(cron = "0 40 12 * * *")
    public void queryShutdownAir() {
        logger.info("开始查询关机的空调");
        String filename = this.exportShutDownAir(15);

        this.sendMail(filename, "12:40空调关机数据");
    }

    @Scheduled(cron = "0 20 14 * * *")
    public void queryOpenAir2() {
        logger.info("开始查询开机的空调");
        String filename = this.exportOpenAir(15);
        this.sendMail(filename, "14:20空调开机数据");
    }

    @Scheduled(cron = "0 10 22 * * *")
    public void queryShutdownAir2() {
        logger.info("开始查询关机的空调");
        String filename = this.exportShutDownAir(15);
        this.sendMail(filename, "22:40空调关机数据");
    }

    /**
     *      * 1 早上6点30分 导出 离线
     *      * 2 12点40 导出 关机的房间
     *      * 3 14点20 导出 开机的房间
     */
    @Scheduled(cron = "0 30 6 * * *")
    public void queryOfflineTerminal() {
        logger.info("开始查询离线的设备");
        String filename = this.exportOfflineTerminal(15);
        this.sendMail(filename, "6:30设备离线数据");
    }

    @Scheduled(cron = "0 40 12 * * *")
    public void queryOfflineTerminal2() {
        logger.info("开始查询离线的设备");
        String filename = this.exportOfflineTerminal(15);
        this.sendMail(filename, "12:40设备离线数据");
    }

    @Scheduled(cron = "0 20 14 * * *")
    public void queryOfflineTerminal3() {
        logger.info("开始查询离线的设备");
        String filename = this.exportOfflineTerminal(15);
        this.sendMail(filename, "14:20设备离线数据");
    }

    @Scheduled(cron = "0 10 22 * * *")
    public void queryOfflineTerminal4() {
        logger.info("开始查询离线的设备");
        String filename = this.exportOfflineTerminal(15);
        this.sendMail(filename, "22:10设备离线数据");
    }

    private String exportShutDownAir(int offline) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String filename = simpleDateFormat.format(calendar.getTime());

        while (true) {
            try {
                List<Map<String, Object>> data = this.historyService.findShutdownAir(offline);

                ExcelUtil.createExcel(filename = dir + "shutdownAir_" + filename + ".xls",
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

        return filename;
    }

    private String exportOpenAir(int offline) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String filename = simpleDateFormat.format(calendar.getTime());

        int count = 5;
        while (count-- > 0) {
            try {
                List<Map<String, Object>> data = this.historyService.findOpenAir(offline);

                ExcelUtil.createExcel(filename = dir + "openAir_" + filename + ".xls",
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

        return filename;
    }

    private String exportOfflineTerminal(int offline) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String filename = simpleDateFormat.format(calendar.getTime());
        int count = 5;
        while (count-- > 0) {
            try {
                List<Map<String, Object>> data = this.historyService.findOfflineTerminal(offline);

                ExcelUtil.createExcel(filename = dir + "offline_" + filename + ".xls",
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

        return filename;
    }

    /**
     * 给配置的接受者发送数据
     *
     * @param filename
     * @param dataType
     */
    private void sendMail(String filename, String dataType) {
        this.to = new LinkedList<>();
        this.to.add("huihuixiao@pihyun.com");
        this.to.add("liangzhuwu@pihyun.com");
        this.to.forEach( e -> {
            this.mailService.sendAttachmentsMail(e, dataType, dataType, new String[]{filename});
        });

    }
}
