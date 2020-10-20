package com.mye.mine.mapper;

import com.mye.mine.entity.DeviceHistoryState;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeviceHistoryStateMapper {
    void insert(DeviceHistoryState deviceHistoryState);
}
