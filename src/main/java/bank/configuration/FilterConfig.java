package bank.configuration;

import bank.filters.UnauthorizedUserFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<UnauthorizedUserFilter> authorizationFilter() {
        final var filter = new FilterRegistrationBean<>(new UnauthorizedUserFilter());
        filter.addUrlPatterns(
                "/user",
                "/transactions",
                "/bank"
        );
        return filter;
    }

}