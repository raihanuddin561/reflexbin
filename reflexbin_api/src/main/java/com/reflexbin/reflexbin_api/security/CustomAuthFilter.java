package com.reflexbin.reflexbin_api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reflexbin.reflexbin_api.constant.ApplicationConstants;
import com.reflexbin.reflexbin_api.model.ErrorModel;
import com.reflexbin.reflexbin_api.model.LoginResponse;
import com.reflexbin.reflexbin_api.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Map;

/**
 * custom authentication filter class
 *
 * @author raihan
 */
@Slf4j
@RequiredArgsConstructor
public class CustomAuthFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    /**
     * overriding attemptAuthentication method
     *
     * @param request  from which to extract parameters and perform the authentication
     * @param response the response, which may be needed if the implementation has to do a
     *                 redirect as part of a multi-stage authentication process (such as OIDC).
     * @return Authentication
     * @throws AuthenticationException authentication failure exception
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException {
        String email = null, password = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, String> requestMap =
                    objectMapper.readValue(request.getInputStream(), Map.class);
            email = requestMap.get("email");
            password = requestMap.get("password");
            log.info("Login with: " + email);
            return authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (Exception e) {
            writeErrorResponse(e, response);
        }
        return null;
    }

    /**
     * successful authentication method
     *
     * @param request    HttpServletRequest
     * @param response   HttpServletResponse
     * @param chain      FilterChain
     * @param authResult the object returned from the <tt>attemptAuthentication</tt>
     *                   method.
     * @throws IOException      can be thrown IOException
     * @throws ServletException can be thrown ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String userName = authResult.getName();
        String token = jwtService.generateToken(userName);
        LoginResponse loginResponse = LoginResponse.builder()
                .token(token)
                .build();
        response.setContentType(ApplicationConstants.CONTENT_TYPE_JSON);
        new ObjectMapper().writeValue(response.getOutputStream(), loginResponse);
    }

    /**
     * unsuccessfulAuthentication method
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param failed   AuthenticationException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) {
        writeErrorResponse(failed, response);
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
