package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by frenchline707@gmail.com on 2020-09-29
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

public class ConfigurationSingletonTest {

    @Test
    void configurationTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        //모두 같은 인스턴스를 참고하고 있다.
        System.out.println("memberService -> memberRepository = " + memberRepository1);
        System.out.println("orderService -> memberRepository = " + memberRepository2);
        System.out.println("memberRepository = " + memberRepository);

        //모두 같은 인스턴스를 참고하고 있다.
        assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);

        /**
         * 확인해보면 memberRepository 인스턴스는 모두 같은 인스턴스가 공유되어 사용된다(한 번만 생성됨)
         */
    }

    @Test
    void configurationDeep() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        //AppConfig도 스프링 빈으로 등록된다.
        AppConfig bean = ac.getBean(AppConfig.class);

        System.out.println("bean = " + bean.getClass()); //클래스 타입 보기
        //출력: bean = class hello.core.AppConfig$$EnhancerBySpringCGLIB$$6cb67377

        /**
         * `AnnotationConfigApplicationContext`에 파라미터로 넘긴 값은 스프링 빈으로 등록된다. 그래서 `AppConfig`도 스프링 빈이 된다
         * `AppConfig` 스프링 빈을 조회해서 클래스 정보를 출력해보자
         * `class hello.core.AppConfig$$EnhancerBySpringCGLIB$$6cb67377`
         *
         * 순수한 클래스라면 다음과 같이 출력되어야 한다.
         * `class hello.core.AppConfig`
         *
         * 그런데 예상과는 다르게 클래스 명에 xxxCGLIB가 붙으면서 상당히 복잡해진 것을 볼 수 있다. 이것은 내가 만든 클래스가 아니라
         * 스프링이 CGLIB라는 바이트코드 조작 라이브러리를 사용해서 AppConfig클래스를 상속받은 임의의 다른 클래스를 만들고,
         * 그 다른 클래스를 스프링 빈으로 등록한 것이다!
         *
         * 그 임의의 다른 클래스가 바로 싱글톤이 보장되도록 해준다 (스프링 자체적으로 CGLIB의 내부 기술을 사용)
         * `@Bean`이 붙은 메서드마다 이미 스프링 빈이 존재하면 존재하는 빈을 반환하고, 스프링 빈이 없으면 생성해서 스프링 빈으로 등록하고
         * 반환하는 코드가 동적으로 만들어진다
         * 덕분에 싱글톤이 보장되는 것이다
         *
         * 참고: AppConfig@CGLIB는 AppConfig의 자식 타입이므로, AppConfig 타입으로 빈을 조회할 수 있다
         * (부모 타입으로 빈을 조회하면, 자식 타입은 모두 다 끌려나온다)
         */
    }


}
