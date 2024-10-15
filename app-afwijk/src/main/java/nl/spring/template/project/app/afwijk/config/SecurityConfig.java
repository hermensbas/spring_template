package nl.spring.template.project.app.afwijk.config;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {

        http
            //           .csrf(csrf -> csrf.csrfTokenRepository
//                (CookieCsrfTokenRepository.withHttpOnlyFalse()))
            .csrf(withDefaults())
            .headers(withDefaults())
            .authorizeHttpRequests(config -> config
                .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll() // required for servlet response error handling
                .requestMatchers("/actuator/**").permitAll()
                .requestMatchers("/public/**").permitAll()
                .anyRequest()
                .authenticated())
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
            .logout(withDefaults())
            .httpBasic(withDefaults());

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {

        return new ProviderManager(Collections.singletonList((inMemoryUserDetailsAuthProvider())));
    }

    public AuthenticationProvider inMemoryUserDetailsAuthProvider() {

        var authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(inMemoryUserDetailsManager());
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }

    public UserDetailsManager inMemoryUserDetailsManager() {

        final InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        final var userDetailsBuilder = User.builder().passwordEncoder(passwordEncoder()::encode);

        final UserDetails newAdmin = userDetailsBuilder
            .username("admin")
            .password("admin")
            .roles("admin")
            .build();
        inMemoryUserDetailsManager.createUser(newAdmin);

        final UserDetails newUser = userDetailsBuilder
            .username("user")
            .password("user")
            .roles("user")
            .build();
        inMemoryUserDetailsManager.createUser(newUser);

        return inMemoryUserDetailsManager;
    }

    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
