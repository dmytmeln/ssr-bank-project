package bank.controllers;

import bank.configuration.ApplicationContextHolder;
import bank.exceptions.AccountAlreadyExists;
import bank.model.domain.User;
import bank.model.services.Composer;
import bank.exceptions.UserAlreadyExists;
import bank.model.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/auth/signup")
public class SignupServlet extends HttpServlet {

    @Autowired
    private Composer composer;

    @Autowired
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ApplicationContextHolder.getAutowireCapableBeanFactory().autowireBean(this);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            User user = composer.composeUser(req);
            userService.add(user);

            resp.sendRedirect("/auth");

        } catch (IllegalArgumentException | NullPointerException | UserAlreadyExists | AccountAlreadyExists e) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            req.setAttribute("errorMsg", "Account haven't been created<br>" + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/html/auth.jsp").forward(req, resp);
        }

    }
}
