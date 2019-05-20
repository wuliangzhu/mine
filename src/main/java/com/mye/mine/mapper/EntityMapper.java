package com.mye.mine.mapper;

import com.mye.mine.entity.Device;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EntityMapper {
    public List<Device> getDeviceList();
}
