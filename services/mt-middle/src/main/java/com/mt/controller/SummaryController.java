package com.mt.controller;

import com.mt.node.SummaryService;
import com.mt.response.SummaryResponse;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/middle/summary")
public class SummaryController {

    @Setter(onMethod = @__({@Autowired}))
    private SummaryService summaryService;

    @GetMapping
    public SummaryResponse getSummaryResponse(@RequestHeader("Authorization") String authorization) {
        return summaryService.getSummaryResponse(authorization);
    }

}
