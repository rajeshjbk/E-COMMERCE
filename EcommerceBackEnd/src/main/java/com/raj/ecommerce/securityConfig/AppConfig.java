package com.raj.ecommerce.securityConfig;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class AppConfig {

	@Bean
	public SecurityFilterChain springSecurityConfiguration(HttpSecurity http) throws Exception {

		http.sessionManagement(
				sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				.cors(cors -> {
					cors.configurationSource(new CorsConfigurationSource() {
						@Override
						public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

							CorsConfiguration cfg = new CorsConfiguration();

							cfg.setAllowedOriginPatterns(Collections.singletonList("*"));
//                            cfg.setAllowedOriginPatterns(Collections.singletonList("https://eccomers96.netlify.app"));
							cfg.setAllowedOriginPatterns(Collections.singletonList("http://localhost:3000"));
							cfg.setAllowedMethods(Collections.singletonList("*"));

							cfg.setAllowCredentials(true);
							cfg.setAllowedHeaders(Collections.singletonList("*"));
							cfg.setExposedHeaders(Arrays.asList("Authorization"));

							return cfg;

						}
					});
				})
				/*.authorizeHttpRequests(auth -> {
				    auth
				            .requestMatchers(HttpMethod.POST, "/ecom/admin/signIn").permitAll()
				            .requestMatchers(HttpMethod.POST, "/ecom/customers/addUser").permitAll()
				            .requestMatchers(HttpMethod.DELETE, "/ecom/orders/delete/**").permitAll()
				            .requestMatchers(HttpMethod.GET, "/ecom/signIn", "/ecom/product-reviews/**","/ecom/products/**").permitAll()
				
				            .requestMatchers(
				                    HttpMethod.POST,
				                    "/ecom/products/**",
				                    "/ecom/order-shippers/**"
				
				            ).hasRole("ADMIN")
				            
				            .requestMatchers(
				                    HttpMethod.POST,
				                   "/ecom/product-reviews/**",
				                    "/ecom/customer-address/**",
				                    "/ecom/cart/**",
				                    "/ecom/orders/placed/**",
				                    "/ecom/order-shipping/**"
				            ).hasRole("USER")
				            
				            .requestMatchers(
				                    HttpMethod.PUT,
				                    "/ecom/admin/**",
				                    "/ecom/products/**"
				
				            ).hasRole("ADMIN")
				            
				            .requestMatchers(
				                    HttpMethod.PUT,
				                    "/ecom/product-reviews/**",
				                    "/ecom/customer-address/update/**",
				                    "/ecom/cart/**", "/ecom/order-shipping/**"
				
				            ).hasRole("USER")
				
				            .requestMatchers(
				                    HttpMethod.DELETE,
				                    "/ecom/products/**",
				                    "/ecom/product-reviews/**",
				                    "/ecom/customer-address/delete/**",
				//                                    "/ecom/orders/users/**",
				                    "/ecom/order-shipping/**",
				                    "/ecom/order-shippers/**"
				
				            ).hasRole("ADMIN")
				            
				            .requestMatchers(
				                    HttpMethod.DELETE,
				                    "/ecom/cart/remove-product/**",
				                    "/ecom/orders/delete/**"
				            ).hasRole("USER")
				
				            .requestMatchers(
				                    HttpMethod.GET,
				                    "/ecom/products/**",
				                    "/ecom/customer-address/**",
				                    "/ecom/cart/products/**",
				                    "/ecom/orders/**",
				                    "/ecom/order-shippers/**",
				                    "/ecom/order-payments/**"
				
				            ).hasAnyRole("ADMIN", "USER")
				
				            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
				            .anyRequest().authenticated();
				})*/

				.authorizeHttpRequests(auth -> {

					auth

							// PUBLIC APIs
							.requestMatchers(HttpMethod.POST, "/ecom/admin/signIn", "/ecom/customers/addUser")
							.permitAll()

							.requestMatchers(HttpMethod.GET, "/ecom/signIn", "/ecom/products/**",
									"/ecom/product-reviews/**")
							.permitAll()

							// ================= ADMIN =================

							// Product Management
							.requestMatchers(HttpMethod.POST, "/ecom/products/**").hasRole("ADMIN")

							.requestMatchers(HttpMethod.PUT, "/ecom/products/**").hasRole("ADMIN")

							.requestMatchers(HttpMethod.DELETE, "/ecom/products/**").hasRole("ADMIN")

							// View ALL orders
							.requestMatchers(HttpMethod.GET, "/ecom/orders/all").hasRole("ADMIN")

							// View ALL customers
							.requestMatchers(HttpMethod.GET, "/ecom/customers/all").hasRole("ADMIN")

							// ================= USER =================

							.requestMatchers(HttpMethod.POST, "/ecom/cart/**", "/ecom/orders/placed/**",
									"/ecom/customer-address/**", "/ecom/product-reviews/**")
							.hasRole("USER")

							.requestMatchers(HttpMethod.PUT, "/ecom/cart/**", "/ecom/customer-address/**",
									"/ecom/product-reviews/**", "/ecom/customers/updatedPassword/**")
							.hasRole("USER")

							.requestMatchers(HttpMethod.DELETE, "/ecom/cart/**",
									"/ecom/customer-address/delete/**", "/ecom/orders/delete/**")
							.hasRole("USER")

							// USER + ADMIN common APIs
							.requestMatchers(HttpMethod.GET, "/ecom/cart/products/**", "/ecom/customer-address/**",
									"/ecom/order-payments/**")
							.hasAnyRole("ADMIN", "USER")

							// USER can see only own orders
							.requestMatchers(HttpMethod.GET, "/ecom/orders/user/**").hasRole("USER")

							// Swagger
							.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

							.anyRequest().authenticated();
					
				}).csrf(csrf -> csrf.disable())
				.addFilterAfter(new JwtTokensGeneratorFilter(), BasicAuthenticationFilter.class)
				.addFilterBefore(new JwtTokenValidatorFilter(), BasicAuthenticationFilter.class)
				.httpBasic(Customizer.withDefaults());

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();

	}
}
