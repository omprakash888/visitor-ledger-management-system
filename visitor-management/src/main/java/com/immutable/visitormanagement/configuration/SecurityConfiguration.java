package com.immutable.visitormanagement.configuration;

import com.immutable.visitormanagement.filter.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.immutable.visitormanagement.constants.Constants.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {


    private static final String[] WHITE_LIST_URLS_FOR_ALL = {CREATE_URL_USER,MAIN_URL_LOGIN,CREATE_URL_VISITOR,CHECKOUT_URL_VISITOR,
                                                             ACTIVATE_ACCOUNT_URL_USER, FORGOT_PASSWORD_URL_USER,RESET_PASSWORD_URL_USER,
                                                             GET_BY_ID_FOR_VISITOR_EMPLOYEE,};
    private static final String[] WHITE_LIST_URLS_FOR_ADMIN = {GET_ALL_URL_VISITOR,GET_BY_ID_URL_VISITOR,CREATE_URL_EMPLOYEE,
                                                              GET_ALL_URL_EMPLOYEE,GET_BY_ID_URL_EMPLOYEE,UPDATE_URL_EMPLOYEE,DELETE_URL_EMPLOYEE,
                                                                DASHBOARD_URL_VISITOR,SEND_EMAIL_URL_USER,GET_VISITOR_ORGANIZATION,DOWNLOAD_REPORTS};
    @Autowired
    private JwtAuthFilter authFilter;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService userDetailsService;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(auth -> auth.requestMatchers(WHITE_LIST_URLS_FOR_ADMIN).authenticated().requestMatchers(WHITE_LIST_URLS_FOR_ALL).permitAll()
                        .requestMatchers("/v3/api-docs",
                                "/configuration/ui",
                                "/swagger-resources/**",
                                "/configuration/security",
                                "/swagger-ui/**",
                                "/webjars/**")
                        .permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }

    @Bean
    public DaoAuthenticationProvider doDaoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
