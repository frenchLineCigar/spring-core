package hello.core.order;

import hello.core.AppConfig;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Created by frenchline707@gmail.com on 2020-09-28
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

public class OrderServiceTest {

    MemberService memberService;
    OrderService orderService;

    // AppConfig 사용
    @BeforeEach
    public void beforeEach() {
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
        orderService = appConfig.orderService();
    }

    @Test
    void createOrder() {
        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10000);
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);

    }

    @Test
    void fieldInjectionTest() {
        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        OrderServiceImpl orderService = new OrderServiceImpl(); //스프링 없이 임의로 이렇게 생성하면 @Autowired가 무효 (DI 프레임워크가 없으면 아무것도 할 수 없다)
        orderService.setMemberRepository(new MemoryMemberRepository()); //결국 이렇게 수정자를 만들어서 또 임의 객체를 넣어주면서 테스트 해야됨...
        orderService.setDiscountPolicy(new FixDiscountPolicy()); //그래서 필드 인젝션은 사용하지 말자
        orderService.createOrder(1L, "itemA", 10000);
    }

}
