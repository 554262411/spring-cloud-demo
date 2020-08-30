package demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

//测试类需要放置到和项目主配置类相同的包下面，否则识别不到相应的配置信息

@Slf4j
@SpringBootTest
class DemoApplicationTests {

    //Spring Boot默认选用：slf4j + logback
    Logger logger = LoggerFactory.getLogger(DemoApplicationTests.class);

    //@Autowired
    //ApplicationContext context;  //spring ioc容器

    @Test
    void logTest(){
        log.info("我是lombok");
        //日志输出级别
        logger.trace("hello trace");
        logger.debug("hello debug");
        //
        logger.info("hello info");
        logger.warn("hello warn");
        logger.error("hello error");
    }


}
