package com.mt.node;

import com.mt.response.DashboardResponse;

public interface DashboardService {
    DashboardResponse getDashboardResponse(String authorization);
}
