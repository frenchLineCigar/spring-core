package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;

/**
 * Created by frenchline707@gmail.com on 2020-09-28
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */


/**
 * `@Configuration`을 적용하지 않고, `@Bean`만 적용하면 어떻게 될까?
 *
 * `@Configuration`을 붙이면 바이트코드를 조작하는 CGLIB 기술을 사용해서 Singleton을 보장하지만,
 * 만약 `@Bean`만 적용하면 어떻게 될까?
 */

//@Configuration 삭제
public class AppConfig {

    //@Bean memberService -> new MemoryMemberRepository()
    //@Bean orderService -> new MemoryMemberRepository()

    @Bean
    public MemberService memberService() { //bean name (default) -> method name 로 등록됨
        //1번
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        //1번
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository(); //db가 바뀌어도 이 코드부분만 변경하면 됨
    }

    @Bean
    public OrderService orderService() {
        //1번
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() { // OCP 만족 :확장에는 열려있고, 변경에는 닫혀있다
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }

}
