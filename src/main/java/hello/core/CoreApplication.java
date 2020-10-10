package hello.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class CoreApplication {

	public static void main(String[] args) {
		System.out.println("==== CoreApplication.main ====");
		ApplicationContext ac = SpringApplication.run(CoreApplication.class, args);
		System.out.println("[main] ac = " + ac);
		System.out.println("[main] ac.getClass() = " + ac.getClass()); //AnnotationConfigApplicationContext
	}

}
