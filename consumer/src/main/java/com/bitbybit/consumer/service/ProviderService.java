package com.bitbybit.consumer.service;

import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.bitbybit.consumer.remote.api.ProviderApiSentinel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProviderService {

    @Autowired
    ProviderApiSentinel providerApiSentinel;

    // 原函数
    @SentinelResource(value = "sentinelA", blockHandler = "exceptionHandler", fallback = "helloFallback", entryType = EntryType.IN)
    public Integer seninel(Long a) {
        return providerApiSentinel.sentinel();
    }

    // Fallback 函数，函数签名与原函数一致或加一个 Throwable 类型的参数.
    public Integer helloFallback(long s) {
        return -3;
    }

    // Block 异常处理函数，参数最后多一个 BlockException，其余与原函数一致.
    public Integer exceptionHandler(long s, BlockException ex) {
        // Do some log here.
        ex.printStackTrace();
        return -4;
    }

    // 这里单独演示 blockHandlerClass 的配置.
    // 对应的 `handleException` 函数需要位于 `ExceptionUtil` 类中，并且必须为 public static 函数.
//    @SentinelResource(value = "test", blockHandler = "handleException", blockHandlerClass = {ExceptionUtil.class})
//    public void test() {
//        System.out.println("Test");
//    }

    @PostConstruct
    void initFlowRules() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("sentinelA");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // Set limit QPS to 20.
        rule.setCount(2);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }
}
