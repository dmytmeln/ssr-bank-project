package bank;

import bank.model.repository.TransactionHistoryDao;
import bank.model.repository.UserDao;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;

public class Main {

    public static void main(String[] args) throws LifecycleException {
//        ApplicationContextHolder.init();
//        System.out.println(ApplicationContextHolder.getAutowireCapableBeanFactory().getBean(BankService.class).findBankAccountByUserId(1L));

        System.out.println(new UserDao().readById(1L));

//        startServer();

    }

    public static void startServer() throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);

        Context ctx = tomcat.addWebapp("", new File("src/main/webapp/").getAbsolutePath());

        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(
                new DirResourceSet(resources, "/WEB-INF/classes",
                        new File("target/classes").getAbsolutePath(), "/"));
        ctx.setResources(resources);

        tomcat.enableNaming();
        tomcat.getConnector();

        tomcat.start();
        tomcat.getServer().await();
    }

}