package demo.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 请求模板配置类
 */
@Configuration
public class ConfigBean {

    @Bean
    //负载均衡，客户端集群RestTemplate，在控制区中就可已通过Eureka中的应用名称获取具体的地址和端口
    //Bean实例打了Ribbon负载均衡@LoadBalanced注解，就可Eureka结合起来了
    //负载均衡了多个Eureka注册中心，一个注册中心断掉，其它继续可用
    //启用负载均衡之后，无法再通过地址+端口号的方式访问了，只能通过注册到Eureka中微服务名称进行访问了
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
