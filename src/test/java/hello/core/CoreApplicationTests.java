package hello.core;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class CoreApplicationTests { //-> GenericWebApplicationContext 기반 구동 (spring-boot-start-web 추가 시)

	@Autowired
	ApplicationContext ac;

	@Test
	void contextLoads() {
		System.out.println("CoreApplicationTests.contextLoads");
		System.out.println("ac = " + ac);
		System.out.println("object : " + ac.getClass()); //GenericWebApplicationContext
	}

}
