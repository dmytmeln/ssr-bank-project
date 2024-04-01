package bank.configuration;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextHolder {

    private static AnnotationConfigApplicationContext applicationContext;

    public static void init() {
        applicationContext = new AnnotationConfigApplicationContext(Config.class);
    }

    public static AutowireCapableBeanFactory getAutowireCapableBeanFactory() {
        return applicationContext.getAutowireCapableBeanFactory();
    }
}
