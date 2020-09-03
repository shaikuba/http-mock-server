package cn.shaikuba.mock.config;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

@EnableWebSecurity
@Configuration
public class LocalWebSecurityConfigurer extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/auth/basic/**").authenticated()
                .and()
                .httpBasic(); // it's indicate all request matched will be secure

        http.authorizeRequests()
                .antMatchers("/auth/digest/**").authenticated()
                .and()
                .addFilter(digestAuthenticationFilter())
                .exceptionHandling()
                .authenticationEntryPoint(digestAuthenticationEntryPoint());

        http.csrf().disable();
    }

    private DigestAuthenticationFilter digestAuthenticationFilter() {
        DigestAuthenticationFilter filter = new DigestAuthenticationFilter();
        filter.setAuthenticationEntryPoint(digestAuthenticationEntryPoint());//必须配置
        filter.setUserDetailsService(userDetailsService());//必须配置
        return filter;
    }

    private DigestAuthenticationEntryPoint digestAuthenticationEntryPoint() {
        DigestAuthenticationEntryPoint point = new DigestAuthenticationEntryPoint();
        point.setRealmName("realm");
        point.setKey("key");
        return point;
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return username -> { // Don't verify username validation
            UserPassword userPassword = UserPassword.valueOf(username.toUpperCase());
            List<GrantedAuthority> authorities = new ArrayList();
            authorities.add(new SimpleGrantedAuthority("auth"));
            return new User(userPassword.getUsername(), userPassword.getPassword(), true, true, true, true, authorities);
        };
    }

    @Override      // here is configuration related to spring boot basic authentication
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                .withUser(UserPassword.TESTER.getUsername()).password(new BCryptPasswordEncoder().encode(UserPassword.TESTER.getPassword())).roles("USER")
                .and()
                .withUser(UserPassword.ADMIN.getUsername()).password(new BCryptPasswordEncoder().encode(UserPassword.ADMIN.getPassword())).roles("ADMIN");
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

    private enum  UserPassword {

        TESTER("tester", "123456"),
        ADMIN("admin", "654321");

        @Getter
        private String username;

        @Getter
        private String password;

        UserPassword(String username, String password) {
            this.username = username;
            this.password = password;
        }


    }

}