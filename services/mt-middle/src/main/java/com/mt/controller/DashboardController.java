package com.mt.controller;

import com.mt.node.DashboardService;
import com.mt.response.DashboardResponse;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/middle/dashboard")
public class DashboardController {

    @Setter(onMethod = @__({@Autowired}))
    private DashboardService dashboardService;

    @GetMapping
    public DashboardResponse getDashboardResponse(@RequestHeader("Authorization") String authorization) {
        return dashboardService.getDashboardResponse(authorization);
    }

}
