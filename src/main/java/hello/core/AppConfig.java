package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.MemberRepository;
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
 * AppConfig : 역할에 따른 구현이 잘 보이도록 리팩터링
 * - 구성 정보를 까보면 역할에 따른 구현이 한 그림에 보여지도록
 * - 메서드 명과 리턴 타입만 봐도 역할이 드러나도록, 각 서비스,레파지토리의 구현에 어떤 클래스를 쓸 것인지
 * - 설계에 대한 그림이 구성 정보에 그대로 드러나게 된다 (역할이 나오고, 그 역할에 대한 구현이 어떻게 되는지 한 눈에 확인됨)
 */
public class AppConfig {

    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    private MemberRepository memberRepository() {
        return new MemoryMemberRepository(); //db가 바뀌어도 이 코드부분만 변경하면 됨
    }

    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    public DiscountPolicy discountPolicy() { //할인정책은
        return new FixDiscountPolicy(); //고정할인금액 쓰는구나
    }

}
