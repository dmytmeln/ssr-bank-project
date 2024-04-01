package bank.controllers;

import bank.configuration.ApplicationContextHolder;
import bank.exceptions.NotFound;
import bank.model.domain.TransactionHistory;
import bank.model.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/transactions", name = "TransactionsServlet")
public class TransactionsServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ApplicationContextHolder.getAutowireCapableBeanFactory().autowireBean(this);
    }

    @Autowired
    private TransactionService transactionService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            Long userId = (Long) req.getSession().getAttribute("id");

            List<TransactionHistory> transactions = transactionService.getTransactionsByUserId(userId);

            req.setAttribute("transactions", transactions);
            req.getRequestDispatcher("/WEB-INF/html/transactions.jsp").forward(req, resp);

        } catch (NotFound e) {
            req.setAttribute("errorMsg", "Account haven't been found");
            req.getRequestDispatcher("/WEB-INF/html/auth.jsp").forward(req, resp);
        }

    }

}