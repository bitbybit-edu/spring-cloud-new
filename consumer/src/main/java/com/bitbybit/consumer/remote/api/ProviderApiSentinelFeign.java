package com.bitbybit.consumer.remote.api;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "PROVIDER",contextId = "context4", fallback = ProviderApiSentinelFeign.SentinelFeignFallback.class, fallbackFactory =ProviderApiSentinelFeign.SentinelFeignFallback.class )
public interface ProviderApiSentinelFeign {

    @GetMapping("/provider/ratelimiter")
    Integer sentinel();

    @Component
    class SentinelFeignFallback implements ProviderApiSentinelFeign {

        @Override
        public Integer sentinel() {
            return -5;
        }
    }
}
