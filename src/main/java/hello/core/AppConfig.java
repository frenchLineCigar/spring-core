package hello.core;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

/**
 * Created by frenchline707@gmail.com on 2020-09-28
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

/**
 * AppConfig : 객체의 생성과 연결하는 역할을 담당, 앱이 어떻게 동작해야 할지 전체 구성을 책임진다 (관심사 분리)
 * 앱의 전체 동작 방식을 구성(config)하기 위해, `구현 객체를 생성하고, 연결하는 책임을 가지는` 별도의 설정 클래스
 * - 1) 앱의 실제 동작에 필요한 `구현 객체를 생성(구체 클래스 선택)`
 * - 2) 생성한 객체 인스턴스의 참조(레퍼런스)를 `생성자를 통해서 주입(연결)`
 */
public class AppConfig {

    public MemberService memberService() {
        return new MemberServiceImpl(new MemoryMemberRepository()); // 1)구현 객체 생성, 2)생성자 주입(연결)
    }

    public OrderService orderService() {
        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy()); // 1)구현 객체 생성, 2)생성자 주입(연결)
    }

}
