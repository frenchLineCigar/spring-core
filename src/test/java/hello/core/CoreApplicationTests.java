package hello.core;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
// -> 여기는 컴포넌트 스캔 설정 안먹힘
//@ComponentScan(
//		excludeFilters = {
//				@Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
//		}
//)
// (여기에 붙이지 말고 @SpringBootApplication 애노테이션이 위치한 CoreApplication 에서 설정하면, 여기서도 똑같이 먹힘)
// (부트 테스트 시 @ComponentScan 처럼 설정 관련된 내용은 메인 앱의 애노테이션 설정을 읽어서 쓰는 듯)
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
