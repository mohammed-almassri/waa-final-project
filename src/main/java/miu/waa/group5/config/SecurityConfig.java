package miu.waa.group5.config;

import lombok.RequiredArgsConstructor;
import miu.waa.group5.exception.CustomAccessDeniedHandler;
import miu.waa.group5.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final JwtFilter jwtFilter;

    private final CustomAccessDeniedHandler accessDeniedHandler;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests( configurer ->
                configurer
                        .requestMatchers("/actuator/health").permitAll()
                        .requestMatchers("/api/admins/login").permitAll()
                        .requestMatchers("/api/customers/properties").permitAll()
                        .requestMatchers("/api/media/upload").permitAll()
                        .requestMatchers("/api/customers/properties/**").permitAll()
                        .requestMatchers("/api/owners/login", "/api/owners/signup").permitAll()
                        .requestMatchers("/api/customers/login", "/api/customers/signup").permitAll()
                        .requestMatchers("/api/admins/**").hasRole("ADMIN")
                        .requestMatchers("/api/owners/**").hasRole("OWNER")
                        .requestMatchers("/api/customers/**").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.GET,"/").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/media/upload").permitAll()
                        .anyRequest().authenticated()
        );
        http.exceptionHandling(exceptions -> exceptions
                .accessDeniedHandler(accessDeniedHandler));

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.csrf(csrf->csrf.disable());
        http.cors(Customizer.withDefaults()); // Enable CORS using the custom configuration below

//        var ctx = SecurityContextHolder.getContext();
//        System.out.println(ctx.getAuthentication().getPrincipal());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsSvc() {
        return userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsSvc());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000","https://waa-final-project-fe.vercel.app")); // Allow requests from React dev server
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true); // Allow credentials such as cookies
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply CORS configuration to all endpoints
        return source;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

}
