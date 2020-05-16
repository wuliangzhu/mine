package com.mye.mine.service;

import com.mye.mine.config.WatchHotels;
import com.mye.mine.entity.Device;
import com.mye.mine.mapper.EntityMapper;
import com.mye.mine.mapper.HistoryMapper;
import com.mye.mine.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 查询指定时间段的数据
 */
@Service
public class TemptureService implements CommandLineRunner {
    private static Logger logger = LoggerFactory.getLogger(TemptureService.class);

    @Autowired
    private WatchHotels hotels;
    @Autowired
    private HistoryMapper saveMapper;

    @Autowired
    private EntityMapper entityMapper;

    private ConcurrentHashMap<Long, Device> devices;


    /**
     * 查询指定日期的数据
     * {tid, deviceId, max_temp_real, avg_temp_real, max_current_power, avg_current_power, roomName, hotelName}
     * @param day 查询指定日期的数据
     * @param temperature 查询高于指定温度的设备
     * @return
     */
    public List<Map<String, Object>> findMaxTempByTemper(String day, int temperature) {
        List<Map<String, Object>> ret = this.saveMapper.findMaxTempByTemper(Map.of("day", day, "temperature", temperature));
        ret.forEach(e -> {
            e.put("collectTime", day);

            Object o =  e.get("deviceId");
            if (o != null) {
                Long devId = (Long)o;
                Device device = this.devices.get(devId);
                e.put("roomName", device.getRoomName());
                e.put("hotelName", device.getHotelName());
            }
        });

        return ret;
    }

    /**
     * 查询一个日期区间的数据.然后按照日期进行了排序
     *
     * @param start end 查询指定日期的数据
     * @param temperature 查询高于指定温度的设备
     * @return
     */
    public List<Map<String, Object>> findMaxTempByTemper(String start, String end,  int temperature) {
        List<String> days = DateUtil.days(DateUtil.parse(start), DateUtil.parse(end));

        return days.parallelStream().flatMap( e -> {
            return this.findMaxTempByTemper(e, temperature).stream();
        }).sorted(Comparator.comparing(e -> {
            return e.get("collectTime").toString();
        })).collect(Collectors.toList());
    }

    /**
     * 获取指定日期区间的电量之和
     *
     * @param begin
     * @param end
     * @return
     */
    public Map<String, Object> findPowerByDate(String begin, String end) {
        LocalDate s = DateUtil.parse(begin);
        LocalDate e = DateUtil.parse(end);

        List<String> days = DateUtil.days(s, e);
        //TODO: add mapper code
        return null;
    }
    /**
     * 打印出监控的信息
     */
    public void print() {
//        List<Hotel> hotels = this.hotels.getHotelList();
//        hotels.forEach(e -> {
//            logger.info("hotel:(), {}: {}", e.getDatasource(), e.getHotel(), e.getRooms());
//        });
    }

    public void run(String[] args) {
//        reloadDevices();
    }

    public int reloadDevices() {
        logger.info("初始化设备列表");
        if (this.devices == null) {
            this.devices = new ConcurrentHashMap<>();
        }
        List<Device> devices = this.entityMapper.getDeviceList();
        int size = devices.size();

        devices.forEach(e -> {
            this.devices.put(e.getId(), e);
        });

        logger.info("一共加载设备数量：{}", size);

        return size;
    }
}
