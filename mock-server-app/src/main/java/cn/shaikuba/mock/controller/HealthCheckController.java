package cn.shaikuba.mock.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthCheckController {

    @GetMapping(value = "/health")
    public Map<String, Object> preAuth() {
        Map<String, Object> result = new HashMap<>();
        result.put("SERVER-TIME", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        result.put("STATUS", "OK");

        return result;
    }

}
