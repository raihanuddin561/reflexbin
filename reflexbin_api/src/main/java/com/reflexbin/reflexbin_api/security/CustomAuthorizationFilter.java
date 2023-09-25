package com.reflexbin.reflexbin_api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reflexbin.reflexbin_api.constant.ApplicationConstants;
import com.reflexbin.reflexbin_api.model.ErrorModel;
import com.reflexbin.reflexbin_api.service.JWTService;
import com.reflexbin.reflexbin_api.service.impl.UserServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

/**
 * CustomAuthorizationFilter for authentication/authorization by token
 *
 * @author raihan
 */
@Slf4j
@RequiredArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {
    private final JWTService jwtService;
    private final UserServiceImpl userServiceImpl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(ApplicationConstants.AUTHORIZATION_HEADER);
        if (header == null || !header.startsWith(ApplicationConstants.BEARER)) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = header.substring(7);
        log.info("Extracting user from token...");
        String username = null;
        try {
            username = jwtService.extractUsername(token);
        } catch (ExpiredJwtException exception) {
            writeErrorResponse(exception, response);
            return;
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            log.info("Extracted username.");
            UserDetails userDetails = this.userServiceImpl.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
            authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );
            SecurityContextHolder.getContext().setAuthentication(authToken);
            log.info("AuthToken prepared.");
        }
        filterChain.doFilter(request, response);
    }

    /**
     * writeErrorResponse method
     *
     * @param e        any exception
     * @param response HttpServletResponse
     */
    private void writeErrorResponse(Exception e, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        ErrorModel errorModel = ErrorModel.builder()
                .status(ApplicationConstants.STATUS_FAILED)
                .error(Map.of("code", HttpStatus.BAD_REQUEST.value(), ApplicationConstants.MESSAGE, e.getLocalizedMessage()))
                .build();
        response.setContentType(ApplicationConstants.CONTENT_TYPE_JSON);
        try {
            new ObjectMapper().writeValue(response.getOutputStream(), errorModel);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
