package com.kayadhu.authUsernamePass.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.kayadhu.authUsernamePass.service.CustomUserDetailsService;

@Configuration
public class SecurityConfig {

	@Autowired
	private CustomUserDetailsService userService;
	
	@Autowired
	private CustomSuccessHandler successHandler;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider1() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }
  
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {    
    	http.authenticationProvider(authenticationProvider1());
    	http
      .authorizeRequests()
      .antMatchers("/register/**").permitAll()
      .antMatchers("/login/**").permitAll()
      .anyRequest().fullyAuthenticated()
      
      .and()
      .formLogin(
              form -> form
                      .loginPage("/login")
                      .loginProcessingUrl("/login")  
                      .permitAll().successHandler(successHandler)
      )
      .logout(
              logout -> logout
                      .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                      .permitAll()

      ).csrf().disable();
        return http.build();

    } 
    
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
	
}
