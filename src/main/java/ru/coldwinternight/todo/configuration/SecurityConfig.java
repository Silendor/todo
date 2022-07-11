package ru.coldwinternight.todo.configuration;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.coldwinternight.todo.filter.CorsSecurityWebFilter;
import ru.coldwinternight.todo.filter.JwtUsernameAndPasswordAuthenticationFilter;
import ru.coldwinternight.todo.filter.JwtTokenVerifierAuthorizationFilter;
import ru.coldwinternight.todo.service.UserService;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final Algorithm algorithm;
    private final JwtConfig jwtConfig;
    private final CorsConfig corsConfig;

    @Autowired
    public SecurityConfig(UserService userService,
                          PasswordEncoder passwordEncoder,
                          Algorithm algorithm,
                          JwtConfig jwtConfig,
                          CorsConfig corsConfig) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.algorithm = algorithm;
        this.jwtConfig = jwtConfig;
        this.corsConfig = corsConfig;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JwtUsernameAndPasswordAuthenticationFilter jwtUsernameAndPasswordAuthenticationFilter =
                new JwtUsernameAndPasswordAuthenticationFilter(authenticationManagerBean(),
                        algorithm, jwtConfig, userService);
        JwtTokenVerifierAuthorizationFilter jwtTokenVerifierAuthorizationFilter =
                new JwtTokenVerifierAuthorizationFilter(algorithm, jwtConfig);
        CorsSecurityWebFilter corsSecurityWebFilter = new CorsSecurityWebFilter(corsConfig.corsConfigurationSource());
//        customAuthenticationFilter.setFilterProcessesUrl("/api/login");
        http
            .csrf().disable()
            .sessionManagement()
                .sessionCreationPolicy(STATELESS)
            .and()
            .addFilterBefore(corsSecurityWebFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilter(jwtUsernameAndPasswordAuthenticationFilter)
            .addFilterAfter(jwtTokenVerifierAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeRequests()
                .antMatchers("/", "/login").permitAll()
                .antMatchers(POST, "/users").permitAll()
//        http.authorizeRequests().antMatchers("/", "/api/login", "/api/login/**").permitAll();
                .anyRequest().authenticated();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
