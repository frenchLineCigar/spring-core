package hello.core.order;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by frenchline707@gmail.com on 2020-10-06
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

/**
 * 순수 자바 코드로만
 * OrderServiceImpl 로직 자체를 테스트
 * - 필요한 구현체들은 임의의 더미 데이터를 사용하면서 순수 자바 코드로만 테스트
 *
 * 생성자 주입을 사용하면 주입 데이터를 누락했을 때 `컴파일 오류`가 발생한다.
 * 그리고 IDE에서 바로 어떤 값을 필수로 주입해야 하는지 알 수 있다.
 */
class OrderServiceImplTest {

    @Test
    void createOrder() {
        MemoryMemberRepository memberRepository = new MemoryMemberRepository();
        memberRepository.save(new Member(1L, "lucy", Grade.VIP));

        OrderServiceImpl orderService = new OrderServiceImpl(memberRepository, new FixDiscountPolicy()); //setter를 두면, 테스트 코드 짤때 바로 의존관계 확인이 안됨. constructor를 두면, 코딩 시점에 바로 컴파일 오류확인됨
        Order order = orderService.createOrder(1L, "itemA", 10000);//NullPointerException 뜨고 이때서야 setter가 있는지 들어가서 확인하게 됨
        assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }

}