package cn.shaikuba.mock.controller;

import cn.shaikuba.mock.TestPeople;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class HealthCheckController {

    @Autowired
    private TestPeople testPeople;

    @PostConstruct
    public void init() {
        testPeople.sayHello();
    }

    @GetMapping(value = "/health")
    public Map<String, Object> preAuth() {
        Map<String, Object> result = new HashMap<>();
        result.put("SERVER-TIME", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        result.put("STATUS", "OK");

        return result;
    }

}
