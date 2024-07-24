package com.mt.task;

import com.mt.service.SaveDailyAmountReportService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SaveDailyAmountReport {

    @Setter(onMethod = @__({@Autowired}))
    private SaveDailyAmountReportService saveDailyAmountReportService;

    @Scheduled(cron = "${task.save-special-task.cron}", zone = "EST")
    public void saveDailyAmountReport() {
        saveDailyAmountReportService.saveDailyAmountReport();
    }
}
