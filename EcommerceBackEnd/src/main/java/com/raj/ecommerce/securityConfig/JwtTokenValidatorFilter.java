package com.raj.ecommerce.securityConfig;

import java.io.IOException;


import javax.crypto.SecretKey;

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

public class JwtTokenValidatorFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String jwtToken = request.getHeader(SecurityConstants.JWT_HEADER);

        if (jwtToken != null && jwtToken.startsWith("Bearer ")) {

            try {

                // Remove Bearer
                jwtToken = jwtToken.substring(7);

                // Generate Secret Key
                SecretKey key = Keys.hmacShaKeyFor(
                        SecurityConstants.JWT_KEY.getBytes());

                // Validate Token
                Claims claimObj = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(jwtToken)
                        .getBody();

                // Fetch Data
                String userName = claimObj.getSubject();

                String userRoles =
                        String.valueOf(claimObj.get("authorities"));

                // Create Authentication Object
                Authentication authObj =
                        new UsernamePasswordAuthenticationToken(
                                userName,
                                null,
                                AuthorityUtils
                                        .commaSeparatedStringToAuthorityList(userRoles)
                        );

                // Set Authentication
                SecurityContextHolder.getContext()
                        .setAuthentication(authObj);
                
                System.out.println(jwtToken);
                System.out.println(userName);
                System.out.println(userRoles);
                
            } catch (Exception ex) {

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                response.getWriter()
                        .write("Invalid Token Received");

                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request)
            throws ServletException {

        return request.getServletPath().equals("/ecom/signIn");
    }
}