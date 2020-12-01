package com.bitbybit.consumer.conroller;

import com.bitbybit.consumer.remote.api.ProviderApi;
import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadRegistry;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.vavr.collection.Seq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
public class ConsumerController {
    @Autowired
    ProviderApi providerApi;

    @Autowired
    RateLimiterRegistry rateLimiterRegistry;

    @Autowired
    BulkheadRegistry bulkheadRegistry;

    @GetMapping("consumer/ratelimiter")
    public Integer ratelimiter() {
        return providerApi.ratelimiter();
    }

    @GetMapping("consumer/bulkhead")
    public Integer bulkhead() {
        return providerApi.bulkhead();
    }

    @GetMapping("consumer/config")
    public Map<String, List> config() {
        Seq<RateLimiter> allRateLimiters = rateLimiterRegistry.getAllRateLimiters();
        Seq<Bulkhead> allBulkheads = bulkheadRegistry.getAllBulkheads();
        Map<String, List> result = new HashMap<>();
        List<Bulkhead> bulkheads = allBulkheads.collect(Collectors.toList());
        List<RateLimiter> rateLimiters = allRateLimiters.collect(Collectors.toList());
        result.put("bulkhead",bulkheads);
        result.put("rateLimiter", rateLimiters);
        return result;
    }

}
