package com.mt.feign;

import com.mt.config.FeignConfig;
import com.mt.dto.model_dto.ProfitReportDto;
import com.mt.dto.reports.DailyAmountReportDto;
import com.mt.dto.reports.DailyReportDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(
        name = "reports",
        url="${feign.client.config.mt-core-service.path.report}",
        configuration = FeignConfig.class
)
public interface ReportCore {
    @GetMapping("/daily-dashboard")
    List<DailyReportDto> getDailyReports(@RequestHeader("Authorization") String authorization);

    @GetMapping("/daily-amount-reports")
    List<DailyAmountReportDto> getDailyAmountReports(@RequestHeader("Authorization") String authorization);

    @GetMapping("/profits-reports")
    List<ProfitReportDto> getProfitReports(@RequestHeader("Authorization") String authorization);

    @PostMapping("/save/daily-amount-reports")
    void saveDailyAmountReport();
}
