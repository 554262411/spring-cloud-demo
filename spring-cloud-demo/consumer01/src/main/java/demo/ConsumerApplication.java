package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;

//Ribbon和Eureka整合以后，客户端可以直接调用，不用关系具体的地址和端口号
@SpringBootApplication
@EnableEurekaClient
//启用Feign，指定扫描的包，也就是要扫描到子项目中的打了@FeignClient(value = "PROVIDER01")注解的接口
@EnableFeignClients(basePackages = {"demo"})
//开启服务熔断监控依赖
@EnableHystrixDashboard
public class ConsumerApplication {
    //psvm   快捷生成主函数
    public static void main(String[] args){
        SpringApplication.run(ConsumerApplication.class,args);
    }
}
