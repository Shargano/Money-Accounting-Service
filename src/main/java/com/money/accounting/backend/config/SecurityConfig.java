package com.money.accounting.backend.config;

import com.money.accounting.backend.model.User;
import com.money.accounting.backend.repository.UserRepository;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "api/v1/login", "/js/**", "/img/**", "/fonts/**", "/public/**", "/css/**", "/error**").permitAll()
                .anyRequest().authenticated()
                .and().logout().logoutSuccessUrl("/api/v1/login").permitAll()
                .and()
                .csrf().disable();

        http.logout()
                .permitAll()
                .logoutUrl("/api/v1/logout")
                .logoutSuccessUrl("/")
                .permitAll()
                .invalidateHttpSession(true);
    }

    @Bean
    public PrincipalExtractor principalExtractor(UserRepository userRepository) {
        return map -> {
            String email = (String) map.get("email");

            User user = userRepository.findByEmail(email).orElseGet(() -> {
                User newUser = new User();
                newUser.setLogin((String) map.get("name"));
                newUser.setEmail(email);
                newUser.setEnable(false); //need to edit information
                return newUser;
            });

            return userRepository.save(user);
        };
    }

}