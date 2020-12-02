package com.bitbybit.provider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("provider")
public class ProviderController {

    AtomicInteger ratelimiter = new AtomicInteger();

    AtomicInteger bulkhead = new AtomicInteger();

    AtomicInteger ratelimiterAndBulkhead = new AtomicInteger();

    @GetMapping("ratelimiter")
    public Integer ratelimiter() {
        return ratelimiter.incrementAndGet();
    }


    @GetMapping("bulkhead")
    public Integer bulkhead() {
        try {
            Thread.sleep(10 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bulkhead.incrementAndGet();
    }

    @GetMapping("ratelimiterAndBulkhead")
    public Integer ratelimiterAndBulkhead() {
        try {
            Thread.sleep(10 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ratelimiterAndBulkhead.incrementAndGet();
    }
}
