package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

/**
 * Created by frenchline707@gmail.com on 2020-09-28
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private DiscountPolicy discountPolicy; //인터페이스에만 의존하도록 코드 변경

    /**
     * 주문 생성
     */
    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId); //회원 정보 조회
        int discountPrice = discountPolicy.discount(member, itemPrice); //할인 정책 조회 -> 실행 시 NullPointerException 발생

        // 구현체가 없는데 어떻게 코드를 실행할 수 있을까?
        // : 누군가가 클라이언트인 OrderServiceImpl에 DiscountPolicy의 구현 객체를 대신 생성하고 주입해주어야 한다

        return new Order(memberId, itemName, itemPrice, discountPrice); //생성된 주문을 반환
    }


}
