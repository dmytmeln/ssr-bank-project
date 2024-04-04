package bank;

import bank.model.repository.AccountRepository;
import bank.model.repository.TransactionRepository;
import bank.model.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.catalina.LifecycleException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
public class BankApplication implements CommandLineRunner {

    UserRepository userRepo;

    AccountRepository accountRepository;

    TransactionRepository transactionRepo;

    public static void main(String[] args) throws LifecycleException {

        SpringApplication.run(BankApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {

//        System.out.println(userRepo.findById(1L).get());

//        System.out.println(userRepo.findUserByEmailOrPhoneNumberAndPassword("dimamel28@gmail.com", "380984035791", "Mdm281004"));
//
//        System.out.println(accountRepository.findById(1L).get());
//
//        System.out.println(transactionRepo.findTransactionHistoriesByBankAccountId(1L));
//
//        System.out.println(transactionRepo.findById(1L).get());

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