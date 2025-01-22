package pet.lovers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pet.lovers.repositories.UserRepository;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private final RoleBasedSuccessHandler roleBasedSuccessHandler;
    private final UserRepository userRepository;

    public SecurityConfig(RoleBasedSuccessHandler roleBasedSuccessHandler, UserRepository userRepository) {
        this.roleBasedSuccessHandler = roleBasedSuccessHandler;
        this.userRepository = userRepository;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests -> requests
                        .requestMatchers("/", "/home", "/images/**", "/js/**", "/css/**","/pets/**","shelter-view").permitAll()
                        .requestMatchers("/forgot-password","/reset-password", "/register/**","/login").anonymous()
                        .requestMatchers("/adopter/**", "/adoption-requests/adopter/**").hasRole("ADOPTER")
                        .requestMatchers("/shelter/**" , "/adoption-requests/shelter/**" ).hasRole("SHELTER")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/vet/**").hasRole("VET")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new UserStatusAuthorizationFilter(userRepository), UsernamePasswordAuthenticationFilter.class)
                .formLogin(form -> form.loginPage("/login")
                                       .successHandler(roleBasedSuccessHandler)
                                       .permitAll())
                .logout(LogoutConfigurer::permitAll);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
