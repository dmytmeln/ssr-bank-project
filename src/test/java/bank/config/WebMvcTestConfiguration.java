package bank.config;

import bank.model.Role;
import bank.model.User;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@TestConfiguration
public class WebMvcTestConfiguration {

    @Bean(name = "UserDetailsServiceTest")
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.builder()
                .id(1L)
                .email("user")
                .role(Role.ROLE_USER)
                .password("123")
                .phoneNumber("1234")
                .firstname("User")
                .lastname("User")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

}
