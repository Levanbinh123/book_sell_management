package com.example.book_sell_management.security;

import com.example.book_sell_management.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import java.util.Arrays;
@Configuration
@EnableWebSecurity
public class SecurityConfiguation {
    @Autowired
    private JwtFilter jwtFilter;
    private final AuthenticationConfiguration authenticationConfiguration;
    public SecurityConfiguation(AuthenticationConfiguration authenticationConfiguration) {
        this.authenticationConfiguration = authenticationConfiguration;
    }
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(UserService userService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return daoAuthenticationProvider;}
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(config -> {
            //public
            config.requestMatchers(HttpMethod.POST,Enpoints.PUBLIC_POST_ENDPOINS).permitAll().
                requestMatchers(HttpMethod.GET,Enpoints.PUBLIC_GET_ENDPOINS).permitAll()
                    .requestMatchers(HttpMethod.PUT,Enpoints.PUBLIC_PUT_ENDPOINS).permitAll()
                    .requestMatchers(HttpMethod.DELETE,Enpoints.PUBLIC_DELETE_ENDPOINS).permitAll()
                    //staff
                    .requestMatchers(HttpMethod.POST,Enpoints.STAFF_POST_ENDPOINS).hasAnyRole("ADMIN","STAFF").
                    requestMatchers(HttpMethod.GET,Enpoints.STAFF_GET_ENDPOINS).hasAnyRole("ADMIN","STAFF")
                    .requestMatchers(HttpMethod.PUT,Enpoints.STAFF_PUT_ENDPOINS).hasAnyRole("ADMIN","STAFF")
                    .requestMatchers(HttpMethod.DELETE,Enpoints.STAFF_DELETE_ENDPOINS).hasAnyRole("ADMIN","STAFF")
                    //userlogin
                    .requestMatchers(HttpMethod.POST,Enpoints.USERLOGIN_POST_ENDPOINS).hasAnyRole("USER","ADMIN","STAFF").
                    requestMatchers(HttpMethod.GET,Enpoints.USERLOGIN_GET_ENDPOINS).hasAnyRole("USER","ADMIN","STAFF")
                    .requestMatchers(HttpMethod.PUT,Enpoints.USERLOGIN_PUT_ENDPOINS).hasAnyRole("USER","ADMIN","STAFF")
                    .requestMatchers(HttpMethod.DELETE,Enpoints.USERLOGIN_DELETE_ENDPOINS).hasAnyRole("USER","ADMIN","STAFF")
                    //admin
                    .requestMatchers(HttpMethod.GET,Enpoints.ADMIN_GET_ENDPOINS).hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST,Enpoints.ADMIN_POST_ENDPOINS).hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT,Enpoints.ADMIN_PUT_ENDPOINS).hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE,Enpoints.ADMIN_DELETE_ENDPOINS).hasRole("ADMIN")
                    //swagger
                    .requestMatchers(
                            "/swagger-ui/**",
                            "/v3/api-docs/**",
                            "/swagger-ui.html"
                    ).permitAll().
                    anyRequest().authenticated();});
        http.cors(cors -> {
            cors.configurationSource(request -> {
                CorsConfiguration corsConfig = new CorsConfiguration();
                corsConfig.addAllowedOrigin(Enpoints.front_end_host);
                corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
                corsConfig.addAllowedHeader("*");
                return corsConfig;
            });
        });
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.csrf(csrf-> csrf.disable());
        http.exceptionHandling(exception -> exception
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\": \"Unauthorized\"}");
                })
        );
        return http.build();
    }
}
