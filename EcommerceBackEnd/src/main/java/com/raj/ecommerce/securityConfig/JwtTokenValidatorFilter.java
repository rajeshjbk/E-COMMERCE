package com.raj.ecommerce.securityConfig;

import java.io.IOException;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenValidatorFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//here we will write code to validate the token
		//Authorization as HeaderName and value will be bearer + " " + TOKEN
		String jwtToken = request.getHeader(SecurityConstants.JWT_HEADER);  //bearer + " " + TOKEN 
		
		if(jwtToken != null) {
			
			try {
				
				jwtToken = jwtToken.substring(7); //This will trim bearer + " "
				SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes());
				
				Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwtToken).getBody();
				
				String userName = String.valueOf(claims.get("username"));
				String authorities = (String) claims.get("authorities");
			
				Authentication authObj = new UsernamePasswordAuthenticationToken(userName, null,
						     AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
				
				SecurityContextHolder.getContext().setAuthentication(authObj);
				
			}catch (Exception e) {
				
				throw new BadCredentialsException("Invalid Token Received");
			}
			filterChain.doFilter(request, response);
		}
	}	
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		
		return !request.getServletPath().equals("/ecom/signIn");
	}

}
