package com.reflexbin.reflexbin_api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Map;

/**
 * custom authentication filter class
 * @author raihan
 */
@Slf4j
@RequiredArgsConstructor
public class CustomAuthFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    /**
     * overriding attemptAuthentication method
     * @param request from which to extract parameters and perform the authentication
     * @param response the response, which may be needed if the implementation has to do a
     * redirect as part of a multi-stage authentication process (such as OIDC).
     * @return Authentication
     * @throws AuthenticationException authentication failure exception
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException {
        String email = null, password = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            Map<String,String> requestMap =
                    objectMapper.readValue(request.getInputStream(), Map.class);
            email = requestMap.get("email");
            password = requestMap.get("password");
            log.info("Login with: "+email);
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(email,password));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }catch (AuthenticationException e){
            throw new RuntimeException("Bad credentials");
        }
        return null;
    }
}
