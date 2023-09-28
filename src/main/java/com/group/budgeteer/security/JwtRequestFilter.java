package com.group.budgeteer.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * The JwtRequestFilter class validates the JWT token.
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    Logger logger = Logger.getLogger(JwtRequestFilter.class.getName());

    private AuthUserDetailsService authUserDetailsService;

    private JWTUtils jwtUtils;

    /**
     * Sets auth user details service
     * @param authUserDetailsService Auth user details service
     */
    @Autowired
    public void setAuthUserDetailsService(AuthUserDetailsService authUserDetailsService) {
        this.authUserDetailsService = authUserDetailsService;
    }

    /**
     * Sets Jwt Utils
     * @param jwtUtils This is the jwtUtils
     */
    @Autowired
    public void setJwtUtils(JWTUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    /**
     * doFilterInternal filters incoming Http request, validates the JWT token, and sets the users authentication details
     * @param request This is the HttpServletRequest to filter.
     * @param response This is the HttpServletResponse.
     * @param filterChain The filter chain to continue processing requests.
     * @throws ServletException If an error occurs during processing
     * @throws IOException If an I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String jwt = parseJwt(request);

            if (jwt != null && jwtUtils.validateJwt(jwt)){
                String username = jwtUtils.getUsernameFromJwt(jwt);
                UserDetails userDetails = this.authUserDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (Exception e) {
            logger.info("Cannot set user authentication token");
        }
        filterChain.doFilter(request, response);
    }

    /**
     * parseJwt parses JWT token from the request header.
     * @param request HttpServletRequest containing the Authorization Header.
     * @return The JWT token if found in the authorization header, or null if not found.
     */
    public String parseJwt(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");

        if(StringUtils.hasLength(authHeader) && authHeader.startsWith("Bearer")){
            return authHeader.substring(7);
        }
        logger.info("No header");
        return null;
    }
}
