package com.airport.dashboard.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DashboardCacheService {

    @CacheEvict(value = CacheNames.DASHBOARD_SUMMARY, allEntries = true)
    public void evictDashboardSummaryCache() {

        log.info("Dashboard summary cache cleared.");

    }

}
