package org.example.springsecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
 @Autowired
 DataSource dataSource ;
 @Bean
 SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
  http.authorizeHttpRequests((requests) -> requests.requestMatchers("/h2-console/**").permitAll().anyRequest().authenticated());
  http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//  http.formLogin(withDefaults());
  http.httpBasic(withDefaults());
  http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
  http.csrf(csrf -> csrf.disable());
  return http.build();


 }
 @Bean
 public UserDetailsService userDetailsService() {
  UserDetails user1 = User.withUsername("user1").
       password("{noop}123").roles("user").build();
  UserDetails admin = User.withUsername("admin").
       password("{noop}123").roles("ADMIN").build();

  JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
//  return new InMemoryUserDetailsManager(user1, admin); keep the user data at the application RAM
userDetailsManager.createUser(admin);
userDetailsManager.createUser(user1);
  return userDetailsManager;
 }
}
