package com.mye.mine.controller;

import com.mye.mine.service.ElecService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 需要调用api_web
 */
@RestController
public class ElecController {
    private String tidString = "EC-B1-00-00-00-00-FD-90\n" +
            "EC-B1-00-00-00-00-CB-E2\n" +
            "EC-B1-00-00-00-01-1D-50\n" +
            "EC-B1-00-00-00-01-1D-A3\n" +
            "EC-B1-00-00-00-01-1D-31\n" +
            "EC-B1-00-00-00-00-F0-9E\n" +
            "EC-B1-00-00-00-00-DD-C7\n" +
            "EC-B1-00-00-00-01-1D-AC\n" +
            "EC-B1-00-00-00-01-1D-82\n" +
            "EC-B1-00-00-00-01-1D-A6\n" +
            "EC-B1-00-00-00-01-1D-5C\n" +
            "EC-B1-00-00-00-01-1D-4D\n" +
            "EC-B1-00-00-00-01-1D-3F\n" +
            "EC-B1-00-00-00-01-1D-5E\n" +
            "EC-B1-00-00-00-01-1D-39\n" +
            "EC-B1-00-00-00-01-1D-44\n" +
            "EC-B1-00-00-00-01-1D-D1\n" +
            "EC-B1-00-00-00-01-1D-78\n" +
            "EC-B1-00-00-00-01-1D-33\n" +
            "EC-B1-00-00-00-01-1D-3C\n" +
            "EC-B1-00-00-00-01-1D-9C\n" +
            "EC-B1-00-00-00-01-1D-94\n" +
            "EC-B1-00-00-00-01-1D-A8\n" +
            "EC-B1-00-00-00-01-1D-83\n" +
            "EC-B1-00-00-00-01-1D-A2\n" +
            "EC-B1-00-00-00-01-1D-35\n" +
            "EC-B1-00-00-00-01-1D-96\n" +
            "EC-B1-00-00-00-01-1D-68\n" +
            "EC-B1-00-00-00-01-1D-4C\n" +
            "EC-B1-00-00-00-00-E6-21\n" +
            "EC-B1-00-00-00-01-02-02\n" +
            "EC-B1-00-00-00-00-D3-3D\n" +
            "EC-B1-00-00-00-00-CF-BE\n" +
            "EC-B1-00-00-00-01-10-33\n" +
            "EC-B1-00-00-00-00-CD-EA\n" +
            "EC-B1-00-00-00-00-D5-98\n" +
            "EC-B1-00-00-00-01-1D-B0\n" +
            "EC-B1-00-00-00-00-DD-8D\n" +
            "EC-B1-00-00-00-00-FE-30\n" +
            "EC-B1-00-00-00-00-DD-B0\n" +
            "EC-B1-00-00-00-00-DB-E6\n" +
            "EC-B1-00-00-00-00-CD-95\n" +
            "EC-B1-00-00-00-00-E4-36\n" +
            "EC-B1-00-00-00-00-EB-75\n" +
            "EC-B1-00-00-00-01-03-88\n" +
            "EC-B1-00-00-00-00-FF-1A\n" +
            "EC-B1-00-00-00-00-D3-A5\n" +
            "EC-B1-00-00-00-00-DD-BD\n" +
            "EC-B1-00-00-00-00-E5-F0\n" +
            "EC-B1-00-00-00-00-DC-DE\n" +
            "EC-B1-00-00-00-00-EB-4F\n" +
            "EC-B1-00-00-00-00-DC-6B\n" +
            "EC-B1-00-00-00-00-FE-81\n" +
            "EC-B1-00-00-00-00-DD-B4\n" +
            "EC-B1-00-00-00-00-DD-CA\n" +
            "EC-B1-00-00-00-00-EB-5C\n" +
            "EC-B1-00-00-00-00-EB-5E\n" +
            "EC-B1-00-00-00-01-03-C3\n" +
            "EC-B1-00-00-00-00-E6-3D\n" +
            "EC-B1-00-00-00-00-E2-0C\n" +
            "EC-B1-00-00-00-00-CC-46\n" +
            "EC-B1-00-00-00-00-D9-8A\n" +
            "EC-B1-00-00-00-00-DF-F7\n" +
            "EC-B1-00-00-00-00-EB-37\n" +
            "EC-B1-00-00-00-00-DA-09\n" +
            "EC-B1-00-00-00-00-DA-07\n" +
            "EC-B1-00-00-00-00-CA-6E\n" +
            "EC-B1-00-00-00-00-DA-13\n" +
            "EC-B1-00-00-00-00-CC-FD\n" +
            "EC-B1-00-00-00-00-EB-4A\n" +
            "EC-B1-00-00-00-00-F6-65\n" +
            "EC-B1-00-00-00-00-F7-BE\n" +
            "EC-B1-00-00-00-00-FF-E5\n" +
            "EC-B1-00-00-00-00-DD-F2\n" +
            "EC-B1-00-00-00-00-EB-67\n" +
            "EC-B1-00-00-00-00-E1-A7\n" +
            "EC-B1-00-00-00-00-DD-81\n" +
            "EC-B1-00-00-00-00-DF-1E\n" +
            "EC-B1-00-00-00-00-C6-E0\n" +
            "EC-B1-00-00-00-01-05-9E\n" +
            "EC-B1-00-00-00-00-CA-6F\n" +
            "EC-B1-00-00-00-00-DD-A7\n" +
            "EC-B1-00-00-00-00-DD-AA\n" +
            "EC-B1-00-00-00-00-DA-5A\n" +
            "EC-B1-00-00-00-00-DD-97\n" +
            "EC-B1-00-00-00-00-F0-DC\n" +
            "EC-B1-00-00-00-00-FB-16\n" +
            "EC-B1-00-00-00-00-F6-50\n" +
            "EC-B1-00-00-00-00-FB-11\n" +
            "EC-B1-00-00-00-00-ED-A8\n" +
            "EC-B1-00-00-00-00-F4-8F\n" +
            "EC-B1-00-00-00-00-D2-A8\n" +
            "EC-B1-00-00-00-00-DA-0F\n" +
            "EC-B1-00-00-00-01-05-C8\n" +
            "EC-B1-00-00-00-00-DA-14\n" +
            "EC-B1-00-00-00-00-FB-24\n" +
            "EC-B1-00-00-00-00-D0-0A\n" +
            "EC-B1-00-00-00-00-E5-FF\n" +
            "EC-B1-00-00-00-00-CF-FD\n" +
            "EC-B1-00-00-00-00-C9-FA\n" +
            "EC-B1-00-00-00-00-DA-02\n" +
            "EC-B1-00-00-00-00-EA-21\n" +
            "EC-B1-00-00-00-00-EA-E9\n" +
            "EC-B1-00-00-00-00-D1-66\n" +
            "EC-B1-00-00-00-00-DC-70\n" +
            "EC-B1-00-00-00-00-DC-ED\n" +
            "EC-B1-00-00-00-00-F6-44";

