package cn.shaikuba.mock;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.PropertySource;

/**
 * Application main entry point.
 * @author xuleilei
 */
@Slf4j
@PropertySource(value = "classpath:mock-server.properties")
@EnableCaching
@MapperScan({"cn.shaikuba.mock.data.mybatis.mapper"})
@SpringBootApplication
public class MockServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MockServerApplication.class, args);
    }

}