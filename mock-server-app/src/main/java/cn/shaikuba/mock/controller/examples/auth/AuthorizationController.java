package cn.shaikuba.mock.controller.examples.auth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ray.Xu
 * @classname AuthorizationController
 * @description Examples for how to using spring-security implement authorize
 * @date 9/1/2020 11:03 AM
 */

@RequestMapping("/auth")
@RestController
public class AuthorizationController {

    @GetMapping("/basic")
    public String basicTest() {
        return "Welcome to Basic Authentication Test Page";
    }
}
