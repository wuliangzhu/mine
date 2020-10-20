package com.mye.mine.service;

import com.mye.mine.entity.DeviceHistoryState;
import com.mye.mine.mapper.DeviceHistoryStateMapper;
import com.mye.mine.utils.DateUtil;
import com.mye.mine.utils.ExcelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * 对指定目录的文件内容进行合并，并存入数据库
 * 1 遍历出所有的文件；
 * 2 配置列到数据库列的映射
 * 2 读取文件内容存储db
 *
 */
@Service
public class MergeExcelService {
    private static Logger logger = LoggerFactory.getLogger(MergeExcelService.class);

    @Autowired
    private DeviceHistoryStateMapper deviceHistoryStateMapper;
    /**
     * 读取目录的文件
     * @param directory
     */
    public void saveFile2Db(String directory) {
        File file = new File(directory);

        File[] files = file.listFiles( (d, name) -> {
            return  name.endsWith(".xlsx");
        });

        // 开始读取数据存入db
        Arrays.asList(files).parallelStream().forEach(e -> {
            try {
                logger.info("start hanle {}" , e.getAbsolutePath());
                List<List<Object>> records = ExcelUtil.readExcel(e);

                records.forEach(r -> {
                    try {
                        DeviceHistoryState deviceHistoryState = new DeviceHistoryState();

                        deviceHistoryState.setProvince(r.get(0).toString());
                        deviceHistoryState.setCity(r.get(1).toString());
                        deviceHistoryState.setArea(r.get(2).toString());
                        deviceHistoryState.setAddress(r.get(3).toString());
                        deviceHistoryState.setAddressId(r.get(4).toString());
                        deviceHistoryState.setDeviceName(r.get(5).toString());
                        deviceHistoryState.setDeviceFactory(r.get(6).toString());
                        deviceHistoryState.setDeviceModel(r.get(7).toString());
                        deviceHistoryState.setDeviceId(r.get(8).toString());
                        deviceHistoryState.setPointType(r.get(9).toString());
                        deviceHistoryState.setUpdateTime(DateUtil.parseFromString(r.get(10).toString()));
                        deviceHistoryState.setPointValue(Float.parseFloat(r.get(11).toString()));
                        deviceHistoryState.setUnit(r.get(12).toString());
                        deviceHistoryState.setStatus(r.get(13).toString());
                        if ("36012100001650".equals(deviceHistoryState.getAddressId())) {
                            deviceHistoryStateMapper.insert(deviceHistoryState);
                        }
                    }catch (Exception exp) {
                        exp.printStackTrace();
                    }
                });

                logger.error("file {} has records {}", e, records.size());
            } catch (Exception e1) {
                e1.printStackTrace();
                logger.error("read file {} error", e.getAbsolutePath());
            }
        });
    }
}
