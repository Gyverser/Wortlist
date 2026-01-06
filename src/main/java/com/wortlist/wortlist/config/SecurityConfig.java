package com.wortlist.wortlist.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.http.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/words").hasAnyAuthority("ROLE_ADMIN", "ROLE_STUDENT")
                .requestMatchers(HttpMethod.GET, "/api/words/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_STUDENT")
                .requestMatchers(HttpMethod.POST, "/api/words/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/words/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/api/words/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/words/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/**").permitAll()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/words", true)
                .permitAll()
            )
            .logout(LogoutConfigurer::permitAll
            )
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/api/**")
            );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        Dotenv dotenv = Dotenv.load();
        UserDetails admin = User.withUsername(dotenv.get("SECURITY_USER_NAME"))
            .password(dotenv.get("SECURITY_USER_PASSWORD"))
            .roles(dotenv.get("SECURITY_USER_ROLES"))
            .build();

        UserDetails student = User.withUsername("student")
            .password(passwordEncoder.encode("student"))
            .roles("STUDENT")
            .build();

        return new InMemoryUserDetailsManager(admin, student);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}