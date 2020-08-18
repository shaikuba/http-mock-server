package cn.shaikuba.mock.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @classname LocalWebMvcConfigurer
 * @description Configure separately for web mvc behavior
 *
 * @author Ray.Xu
 * @date 2020/8/18 10:18
 */
@Configuration
public class LocalWebMvcConfigurer implements WebMvcConfigurer {

    // Making entire application request mappings accessible for all origins
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowCredentials(true);
    }

}
