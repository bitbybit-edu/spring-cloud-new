package com.bitbybit.consumer.remote.api;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "PROVIDER",contextId = "context3")
public interface ProviderApiSentinel {

    default Integer rateLimiterFallback(Throwable throwable) {
        System.out.println(throwable);
        return -1;
    }

    default Integer bulkheadFallback(Throwable throwable) {
        System.out.println(throwable);
        return -2;
    }

    @GetMapping("provider/ratelimiter")
    Integer sentinel();


}
