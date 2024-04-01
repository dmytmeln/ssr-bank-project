package bank.filters;

// class to filter unauthorized users that trying to access profile or transaction history

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(servletNames = {"UserServlet", "TransactionsServlet", "BankServlet"})
public class UnauthorizedUserFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Long id  = (Long) request.getSession().getAttribute("id");
        if (id == null) {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.sendRedirect("/auth");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);



    }

    @Override
    public void destroy() {

    }

}
