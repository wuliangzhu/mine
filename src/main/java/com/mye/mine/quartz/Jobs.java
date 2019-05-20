package com.mye.mine.quartz;

import com.mye.mine.service.HistoryService;
import com.mye.mine.service.MailService;
import com.mye.mine.service.TemptureService;
import com.mye.mine.utils.DateUtil;
import com.mye.mine.utils.ExcelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.SQLTransientConnectionException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

/**
 * 所有定时任务都在这里触发
 */
@Service
@ConfigurationProperties(prefix = "myapp")
public class Jobs {
    private static Logger logger = LoggerFactory.getLogger(Jobs.class);

    @Autowired
    private HistoryService historyService;

    @Autowired
    private MailService mailService;
    @Autowired
    private TemptureService temptureService;

    private List<String> to;
    private int age;
    private String name;
    private Map<String, String> maps;
    private String dir = "./data/";
    private boolean enable;

    public List<String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getMaps() {
        return maps;
    }

    public void setMaps(Map<String, String> maps) {
        this.maps = maps;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

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
     * 导出温度高于45度的设备
     */
    @Scheduled(cron = "0 0 6 * * *")
    public void queryMaxTemperature() {
        int temper = 45;

        logger.info("开始前一天最高温度超过{}的设备", temper);
        LocalDate today = LocalDate.now();
        today = today.plusDays(-1);
        String dayStr = today.format(DateUtil.DATE_FORMAT);

        String filename = this.exportMaxTemperatureTerminal(dayStr, 45);
        this.sendMail(filename,  dayStr + "最高温度超过45的数据", List.of("junxiong@pihyun.com", "liangzhuwu@pihyun.com"));
    }

    public List<String> getMailReceiver() {
        if (this.age > 0) {
            this.to.add(Integer.toString(age));
            this.age = -1;
            if(maps != null) {
                this.to.addAll(this.maps.keySet());
            }
        }

        return this.to;
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
        if (!isEnable())return null;

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String filename = simpleDateFormat.format(calendar.getTime());
        String ret = null;
        int count = 5;
        while (count-- > 0) {
            try {
                List<Map<String, Object>> data = this.historyService.findShutdownAir(offline);

                ExcelUtil.createExcel(ret = dir + "shutdownAir_" + filename + ".xls",
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

        return ret;
    }

    /**
     * 导出开机设备 并且所欲设备必须在offline时间有上报
     *
     * @param offline
     * @return
     */
    private String exportOpenAir(int offline) {
        if (!isEnable())return null;

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String filename = simpleDateFormat.format(calendar.getTime());
        String ret = null;
        int count = 5;
        while (count-- > 0) {
            try {
                List<Map<String, Object>> data = this.historyService.findOpenAir(offline);

                ExcelUtil.createExcel(ret = dir + "openAir_" + filename + ".xls",
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

        return ret;
    }

    /**
     * 导出离线设备
     *
     * @param offline 多久时间没有更新数据
     * @return
     */
    private String exportOfflineTerminal(int offline) {
        if (!isEnable()) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String filename = simpleDateFormat.format(calendar.getTime());
        int count = 5;
        String ret = null;
        while (count-- > 0) {
            try {
                List<Map<String, Object>> data = this.historyService.findOfflineTerminal(offline);

                ExcelUtil.createExcel(ret = dir + "offline_" + filename + ".xls",
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

        return ret;
    }

    /**
     * 导出前一天最高温度超过temperature的设备
     * @param temperature
     * @return
     */
    private String exportMaxTemperatureTerminal(String day, int temperature) {
        if (!isEnable()) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String filename = simpleDateFormat.format(calendar.getTime());
        int count = 5;
        String ret = null;
        while (count-- > 0) {
            try {
                List<Map<String, Object>> data = this.temptureService.findMaxTempByTemper(day, temperature);

                ExcelUtil.createExcel(ret = dir + "max_temperature_" + filename + ".xls",
                        new String[]{"TID", "deviceId", "最高温度", "平均温度", "最高功率", "平均功率", "房间名字", "楼栋名字"},
                        new String[]{"tid", "deviceId", "max_temp_real", "avg_temp_real",
                                "max_current_power", "avg_current_power", "roomName", "hotelName"},
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

        return ret;
    }

    /**
     * 给配置的接受者发送数据
     *
     * @param filename
     * @param dataType
     */
    private void sendMail(String filename, String dataType) {
        if (filename == null)return;

        this.to = new LinkedList<>();
        this.to.add("huihuixiao@pihyun.com");
        this.to.add("liangzhuwu@pihyun.com");

        this.sendMail(filename, dataType, this.to);

    }

    private void sendMail(String filename, String dataType, List<String> to) {
        if (filename == null)return;

        if(to != null) {
            to.forEach(e -> {
                this.mailService.sendAttachmentsMail(e, dataType, dataType, new String[]{filename});
            });
        }

    }
}
