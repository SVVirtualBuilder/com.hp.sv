package com.hp.sv.runtime.reports.inmemory.rest.service;

import com.hp.sv.runtime.reports.api.RuntimeReportsService;
import org.apache.commons.lang3.Validate;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class RuntimeReportServiceImpl implements RuntimeReportsService {

    private ConcurrentMap<Integer, AtomicInteger> map = new ConcurrentHashMap<Integer, AtomicInteger>();

    public void registerService(int id) {
        Validate.isTrue(id > 0);
        Validate.isTrue(map.putIfAbsent(id, new AtomicInteger()) == null, "Service [Id=%d] is already registered.", id);
    }

    public void increaseServiceUsageCount(int id) {
        Validate.isTrue(id > 0);
        AtomicInteger count = map.get(id);
        Validate.notNull(count);
        count.incrementAndGet();
    }

    public Integer getServiceUsageCount(int id) {
        AtomicInteger count = map.get(id);
        return count != null ? count.get() : null;
    }

    public void unregisterService(int id) {
        Validate.notNull(map.remove(id), "Service [Id=%d] is not registered.", id);
    }
}
