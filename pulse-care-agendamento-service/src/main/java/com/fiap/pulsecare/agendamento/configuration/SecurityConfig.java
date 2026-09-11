package com.fiap.pulsecare.agendamento.configuration;

import com.fiap.pulsecare.core.security.JwtAuthFilter;
import com.fiap.pulsecare.core.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtUtil jwtUtil;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) {
		return http
				.csrf(AbstractHttpConfigurer::disable)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/auth/login").permitAll()
						.requestMatchers(HttpMethod.POST, "/consultas").hasRole("MEDICO")
						.requestMatchers(HttpMethod.PUT, "/consultas/**").hasAnyRole("MEDICO", "ENFERMEIRO")
						.requestMatchers(HttpMethod.GET, "/consultas/**").hasAnyRole("MEDICO", "ENFERMEIRO", "PACIENTE")
						.anyRequest().authenticated())
				.addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
				.build();
	}

}
