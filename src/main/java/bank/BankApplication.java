package bank;

import bank.configuration.ApplicationContextHolder;
import bank.model.repository.BankAccountDao;
import bank.model.repository.TransactionHistoryDao;
import bank.model.repository.UserDao;
import lombok.AllArgsConstructor;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
@AllArgsConstructor
public class BankApplication implements CommandLineRunner {

    @Autowired
    UserDao userDao;

    @Autowired
    BankAccountDao bankAccountDao;

    @Autowired
    TransactionHistoryDao transactionDao;

    public static void main(String[] args) throws LifecycleException {

        SpringApplication.run(BankApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {

//        System.out.println(userDao.readById(1L));

    }

//    public static void startServer() throws LifecycleException {
//        Tomcat tomcat = new Tomcat();
//        tomcat.setPort(8080);
//
//        Context ctx = tomcat.addWebapp("", new File("src/main/webapp/").getAbsolutePath());
//
//        WebResourceRoot resources = new StandardRoot(ctx);
//        resources.addPreResources(
//                new DirResourceSet(resources, "/WEB-INF/classes",
//                        new File("target/classes").getAbsolutePath(), "/"));
//        ctx.setResources(resources);
//
//        tomcat.enableNaming();
//        tomcat.getConnector();
//
//        tomcat.start();
//        tomcat.getServer().await();
//    }

}