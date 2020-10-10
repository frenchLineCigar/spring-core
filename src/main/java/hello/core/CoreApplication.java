package hello.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class CoreApplication { //-> AnnotationConfigServletWebServerApplicationContext 기반 구동 (spring-boot-start-web 추가 시)

	/**
	 * 스프링 부트에 웹 라이브러리가 추가되면, 웹과 관련된 추가 설정과 환경들이 필요하므로
	 * `AnnotationConfigServletWebServerApplicationContext` 를 기반으로 애플리케이션 구동
	 */
	public static void main(String[] args) {
		System.out.println("==== CoreApplication.main ====");
		ApplicationContext ac = SpringApplication.run(CoreApplication.class, args);
		System.out.println("[main] ac = " + ac);
		System.out.println("[main] ac.getClass() = " + ac.getClass()); //AnnotationConfigServletWebServerApplicationContext
	}

	/**
	 * 1. `spring-boot-starter-web` 라이브러리를 추가하면 스프링 부트는 내장 톰캣 서버(Embedded Tomcat Server)를 활용해서
	 * 	웹 서버와 스프링을 함께 실행시킨다
	 * 2. 스프링 부트는 웹 라이브러리가 없으면, 우리가 지금까지 학습한 `AnnotationConfigApplicationContext` 를 기반으로 애플리케이션을 구동한다. 웹 라이브러리가 추가되면, 웹과 관련된 추가 설정과 환경들이 필요하므로
	 * 	`AnnotationConfigServletWebServerApplicationContext` 를 기반으로 애플리케이션을 구동한다.
	 * * 참고 : 스프링 부트 테스트( @SpringBootTest )는 `GenericWebApplicationContext` 를 기반으로 구동
	 */
}
