package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;

/**
 * Created by frenchline707@gmail.com on 2020-09-28
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

/**
 * OrderServiceImpl - 생성자 주입 => 기능을 실행하는 역할만 책임지도록 (관심사 분리)
 * 1) 설계 변경으로 `OrderServiceImpl`은 `FixDiscountPolicy`를 의존하지 않는다
 * 2) 단지 `DiscountPolicy` 인터페이스만 의존한다
 * 3) `OrderServiceImpl` 입장에서 생성자를 통해 어떤 구현 객체가 들어올지(주입될지)는 알 수 없다
 * 4) `OrderServiceImpl` 의 생성자를 통해서 어떤 구현 객체를 주입할지는 오직 외부(`AppConfig`)에서 결정된다
 * 5) `OrderServiceImpl` 은 이제부터 `실행에만 집중`하면 된다
 * - DIP 완성: `OrderServiceImpl`은 `MemberRepository, DiscountPolicy` 추상에만 의존하면 된다. 이제 구체 클래스를 몰라도 된다
 */
public class OrderServiceImpl implements OrderService {

    //DIP 만족 : OrderServiceImpl이 인터페이스(추상화)에만 의존하도록 코드 변경, 구현체가 누가 들어올지 전혀 모름
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    /**
     * 주문 생성
     */
    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId); //회원 정보 조회
        int discountPrice = discountPolicy.discount(member, itemPrice); //할인 정책 조회

        return new Order(memberId, itemName, itemPrice, discountPrice); //생성된 주문을 반환
    }


}