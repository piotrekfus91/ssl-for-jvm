package com.github.ssl.jvm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@SpringBootApplication
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RestController
public class Server extends WebSecurityConfigurerAdapter {
    @GetMapping
    public String root(Principal principal) {
        return "You are mostly welcome here, " + principal.getName() + "!";
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .x509()
                .subjectPrincipalRegex("CN=(.*?)(?:,|$)")
                .userDetailsService(buildUserDetailsService());
    }

    private UserDetailsService buildUserDetailsService() {
        return username -> new User(username, "", AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
    }

    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }
}
