package cn.shaikuba.mock.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class LocalWebSecurityConfigurer extends WebSecurityConfigurerAdapter {


//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
////        http
////                .requiresChannel()
////                .anyRequest()
////                .requiresSecure()
////                .and()
////                .authorizeRequests()
////                .antMatchers("/**")
////                .permitAll();
//
//        http.authorizeRequests()
//                .anyRequest()
//                .fullyAuthenticated()
//                .and()
//                .formLogin();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic() // it indicate basic authentication is requires
                .and()
                .authorizeRequests()
                .antMatchers("/auth/basic/**").authenticated(); // it's indicate all request matched will be secure
                //.anyRequest().permitAll();
        http.csrf().disable();
    }

    @Override      // here is configuration related to spring boot basic authentication
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                .withUser("tester").password(new BCryptPasswordEncoder().encode("123456")).roles("USER")
                .and()
                .withUser("admin").password(new BCryptPasswordEncoder().encode("123456")).roles("USER");
    }


//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.ldapAuthentication()
//                .userDnPatterns("uid={0},ou=people")
//                .groupSearchBase("ou=groups")
//                .contextSource()
//                .url("ldap://localhost:8389/dc=springframework,dc=org")
//                .and()
//                .passwordCompare()
//                .passwordEncoder(new BCryptPasswordEncoder())
//                .passwordAttribute("userPassword");
//    }
}