    @Autowired
    private ElecService elecService;

    @ApiOperation("查询指定区间每一天的用电量 日期格式 yyyy-MM-dd")
    @GetMapping("/queryElecEveryDay")
    public Object queryElecEveryDay(@RequestParam("beginDay") String beginDay,
                                    @RequestParam("endDay") String endDay) {
        this.elecService.queryUseElecEveryDay(beginDay, endDay, tidString);
        return Map.of("result", 1);
    }

    @ApiOperation("查询指定日期区间的用电量 日期格式 yyyy-MM-dd HH:mm:ss")
    @GetMapping("/queryUseElecByRangeDay")
    public Object queryUseElecByRangeDay(@RequestParam("beginDay") String beginDay,
                                         @RequestParam("endDay") String endDay) {

        this.elecService.queryUseElecByRangeDay(beginDay, endDay, tidString);

        return Map.of("result", 1);
    }

    @ApiOperation("查询指定区间的运行时长 日期格式 yyyy-MM-dd")
    @GetMapping("/queryRuntimeByRangeDay")
    public Object queryRuntimeByRangeDay(@RequestParam("beginDay") String beginDay,
                                         @RequestParam("endDay") String endDay) {
        this.elecService.queryRuntimeByRangeDay(beginDay, endDay, this.tidString.split("\n"));

        return Map.of("status", "success");
    }

    @ApiOperation("查询指定区间的每一天的运行时长 日期格式 yyyy-MM-dd")
    @GetMapping("/queryRuntimeEveryDayByRangeDay")
    public Object queryRuntimeEveryDayByRangeDay(@RequestParam("beginDay") String beginDay,
                                         @RequestParam("endDay") String endDay) {
        this.elecService.queryRuntimeEveryDay(beginDay, endDay, this.tidString.split("\n"));

        return Map.of("status", "success");
    }
}
