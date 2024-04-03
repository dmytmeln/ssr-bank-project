//package bank.controllers;
//
//
//import bank.configuration.ApplicationContextHolder;
//import bank.model.domain.BankAccount;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//
//import javax.servlet.ServletConfig;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@WebServlet(value = "/bank", name = "BankServlet")
//public class BankServlet extends HttpServlet {
//
//    @Autowired
//    @Qualifier("BankService")
//    private BankService bankService;
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
//            Long userId = (Long) req.getSession().getAttribute("id");
//
//            BankAccount account = bankService.findBankAccountByUserId(userId);
//            req.setAttribute("bank", account);
//            req.getRequestDispatcher("/WEB-INF/html/bank.jsp").forward(req, resp);
//
//        } catch (RuntimeException e) {
//            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
//            req.setAttribute("errorMsg", "Account haven't been found");
//            req.getRequestDispatcher("/WEB-INF/html/auth.jsp").forward(req, resp);
//        }
//
//    }
//
//}
