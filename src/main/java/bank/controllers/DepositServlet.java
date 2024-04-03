//package bank.controllers;
//
//import bank.configuration.ApplicationContextHolder;
//import bank.exceptions.NotFound;
//import bank.model.services.BankService;
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
//@WebServlet("/bank/deposit")
//public class DepositServlet extends HttpServlet {
//
//    @Autowired
//    private BankService bankService;
//
//    @Override
//    public void init(ServletConfig config) throws ServletException {
//        super.init(config);
//        ApplicationContextHolder.getAutowireCapableBeanFactory().autowireBean(this);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//        try {
//
//            Long id = Long.valueOf(req.getParameter("id"));
//            Double moneyAmount = Double.valueOf(req.getParameter("money_amount"));
//            String transactionMsg = req.getParameter("transaction_msg");
//
//            bankService.makeDeposit(id, moneyAmount, transactionMsg);
//
//            resp.sendRedirect("/bank");
//
//        } catch (IllegalArgumentException | NullPointerException | NotFound e) {
//            req.setAttribute("errorMsg", e.getMessage());
//            req.getRequestDispatcher("/WEB-INF/html/error.jsp").forward(req, resp);
//        }
//
//    }
//}