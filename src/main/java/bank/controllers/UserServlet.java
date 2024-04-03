//package bank.controllers;
//
//import bank.configuration.ApplicationContextHolder;
//import bank.exceptions.UserAlreadyExists;
//import bank.exceptions.UserNotFound;
//import bank.model.domain.User;
//import bank.model.services.Composer;
//import bank.model.services.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import javax.servlet.ServletConfig;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@WebServlet(value = "/user", name = "UserServlet")
//public class UserServlet extends HttpServlet {
//
//    @Autowired
//    private Composer composer;
//
//    @Autowired
//    private UserService userService;
//
//    private Long userId;
//    private User user;
//
//    @Override
//    public void init(ServletConfig config) throws ServletException {
//        super.init(config);
//        ApplicationContextHolder.getAutowireCapableBeanFactory().autowireBean(this);
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//        try {
//
//            this.userId = (Long) req.getSession().getAttribute("id");
//            this.user = userService.find(userId);
//
//            req.setAttribute("user", this.user);
//            req.getRequestDispatcher("/WEB-INF/html/user.jsp").forward(req, resp);
//
//        } catch (UserNotFound e) {
//            req.setAttribute("errorMsg", "Account haven't been found");
//            req.getRequestDispatcher("/WEB-INF/html/auth.jsp").forward(req, resp);
//        }
//
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//        try {
//
//            User user = composer.composeUser(req, true);
//            this.user = userService.update(user, userId);
//            resp.sendRedirect("/user");
//
//        } catch (IllegalArgumentException | NullPointerException| UserAlreadyExists e) {
//            resp.setStatus(HttpServletResponse.SC_CONFLICT);
//            req.setAttribute("user", this.user);
//            req.setAttribute("errorMsg", e.getMessage());
//            req.getRequestDispatcher("/WEB-INF/html/user.jsp").forward(req, resp);
//        } catch (UserNotFound e) {
//            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
//
//            req.setAttribute("errorMsg", e.getMessage());
//            req.getRequestDispatcher("/WEB-INF/html/auth.jsp").forward(req, resp);
//        }
//
//    }
//}