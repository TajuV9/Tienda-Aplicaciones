package com.eviden.fct.tiendaaplicaciones.application.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.eviden.fct.tiendaaplicaciones.application.security.CustomAccessDeniedHandler;
import com.eviden.fct.tiendaaplicaciones.application.security.CustomAuthenticationEntryPoint;
import com.eviden.fct.tiendaaplicaciones.application.security.JwtAthenticationFilter;
import com.eviden.fct.tiendaaplicaciones.application.services.AuthenticationService;
import com.eviden.fct.tiendaaplicaciones.domain.appservices.AuthenticationApplicationService;
import com.eviden.fct.tiendaaplicaciones.domain.appservices.JwtApplicationService;
import com.eviden.fct.tiendaaplicaciones.domain.repositories.UserRepository;
import com.eviden.fct.tiendaaplicaciones.domain.services.UserDomainService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

	private final AuthenticationConfiguration authConfig;
	private final JwtApplicationService jwtService;
	private final UserRepository userRepository;

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http, CustomAuthenticationEntryPoint authEntryPoint,
			CustomAccessDeniedHandler accessDeniedHandler) throws Exception {
		return http
				.csrf(c -> c.disable())
				.cors(_ -> corsConfigurationSource())
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("/swagger-ui/**").permitAll()
						.requestMatchers("/v3/api-docs/**").permitAll()
						.requestMatchers("/v1/auth/**").permitAll()
						
						.requestMatchers(HttpMethod.GET, "/v1/content").permitAll()
						.requestMatchers(HttpMethod.GET, "/v1/content/{id}").permitAll()
						.requestMatchers(HttpMethod.GET, "/v1/content/{id}/icon").permitAll()
						.requestMatchers(HttpMethod.GET, "/v1/content/{id}/review").permitAll()
						.requestMatchers(HttpMethod.GET, "/v1/content/{id}/file").authenticated()
						.requestMatchers("/v1/content/{id}/review").authenticated()
						.requestMatchers("/v1/content/**").hasAnyRole("CREATOR", "ADMIN")
						
						.requestMatchers(HttpMethod.GET, "/v1/content-media/{id}").permitAll()
						.requestMatchers(HttpMethod.DELETE, "/v1/content-media/{id}").hasAnyRole("CREATOR", "ADMIN")
						
						.requestMatchers("/v1/review/**").hasAnyRole("CREATOR", "ADMIN")
						
						.anyRequest().authenticated())
				.exceptionHandling(
						e -> e.authenticationEntryPoint(authEntryPoint).accessDeniedHandler(accessDeniedHandler))
				.formLogin(form -> form.disable()).httpBasic(basic -> basic.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class).build();
	}
	
	@Bean UserDomainService userDomainService() {
		return new UserDomainService(userRepository, passwordEncoder());
	}

	@Bean
	JwtAthenticationFilter jwtFilter() throws Exception {
		return new JwtAthenticationFilter(authService());
	}

	@Bean
	AuthenticationApplicationService authService() throws Exception {
		return new AuthenticationService(authenticationManager(authConfig), jwtService, userDomainService());
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDomainService());
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration();
	    configuration.addAllowedOrigin("http://localhost:4200");
	    configuration.addAllowedMethod("*");
	    configuration.addAllowedHeader("*");
	    configuration.addExposedHeader("Content-Disposition");
	    configuration.setAllowCredentials(true);
 
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
	    return source;
	}

}
