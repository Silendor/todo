package ru.coldwinternight.todo.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.coldwinternight.todo.configuration.JwtConfig;
import ru.coldwinternight.todo.entity.UserEntity;
import ru.coldwinternight.todo.exception.UserNotFoundException;
import ru.coldwinternight.todo.service.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final Algorithm algorithm;
    private final JwtConfig jwtConfig;
    private final UserService userService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authentication) throws IOException, ServletException {
        Map<String, String> answer = new HashMap<>();
        UserEntity userEntity = null;
        Integer userId = null;
        String userName = null;

        try {
            User user = (User) authentication.getPrincipal();
            userEntity = userService.loadUserEntityByEmail(user.getUsername());

            if (userEntity.getId() != null)
                userId = userEntity.getId();
            else
                throw new UserNotFoundException();

            userName = userEntity.getUsername();

            String access_token = JWT.create()
                    .withClaim("name", userName)
                    .withClaim("id", userId)
//                    .withSubject(userIdString)
                    .withExpiresAt(Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays())))
                    .withIssuer(request.getRequestURI())
                    // no roles
    //                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                    .sign(algorithm);

            response.addHeader(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix() + access_token);

//            answer.put("user_id", userId.toString());
            answer.put(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix() + access_token);
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), answer);
        } catch (UserNotFoundException userNotFoundException) {
            response.setHeader("error", userNotFoundException.getMessage());
            response.setStatus(FORBIDDEN.value());
            answer.put("error_message", userNotFoundException.getMessage());
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), answer);
        }
    }
}
