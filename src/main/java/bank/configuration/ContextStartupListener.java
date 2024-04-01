package bank.configuration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextStartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ApplicationContextHolder.init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

}
