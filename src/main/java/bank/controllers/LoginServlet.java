package bank.controllers;

import bank.configuration.ApplicationContextHolder;
import bank.exceptions.UserNotFound;
import bank.model.services.Composer;
import bank.model.domain.User;
import bank.model.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {

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
            user = userService.find(user);

            Long id = user.getId();
            HttpSession session = req.getSession();
            session.setAttribute("id", id);
            resp.sendRedirect("/");

        } catch (UserNotFound e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            req.setAttribute("errorMsg", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/html/auth.jsp").forward(req, resp);
        }

    }
}
