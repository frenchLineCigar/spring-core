package hello.core;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest //GenericWebApplicationContext 기반 구동 (spring-boot-start-web 추가 시)
class CoreApplicationTests {

	@Autowired ApplicationContext ac;
	@Autowired ApplicationContext ac2;
	@Autowired ApplicationContext ac3;

	@Test
	void contextLoads() {
		System.out.println("CoreApplicationTests.contextLoads");
		System.out.println("ac = " + ac);
		System.out.println("object : " + ac.getClass()); //GenericWebApplicationContext
	}

	@Test
	void applicationContextSingletonTest() { //모두 동일한 컨텍스트를 사용하는지 싱글톤 확인
		System.out.println("ac = " + ac);
		System.out.println("ac2 = " + ac2);
		System.out.println("ac3 = " + ac3);
		Assertions.assertThat(ac).isSameAs(ac2).isSameAs(ac3);
	}

}
