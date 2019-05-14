package id.bts.userShopping.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import id.bts.userShopping.interceptor.AuthenticationTokenFilter;
import id.bts.userShopping.security.CsrfHeaderFilter;
import id.bts.userShopping.security.JwtAuthenticationEntryPoint;
import id.bts.userShopping.security.JwtTokenUtil;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final UserDetailsService userDetailsService;
	private final JwtAuthenticationEntryPoint authenticationEntryPoint;
	private final JwtTokenUtil jwtTokenUtil;

	public SecurityConfiguration(UserDetailsService userDetailsService,
			JwtAuthenticationEntryPoint authenticationEntryPoint, JwtTokenUtil jwtTokenUtil) {
		super();
		this.userDetailsService = userDetailsService;
		this.authenticationEntryPoint = authenticationEntryPoint;
		this.jwtTokenUtil = jwtTokenUtil;
	}

	// Configure root AuthenticationManager
	@Autowired
	public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder
			.userDetailsService(this.userDetailsService)
			.passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		
		return super.authenticationManagerBean();
	}
	
	@Bean
	public AuthenticationTokenFilter authenticationTokenFilterBean() {
		return new AuthenticationTokenFilter(userDetailsService, jwtTokenUtil);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			// security config stop Blocking H2 Console
//			.headers()
//				.frameOptions()
//					.sameOrigin()
//			.and()
			.csrf()
				.disable()
			.exceptionHandling()
				.authenticationEntryPoint(authenticationEntryPoint)
			.and()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()
				.antMatchers("/**")
					.permitAll()
				.antMatchers("/api/users/signup/")
					.permitAll()
				.antMatchers("/v1/login")
					.permitAll()
				.anyRequest()
					.authenticated()
				.antMatchers(HttpMethod.OPTIONS, "/**")
					.permitAll()
				;
		
		http
			.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class)
			.addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class);
		
		http
			.headers()
				.cacheControl();
		
		http
			.headers()
				.httpStrictTransportSecurity()
					.includeSubDomains(true)
					.maxAgeInSeconds(31536000);
	}
	
	@Bean
	public CorsFilter corsFilter() {
		
		final CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedHeader("*");
		config.addAllowedOrigin("*");
		config.addAllowedMethod("OPTIONS");
		config.addAllowedMethod("POST");
		config.addAllowedMethod("GET");
		config.addAllowedMethod("PUT");
		config.addAllowedMethod("DELETE");
		
		final UrlBasedCorsConfigurationSource source = 
				new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		
		return new CorsFilter(source);
	}
}
