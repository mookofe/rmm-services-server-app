package me.victorcruz.ninjaserver.api.v1.filters;

import java.util.Date;
import com.auth0.jwt.JWT;
import java.io.IOException;
import lombok.SneakyThrows;
import java.util.ArrayList;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import me.victorcruz.ninjaserver.constants.SecurityConstants;
import me.victorcruz.ninjaserver.domain.models.ApplicationUser;
import me.victorcruz.ninjaserver.api.v1.responses.ErrorResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.AuthenticationManager;
import me.victorcruz.ninjaserver.api.v1.responses.SuccessAuthenticationResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    ObjectMapper objectMapper = new ObjectMapper();
    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;

        setFilterProcessesUrl(SecurityConstants.LOGIN_URL);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {
        try {
            ApplicationUser credentials = new ObjectMapper().readValue(
                    request.getInputStream(),
                    ApplicationUser.class
            );

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    credentials.getUsername(),
                    credentials.getPassword(),
                    new ArrayList<>()
            );

            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication auth
    ) throws IOException, ServletException {
        String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .sign(HMAC512(SecurityConstants.SECRET.getBytes()));

        response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
        prepareSuccessResponse(response, auth, SecurityConstants.TOKEN_PREFIX + token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        ErrorResponse errorResponse = new ErrorResponse(
                failed.getMessage(),
                HttpStatus.UNAUTHORIZED.value()
        );

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(), errorResponse);
    }

    @SneakyThrows
    private void prepareSuccessResponse(HttpServletResponse response, Authentication auth, String token) {
        User user = (User)auth.getPrincipal();

        SuccessAuthenticationResponse successResponse = SuccessAuthenticationResponse.builder()
                .token(token)
                .username(user.getUsername())
                .build();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(), successResponse);
    }
}
