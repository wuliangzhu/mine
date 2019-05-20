package com.mye.mine.controller;

import com.mye.mine.quartz.Jobs;
import com.mye.mine.service.TemptureService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 1 查询指定房间所在时期的温度
 */
@RestController
public class TemptureController {
    @Autowired
    TemptureService watchTemptureService;
    @Autowired
    Jobs jobs;


    @GetMapping("/history/queryTempture")
    public List<Map<String, Object>> queryTempture() {
        this.watchTemptureService.print();

        return new LinkedList<>();
    }

    /**
     * 查询指定日期高于指定温度的设备
     *
     * @return
     */
    @ApiOperation("查询指定日期高于指定温度的设备 yyyy-MM-dd")
    @GetMapping("/history/findMaxTempByDay")
    public List<Map<String, Object>> findMaxTempByTemper(@RequestParam("day") String day,
                                                         @RequestParam("temperature")int temperature) {
        return this.watchTemptureService.findMaxTempByTemper(day, temperature);
    }

    /**
     * 查询指定日期区间高于指定温度的设备
     *
     * @return
     */
    @ApiOperation("查询指定日期区间高于指定温度的设备 yyyy-MM-dd")
    @GetMapping("/history/findMaxTempByDateRange")
    public List<Map<String, Object>> findMaxTempByTemper(@RequestParam("start") String start,
                                                         @RequestParam("end") String end,
                                                         @RequestParam("temperature")int temperature) {
        return this.watchTemptureService.findMaxTempByTemper(start, end, temperature);
    }

    @GetMapping("/history/reloadDevices")
    public Object reloadDevices() {
        int size = this.watchTemptureService.reloadDevices();

        return Map.of("size", size);
    }

    @GetMapping("/history/executeJob")
    public Object executeJob() {
        this.jobs.queryMaxTemperature();

        return Map.of("status", 0);
    }
}
