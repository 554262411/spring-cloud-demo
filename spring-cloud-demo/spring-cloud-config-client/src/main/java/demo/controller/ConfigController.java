package demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 【客户端】通过【服务端】获取【git远程仓库】中的配置信息
 */
@RestController
public class ConfigController {

    @Value("${spring.application.name}")
    private String applicationName;
    @Value("${eureka.client.service-url.defaultZone}")
    private String eurekaUrl;
    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/getConfig")
    public String getConfig(){
        return "applicationName："+applicationName+
                "eurekaUrl："+eurekaUrl+
                "serverPort："+serverPort;
    }

}
