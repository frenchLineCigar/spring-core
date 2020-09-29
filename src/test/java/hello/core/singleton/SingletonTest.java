package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by frenchline707@gmail.com on 2020-09-29
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

/**
 * 앞에서 만들었던 순수한 DI 컨테이너가 가진 문제점을 살펴보고
 * 나중에 스프링과 비교해본다
 * 
 * 문제점:
 * 스프링이 없는 순수한 DI 컨테이너인 AppConfig는 요청을 할 때 마다 객체를 새로 생성한다
 * 고객 트래픽이 초당 10만(TPS)이 나오면 초당 10만개의 객체가 생성되고 소멸됨 -> 메모리 낭비가 심하다
 * 해결방안은 해당 객체가 딱 1개만 생성되고, 공유하도록 설계 -> 싱글톤 패턴
 */
public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer() {
        AppConfig appConfig = new AppConfig();
        //1. 조회: 호출할 때 마다 객체를 생성
        MemberService memberService1 = appConfig.memberService();

        //2. 조회: 호출할 때 마다 객체를 생성
        MemberService memberService2 = appConfig.memberService();

        //참조값이 다른 것을 확인
        System.out.println("memberService1 = " + memberService1); //MemberServiceImpl@2a4fb17b
        System.out.println("memberService2 = " + memberService2); //MemberServiceImpl@5c6648b0

        //memberService1 != memberService2
        assertThat(memberService1).isNotSameAs(memberService2);
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest() {
        //private으로 생성자를 막아두었다. 컴파일 오류가 발생한다.
        //new SingletonService();

        //1. 조회: 호출할 때 마다 같은 객체를 반환
        SingletonService singletonService1 = SingletonService.getInstance();
        //2. 조회: 호출할 때 마다 같은 객체를 반환
        SingletonService singletonService2 = SingletonService.getInstance();

        //참조값이 같은 것을 확인 : 같은 객체의 인스턴스가 반환
        System.out.println("singletonService1 = " + singletonService1); //SingletonService@2a65fe7c
        System.out.println("singletonService2 = " + singletonService2); //SingletonService@2a65fe7c

        // singletonService1 == singletonService2
        assertThat(singletonService1).isSameAs(singletonService2);
        //same : == (객체 인스턴스, 참조값 비교)
        //equal : equals method

        singletonService1.logic();
    }

}
