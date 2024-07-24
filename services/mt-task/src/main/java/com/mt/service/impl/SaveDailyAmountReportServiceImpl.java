package com.mt.service.impl;

import com.mt.feign.MtCore;
import com.mt.service.SaveDailyAmountReportService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveDailyAmountReportServiceImpl implements SaveDailyAmountReportService {

    @Setter(onMethod = @__(@Autowired))
    private MtCore mtCore;

    @Override
    public void saveDailyAmountReport() {
        mtCore.saveDailyAmountReport();
    }
}
