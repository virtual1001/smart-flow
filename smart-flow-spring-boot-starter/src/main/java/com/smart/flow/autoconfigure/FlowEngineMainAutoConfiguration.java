package com.smart.flow.autoconfigure;
import com.smart.flow.FlowEngineExecutor;
import com.smart.flow.FlowEngineExecutorStart;
import com.smart.flow.SpringBeanManageSpi;
import com.smart.flow.property.FlowEngineConfig;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@AutoConfigureAfter({ FlowEnginePropertyAutoConfiguration.class }) //当前配置 FlowEngineMainAutoConfiguration 在指定 FlowEnginePropertyAutoConfiguration 之后执行
@ConditionalOnBean(FlowEngineConfig.class)//在spring容器中有FlowEngineConfig 时才加载被注解的类
@ConditionalOnProperty(prefix = "flow-engine", name = "enable", havingValue = "true") //配置为true配置才生效
@Import(SpringBeanManageSpi.class)
public class FlowEngineMainAutoConfiguration {




	@Bean
	@ConditionalOnMissingBean
	public FlowEngineExecutor flowEngineExecutor(FlowEngineConfig engineConfig, SpringBeanManageSpi springBeanManageSpi) {
        FlowEngineExecutor flowExecutor = new FlowEngineExecutor();
		flowExecutor.setFlowEngineConfig(engineConfig);
		return flowExecutor;
	}

    @Bean
    @ConditionalOnMissingBean
    public FlowEngineExecutorStart flowEngineExecutorStart(FlowEngineExecutor engineConfig, SpringBeanManageSpi springBeanManageSpi) {
        return new FlowEngineExecutorStart(engineConfig);
    }

    static {
        System.out.println("打印了");
    }

}
