package demo;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//启用Eureka注册，服务启动后，自动把微服务注册到Eureka服务端
//此处也可以忽略不写，同样会自动扫描到Eureka注册中心去：只要是POM引用了Eureka依赖，就会自动扫描注册到Eureka注册中心
@EnableEurekaClient
//@EnableDiscoveryClient
//启用Hystrix熔断机制，断路由
@EnableCircuitBreaker
public class ProviderApplication {
    /*
    从Spring Cloud Edgware开始，@EnableDiscoveryClient 或@EnableEurekaClient 可省略。只需加上相关依赖，并进行相应配置，
    即可将微服务注册到服务发现组件上。
    共同点：@EnableDiscoveryClient和@EnableEurekaClient共同点就是：都是能够让注册中心能够发现，扫描到改服务。
    不同点：@EnableEurekaClient只适用于Eureka作为注册中心，@EnableDiscoveryClient 可以是其他注册中心。
    */
    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }

    //为了配合hystrix监控使用，需要增加一个servlet的Bean
    @Bean
    public ServletRegistrationBean hystrixMetricsStreamServlet(){
        ServletRegistrationBean registrationBean=new ServletRegistrationBean(new HystrixMetricsStreamServlet());
        registrationBean.addUrlMappings("/actuator/hystrix.stream");
        return registrationBean;
    }

}
