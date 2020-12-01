package com.bitbybit.consumer.remote.api;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "PROVIDER")
public interface ProviderApi {

    @GetMapping("provider/ratelimiter")
    @RateLimiter(name = "provider", fallbackMethod = "providerFallback")
    Integer ratelimiter();

    default Integer providerFallback(Throwable throwable) {
        System.out.println(throwable);
        return -1;
    }

    @GetMapping("provider/bulkhead")
    @Bulkhead(name = "provider",type = Bulkhead.Type.SEMAPHORE, fallbackMethod = "providerFallback")
    Integer bulkhead();

}
