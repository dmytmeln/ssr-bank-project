package bank.filters;

import bank.model.domain.User;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UnauthorizedUserFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        User user = (User) httpServletRequest.getSession().getAttribute("user");
        if (user == null) {
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/auth");
            return;
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}