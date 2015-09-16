package com.ai.przychodnia.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ai.przychodnia.service.UserService;

@Configuration
@EnableWebSecurity
@Order(1)
// @EnableGlobalMethodSecurity(prePostEnabled=true) np. @PreAuthorize("hasRole('ROLE_1')")
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	@Autowired
	UserService userDetailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(
				passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/").hasAnyRole("0","1","2")
				.antMatchers("/resources/**").permitAll()
				.antMatchers("/admin/**").hasRole("2")
				.antMatchers("/user/new-0","/login").permitAll()
				.antMatchers("/user/**").hasAnyRole("0", "2")
				.antMatchers("/doctor/**").hasRole("1")
				.anyRequest().authenticated()
				.and().formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/j_spring_security_check")
				.failureUrl("/login?error")
				.usernameParameter("j_username")
				.passwordParameter("j_password")
				.and().logout().logoutSuccessUrl("/login?logout")
				.and().csrf()
				//.and().exceptionHandling().accessDeniedPage("/loginError")
				;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
}