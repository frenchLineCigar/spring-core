package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by frenchline707@gmail.com on 2020-10-07
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

public class AllBeanTest {

    @Test
    void findAllBean() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class); //스프링 빈 등록

        DiscountService discountService = ac.getBean(DiscountService.class);
        Member member = new Member(1L, "userA", Grade.VIP); //VIP
        assertThat(discountService).isInstanceOf(DiscountService.class);

        int fixDiscountPrice = discountService.discount(member, 20000, "fixDiscountPolicy");
        assertThat(fixDiscountPrice).isEqualTo(1000); //고정 할인 정책에서는 VIP의 경우 1000원이 할인돼야 함

        int rateDiscountPrice = discountService.discount(member, 50000, "rateDiscountPolicy");
        assertThat(rateDiscountPrice).isEqualTo(5000); //정률 할인 정책에서는 VIP의 경우 판매금액의 10%가 할인돼야 함

    }

    static class DiscountService { // allBeanTest.DiscountService
        private final Map<String, DiscountPolicy> policyMap;
        private final List<DiscountPolicy> policies;

        @Autowired
        public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policies) {
            this.policyMap = policyMap;
            this.policies = policies;
            System.out.println("policyMap = " + policyMap);
            System.out.println("policies = " + policies);
        }

        public int discount(Member member, int price, String discountCode) {
            DiscountPolicy discountPolicy = policyMap.get(discountCode); //다형성 활용
            return discountPolicy.discount(member, price);
        }

    }
    /**
     * [ 로직 분석 ]
     * ● DiscountService는 Map으로 모든 `DiscountPolicy`를 주입받는다. 이때 `fixDiscountPolicy`, `rateDiscountPolicy`가 주입된다
     * ● `discount()` 메서드는 discountCode로 문자열 "fixDiscountPolicy"가 넘어오면 map에서 `fixDiscountPolicy` 스프링 빈을 찾아서
     *  실행한다. 물론 "rateDiscountPolicy"가 넘어오면 `rateDiscountPolicy` 스프링 빈을 찾아서 실행한다
     *
     * [ 주입 분석 ]
     * ● `Map<String, DiscountPolicy>` : map의 키에 스프링 빈의 이름을 넣어주고, 그 값으로 `DiscountPolicy` 타입으로 조회한
     *  모든 스프링 빈을 담아준다
     * ● `List<DiscountPolicy>` : `DiscountPolicy` 타입으로 조회한 모든 스프링 빈을 담아준다
     * ● 만약 해당하는 타입의 스프링 빈이 없으면, 빈 컬렉션이나 Map을 주입한다.
     */

}