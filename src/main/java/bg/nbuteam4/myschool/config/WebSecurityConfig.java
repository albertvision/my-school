package bg.nbuteam4.myschool.config;

import bg.nbuteam4.myschool.entity.Role;
import bg.nbuteam4.myschool.entity.User;
import bg.nbuteam4.myschool.repository.UserDetailsManager;
import bg.nbuteam4.myschool.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.HashMap;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder
    ) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public UserDetailsService userDetailsService(
            UserDetailsManager userDetailsManager,
            PasswordEncoder passwordEncoder
    ) {
        try {
            Arrays.stream(Role.values())
                    .map(it -> {
                        String roleString = it.name().toLowerCase();

                        return new User()
                                .setUsername(roleString)
                                .setPassword(passwordEncoder.encode(roleString))
                                .setRole(it);
                    })
                    .forEach(userDetailsManager::createUser);
        } catch (DataIntegrityViolationException e) {
            // nothing to do as users already exist
        }

        return userDetailsManager;
    }

    @Bean
    public UserDetailsManager userDetailsManager(UserRepository userRepository) {
        return new UserDetailsManager(userRepository);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        String defaultEncoderId = "bcrypt";
        HashMap<String, PasswordEncoder> encoders = new HashMap<>() {{
            put(defaultEncoderId, new BCryptPasswordEncoder());
        }};

        return new DelegatingPasswordEncoder(defaultEncoderId, encoders);
    }
}
