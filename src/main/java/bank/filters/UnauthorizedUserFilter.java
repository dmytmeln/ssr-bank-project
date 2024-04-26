package bank.filters;

import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UnauthorizedUserFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        Long userId = (Long) httpServletRequest.getSession().getAttribute("userId");
        if (userId == null) {
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/auth/login");
            return;
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}