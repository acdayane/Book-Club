package ada.mod3.bookclub.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    SecurityFilter securityFilter;
    
    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);

        return http.csrf(csrf -> csrf.disable())
                   .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //o gerenciamento da sessao nao é nessa app
                   .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/user/sign-up")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/sign-in")).permitAll()
                        .anyRequest().authenticated())
                   .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                   .build();

    }

    @Bean //criar o authentication para poder usar
    public AuthenticationManager authManager (AuthenticationConfiguration authConfiguration) throws Exception { 
        return authConfiguration.getAuthenticationManager();
    }

    @Bean //que criptografia é usada para poder descriptografar
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
