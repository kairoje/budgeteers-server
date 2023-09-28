package com.group.budgeteer.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    Logger logger = Logger.getLogger(JwtRequestFilter.class.getName());

    private final AuthUserDetailsService authUserDetailsService;

    private final JWTUtils jwtUtils;

    @Autowired
    public JwtRequestFilter(AuthUserDetailsService authUserDetailsService, JWTUtils jwtUtils){
        this.authUserDetailsService = authUserDetailsService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    }

    public String parseJwt(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");

        if(StringUtils.hasLength(authHeader) && authHeader.startsWith("Bearer")){
            return authHeader.substring(7);
        }
        logger.info("No header");
        return null;
    }
}
