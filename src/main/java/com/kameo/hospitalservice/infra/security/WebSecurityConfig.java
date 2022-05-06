package com.kameo.hospitalservice.infra.security;

import com.kameo.hospitalservice.staff.boundary.StaffMemberController;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final IdToPrincipalResolver idToPrincipalResolver;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(new HospitalAuthenticationFilter(idToPrincipalResolver), BasicAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, StaffMemberController.STAFF_MEMBERS_URL).permitAll()
                .anyRequest().authenticated()
                .and().httpBasic().disable().csrf().disable()
                .headers().frameOptions().sameOrigin(); // h2-console
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication();
    }
}
