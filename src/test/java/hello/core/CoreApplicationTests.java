package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.order.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CoreApplicationTests {

	/**
	 * 필드 주입을 활용할 수 있는 경우
	 * 1. 애플리케이션의 실제 코드와 관계 없는 테스트 코드 (테스트를 스프링 컨테이너를 띄워서 하는 경우)
	 */
	@Autowired
	OrderService orderService;
	@Autowired
	MemberService memberService;

	@Test
	void contextLoads() {
		System.out.println("orderService = " + orderService);
		System.out.println("memberService = " + memberService);

		Long memberId = 1L;
		Member member = new Member(memberId, "memberA", Grade.VIP);
		memberService.join(member);

		orderService.createOrder(1L, "itemA", 10000);

	}

}
