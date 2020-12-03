package com.bitbybit.consumer.remote.api;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "PROVIDER", contextId = "context1")
public interface ProviderApi {

    /**
     * 可以打印参数
     *
     * @param throwable
     * @return
     */
    default Integer rateLimiterFallback(Throwable throwable) {
        System.out.println(throwable);
        return -1;
    }

    default Integer bulkheadFallback(Throwable throwable) {
        System.out.println(throwable);
        return -2;
    }

    @GetMapping("provider/ratelimiter")
    @RateLimiter(name = "provider", fallbackMethod = "rateLimiterFallback")
    Integer ratelimiter();

    @GetMapping("provider/bulkhead")
    @Bulkhead(name = "provider", type = Bulkhead.Type.SEMAPHORE, fallbackMethod = "bulkheadFallback")
    Integer bulkhead();

    @GetMapping("provider/ratelimiterAndBulkhead")
    @RateLimiter(name = "provider", fallbackMethod = "rateLimiterFallback")
    @Bulkhead(name = "provider", type = Bulkhead.Type.SEMAPHORE, fallbackMethod = "bulkheadFallback")
    Integer ratelimiterAndBulkhead();

}
