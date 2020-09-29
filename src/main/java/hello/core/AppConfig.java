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
import org.springframework.context.annotation.Configuration;

/**
 * Created by frenchline707@gmail.com on 2020-09-28
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

@Configuration
public class AppConfig {

    //@Bean memberService -> new MemoryMemberRepository()
    //@Bean orderService -> new MemoryMemberRepository()

    //call AppConfig.memberService
    //call AppConfig.memberRepository
    //call AppConfig.memberRepository
    //call AppConfig.orderService
    //call AppConfig.memberRepository
    //예상: 메서드 호출 순서는 보장될 순 없으나, 결과적으로 memberRepository가 3번 호출되어야 한다?

    //실제: memberRepository가 1번 호출된다 (스프링이 어떠한 방법을 써서라도 싱글톤을 보장해주는 걸 로그를 통해 확인)
    //call AppConfig.memberService
    //call AppConfig.memberRepository
    //call AppConfig.orderService

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
