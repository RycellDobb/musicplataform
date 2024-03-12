package com.dagarcvj.music.plataform.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dagarcvj.music.plataform.User.UserRepository;

import lombok.RequiredArgsConstructor;
/**
 * 
 * @file: ApplicationConfig.java
 * @author: (c)2024 Cleysi
 * @created: 2 mar 2024, 0:55:01
 *
 */

/**
 * Configuración de seguridad para la autenticación y autorización.
 */

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
	
	private final UserRepository userRepository;
	
	/**
     * Método que devuelve una instancia de ModelMapper, que se utiliza para mapear objetos de un tipo a otro.
     *
     * @return Una instancia de ModelMapper para realizar mapeos entre objetos.
     */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	/**
     * Bean para proporcionar un administrador de autenticación.
     *
     * @param config Configuración de autenticación.
     * @return Un administrador de autenticación.
     * @throws Exception Si hay un error al obtener el administrador de autenticación.
     */
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
		return config.getAuthenticationManager();
	}
	/**
     * Bean para proporcionar un proveedor de autenticación personalizado.
     *
     * @return Un proveedor de autenticación.
     */
	@Bean
	public AuthenticationProvider authenticationProvider () {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
    /**
     * Bean para proporcionar un codificador de contraseñas.
     *
     * @return Un codificador de contraseñas.
     */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
    /**
     * Bean para proporcionar un servicio de detalles de usuario personalizado.
     *
     * @return Un servicio de detalles de usuario personalizado.
     */
	@Bean
	public UserDetailsService userDetailsService() {
		return username -> userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}
	
}
