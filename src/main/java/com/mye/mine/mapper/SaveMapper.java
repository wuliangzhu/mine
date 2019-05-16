package com.mye.mine.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SaveMapper {
    List<Map<String, Object>> findTidByParams(Map<String, Object> params);
    List<Map<String, Object>> findLastReportTime(Map<String, String> params);
    List<Map<String, Object>> findOfflineTerminal(int offline);
    List<Map<String, Object>> findShutdownAir(int offline);
    List<Map<String, Object>> findOpenAir(int offline);
}
