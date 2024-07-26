package com.mt.node;

import com.mt.response.SummaryResponse;

public interface SummaryService {
    SummaryResponse getSummaryResponse(String authorization);
}
