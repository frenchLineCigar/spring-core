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

        /**
         * AppConfig에 `@Configuration`을 적용하지 않고, `@Bean`만 적용하면 어떻게 될까?
         *
         * 출력 : bean = class hello.core.AppConfig
         * 이 출력 결과를 통해서 AppConfig가 CGLIB 기술 없이 순수한 자바 코드 AppConfig로 스프링 빈에 등록된 것을 알 수 있다.
         *
         * 출력:
         * call AppConfig.memberService
         * call AppConfig.memberRepository
         * call AppConfig.memberRepository
         * call AppConfig.orderService
         * call AppConfig.memberRepository
         * 이 출력 결과를 통해서 memberRepository가 총 3번 호출된 것을 알 수 있다. 1번은 @Bean에 의해 스프링 컨테이너에 등록하기 위해서이고,
         * 2번은 각각 `memberRepository()`를 호출하면서(이 경우 직접 new MemoryMemberRepository()로 만든 거랑 다를 게 없으므로 스프링 컨테이너가 관리 X)
         * 발생한 코드다
         * ( @Configuration없이 @Bean만 사용해도 스프링 빈으로 등록되지만, 싱글톤을 보장하지 않는다 )
         *
         *
         * 인스턴스가 같은지 테스트 ->  configurationTest() 메서드 실행
         * 출력:
         * memberService -> memberRepository = hello.core.member.MemoryMemberRepository@60129b9a
         * orderService -> memberRepository = hello.core.member.MemoryMemberRepository@78d6692f
         * memberRepository = hello.core.member.MemoryMemberRepository@7a55af6b
         * ( 각각 다 다른 MemoryMemberRepository 인스턴스를 가지고 있다 )
         *
         */
    }


}
