package com.mye.mine.service;

import com.mye.mine.mapper.SaveMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HistoryService {
    @Autowired
    SaveMapper saveMapper;


    public List<Map<String, Object>> findTidByParams(Map<String, Object> params) {
        return saveMapper.findTidByParams(params);
    }

    /**
     * 查询一个酒店和房间的最后登录时间
     * @param params hotel_name room_names
     * @return
     */
    public List<Map<String, Object>> findLastReportTime(Map<String, String> params) {
        return saveMapper.findLastReportTime(params);
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
        return this.saveMapper.findOfflineTerminal(offline);
    }

    /**
     * 传入一个时间查出所有 大于指定延迟没有传回数据的关机空调
     * findOfflineTerminal
     * @return
     */
    public List<Map<String, Object>> findShutdownAir(int offline) {
        return this.saveMapper.findShutdownAir(offline);
    }
}
