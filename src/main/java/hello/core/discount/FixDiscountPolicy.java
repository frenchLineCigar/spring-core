package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.stereotype.Component;

/**
 * Created by frenchline707@gmail.com on 2020-09-28
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

/**
 * 할인 정책 : 고정 할인 정책
 */
@Component("fix")
public class FixDiscountPolicy implements DiscountPolicy {

    private int discountFixAmount = 1000; //1000원 할인
    
    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return discountFixAmount;
        } else {
            return 0;
        }
    }
}
