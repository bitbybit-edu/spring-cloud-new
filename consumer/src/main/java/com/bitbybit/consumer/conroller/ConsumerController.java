package com.bitbybit.consumer.conroller;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.bitbybit.consumer.remote.api.ProviderApi;
import com.bitbybit.consumer.remote.api.ProviderApiSentinel;
import com.bitbybit.consumer.service.ProviderService;
import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.bulkhead.BulkheadRegistry;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.vavr.collection.Seq;
import io.vavr.control.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
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

    @Autowired
    ProviderService providerService;

    @GetMapping("consumer/ratelimiter")
    public Integer ratelimiter() {
        return providerApi.ratelimiter();
    }

    @GetMapping("consumer/bulkhead")
    public Integer bulkhead() {
        return providerApi.bulkhead();
    }

    @GetMapping("consumer/ratelimiterAndBulkhead")
    public Integer ratelimiterAndBulkhead() {
        return providerApi.ratelimiterAndBulkhead();
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

    @GetMapping("consumer/config/change")
    public Map<String, List> configChange() {
        Seq<RateLimiter> allRateLimiters = rateLimiterRegistry.getAllRateLimiters();
        Seq<Bulkhead> allBulkheads = bulkheadRegistry.getAllBulkheads();

        allBulkheads.forEach(bulkhead -> a(bulkhead));


        Map<String, List> result = new HashMap<>();
        List<Bulkhead> bulkheads = allBulkheads.collect(Collectors.toList());
        List<RateLimiter> rateLimiters = allRateLimiters.collect(Collectors.toList());
        result.put("bulkhead",bulkheads);
        result.put("rateLimiter", rateLimiters);
        return result;
    }

    private void a(Bulkhead bulkhead) {
        if (bulkhead.getName().equals("provider")) {
            Option<String> provider = bulkhead.getTags().get("provider");
            String s = provider.get();
            BulkheadConfig bulkheadConfig = bulkhead.getBulkheadConfig();
            int maxConcurrentCalls = bulkheadConfig.getMaxConcurrentCalls() + 1;
            BulkheadConfig build = BulkheadConfig.from(bulkheadConfig).maxConcurrentCalls(maxConcurrentCalls).build();
            bulkhead.changeConfig(build);
        }
    }


    @GetMapping("consumer/sentinel")
    public Integer seninel() {
        return providerService.seninel(123L);
    }
}
