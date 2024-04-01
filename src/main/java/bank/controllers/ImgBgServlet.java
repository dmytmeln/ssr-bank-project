package bank.controllers;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet("/img/bg")
public class ImgBgServlet extends HttpServlet {

    private final File imgFile = new File("src/main/webapp/WEB-INF/img/bg.jpg");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (PrintWriter writer = resp.getWriter()) {

            try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(imgFile))) {
                int i;

                while ((i = bufferedInputStream.read()) != -1) {
                    writer.print((char) i);
                }
            }

        }
    }
}
