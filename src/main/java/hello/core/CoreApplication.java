package hello.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@ComponentScan(
//		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = AppConfig.class)
//)
public class CoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}

	/**
	 * 여러 설정들이 꼬여서 잡기 어려운 애매한 버그가 발생하는 것을 방지하기 위해
	 * 최근 스프링 부트는 수동 빈 등록과 자동 빈 등록이 충돌나면 오류가 발생하도록 기본 값을 바꾸었다.
	 *
	 * [ 수동 빈 등록, 자동 빈 등록 오류 시 스프링 부트 에러 ]
	 *
	 * The bean 'memoryMemberRepository', defined in class path resource [hello/core/AutoAppConfig.class], could not be registered.
	 * A bean with that name has already been defined in file [C:\...\core\out\production\classes\hello\core\member\MemoryMemberRepository.class]
	 * and overriding is disabled. ( 오버라이딩이 비활성화됨 )
	 *
	 * Action:
	 *
	 * Consider renaming one of the beans or enabling overriding 
	 * by setting 
	 * spring.main.allow-bean-definition-overriding=true
	 * ( application.properties에 셋팅 값을 위와 같이 추가해주면 오버라이딩 활성화됨 )
	 *
	 *
	 */
}
