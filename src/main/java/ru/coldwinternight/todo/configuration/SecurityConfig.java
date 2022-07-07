package ru.coldwinternight.todo.configuration;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.coldwinternight.todo.filter.CustomAuthenticationFilter;
import ru.coldwinternight.todo.filter.CustomAuthorizationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final Algorithm algorithm;
    private final JwtConfig jwtConfig;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder,
                          @Qualifier("userService") UserDetailsService userDetailsService,
                          Algorithm algorithm,
                          JwtConfig jwtConfig) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.algorithm = algorithm;
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter =
                new CustomAuthenticationFilter(authenticationManagerBean(), algorithm, jwtConfig);
//        customAuthenticationFilter.setFilterProcessesUrl("/api/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers("/", "/login").permitAll();
//        http.authorizeRequests().antMatchers("/", "/api/login", "/api/login/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterAfter(new CustomAuthorizationFilter(algorithm, jwtConfig), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
