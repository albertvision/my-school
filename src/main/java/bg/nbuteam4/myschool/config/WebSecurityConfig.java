package bg.nbuteam4.myschool.config;

import bg.nbuteam4.myschool.entity.User;
import bg.nbuteam4.myschool.enums.Role;
import bg.nbuteam4.myschool.repository.SchoolRepository;
import bg.nbuteam4.myschool.repository.StudyPeriodRepository;
import bg.nbuteam4.myschool.repository.UserDetailsManager;
import bg.nbuteam4.myschool.repository.UserRepository;
import bg.nbuteam4.myschool.resolver.GlobalFiltersResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig implements WebMvcConfigurer {
    private final SchoolRepository schoolRepository;
    private final StudyPeriodRepository studyPeriodRepository;

    public WebSecurityConfig(SchoolRepository schoolRepository, StudyPeriodRepository studyPeriodRepository) {
        this.schoolRepository = schoolRepository;
        this.studyPeriodRepository = studyPeriodRepository;
    }

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
    @Primary
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

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new GlobalFiltersResolver(schoolRepository, studyPeriodRepository));
    }
}
