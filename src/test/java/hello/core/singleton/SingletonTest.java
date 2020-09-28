package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
        Assertions.assertThat(memberService1).isNotSameAs(memberService2);
    }

}
