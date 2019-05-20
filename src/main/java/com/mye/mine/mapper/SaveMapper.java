package com.mye.mine.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SaveMapper {
    /**
     * 返回电压为0的不同的设备tid
     * @param params 日期 yyyy-MM-dd {day:,}
     * @return {tid:,}
     */
    List<Map<String, Object>> findTidByParams(Map<String, Object> params);

    /**
     *  查询出指定楼栋和房间名字列表的最后登录时间
     *
     * @param params {room_names<String></>:, hotel_name:}
     * @return {t_id:, update_time:, ac_name:, name:, hotel_name:}
     */
    List<Map<String, Object>> findLastReportTime(Map<String, String> params);

    /**
     * 查出所有离线offline时间的所有设备
     * @param offline
     * @return  {t_id:, update_time:, ac_name:, name:, hotel_name:}
     */
    List<Map<String, Object>> findOfflineTerminal(int offline);

    /**
     * 查出所有没有离线但在关机的设备
     * @param offline
     * @return {t_id:, update_time:, ac_name:, name:, hotel_name:}
     */
    List<Map<String, Object>> findShutdownAir(int offline);

    /**
     * 查出所有没有离线但开机的设备
     * @param offline
     * @return {t_id:, update_time:, ac_name:, name:, hotel_name:}
     */
    List<Map<String, Object>> findOpenAir(int offline);

    /**
     *
     * @param params
     * @return {t_id:, update_time:, ac_name:, name:, hotel_name:}
     */
    Map<String, Object> findPowerByDay(Map<String, Object> params);

    /**
     *
     * @param params {day, temperature}
     * @return {tid, deviceId, max_temp_real, avg_temp_real, max_current_power, avg_current_power}
     */
    List<Map<String, Object>> findMaxTempByTemper(Map<String, Object> params);
}
