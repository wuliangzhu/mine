package com.mye.mine.mapper;

import com.mye.mine.entity.History;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface HistoryMapper {
    /**
     * 返回电压为0的不同的设备tid
     * @param params 日期 yyyy-MM-dd {day:,}
     * @return {tid:,}
     */
    List<Map<String, Object>> findTidByParams(Map<String, Object> params);
    List<History> findHistoryByTid(Map<String, Object> params);
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

    /**
     * 查询指定日期的运行时间: 2019-05-17 2019-05-21 返回的是 2019-05-17 12点到 2019-05-22 12点的数据
     *
     * @param params begin end [yyyy-MM-dd]
     * @return device_id, t_id, sum(runtime_12), name, hotel_name
     */
    List<Map<String, Object>> findRuntimeByDay(Map<String, Object> params);

    /**
     * 查询出指定设备和日期的故障数据
     * @param params {tid, day}
     * @return { freq_conversion, cooling_input_power,  heading_input_power,  ehtrp,  0,  0,  max_input_power, 0,
     * tid,currentPower,collectTime,switch_,runModel,tempratureSet,temperature_wireless,humidityReal,temperatureOut, 0,
     * fan_power }
     */
    List<Map<String, Object>> findOneDayDiagnoseByTid(Map<String, Object> params);
}
