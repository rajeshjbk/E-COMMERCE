package com.raj.ecommerce.securityConfig;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokensGeneratorFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
					throws ServletException, IOException {

		//Need to verify if there is an authentication object exists
		Authentication authObj = SecurityContextHolder.getContext().getAuthentication();

		if(authObj != null) {
			
			//This means user passed authentication sucessfully
			SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes());   

			String jwtToken = Jwts.builder().setIssuer("RAJIT ECOMMERCE")
					.setSubject(authObj.getName())
					/*.setSubject("JWT TOKEN")
					.claim("username", authObj.getName())*/
					
					.claim("authorities", populateAuthorities(authObj.getAuthorities()))
					.setIssuedAt(new Date())
					.setExpiration(new Date(new Date().getTime() + 30000000))
					.signWith(key).compact();

			response.setHeader(SecurityConstants.JWT_HEADER, jwtToken);		 
		}
		filterChain.doFilter(request, response);  //Regular work
	}

	private String populateAuthorities(Collection<? extends GrantedAuthority> roles) {

		Set<String> rolesList = new HashSet<>();
		for(GrantedAuthority role : roles) {

			rolesList.add(role.getAuthority());
		}
		return String.join(",", rolesList);   //ADMIN GUEST SUPERADMIN

	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		
		return !request.getServletPath().equals("/ecom/signIn");
	}
}
