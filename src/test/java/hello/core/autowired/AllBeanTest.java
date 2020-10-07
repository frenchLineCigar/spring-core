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

        int fixDiscountPrice = discountService.discountFindByPrefix(member, 20000, "fix");
//        int fixDiscountPrice = discountService.discount(member, 20000, "fix");
//        int fixDiscountPrice = discountService.discount(member, 20000, "fixDiscountPolicy");
        assertThat(fixDiscountPrice).isEqualTo(1000); //고정 할인 정책에서는 VIP의 경우 1000원이 할인돼야 함

        int rateDiscountPrice = discountService.discountFindByPrefixWithJava8(member, 50000, "rate");
//        int rateDiscountPrice = discountService.discount(member, 50000, "rate");
//        int rateDiscountPrice = discountService.discount(member, 50000, "rateDiscountPolicy");
        assertThat(rateDiscountPrice).isEqualTo(5000); //정률 할인 정책에서는 VIP의 경우 판매금액의 10%가 할인돼야 함

    }

    static class DiscountService { // allBeanTest.DiscountService
        private final Map<String, DiscountPolicy> policyMap;
        private final List<DiscountPolicy> policies;

//        @Autowired //자동 주입 시 커스텀 애노테이션 옵션 활용
//        public DiscountService(@MainDiscountPolicy Map<String, DiscountPolicy> policyMap, @MainDiscountPolicy List<DiscountPolicy> policies) {
//            this.policyMap = policyMap;
//            this.policies = policies;
//            System.out.println("policyMap = " + policyMap); //only RateDiscountPolicy <- caused by @MainDiscountPolicy
//            System.out.println("policies = " + policies);
//        }

        @Autowired
        public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policies) {
            this.policyMap = policyMap;
            this.policies = policies;
            System.out.println("policyMap = " + policyMap);
            System.out.println("policies = " + policies);
        }

        public int discount(Member member, int price, String discountCode) { //문자열 모두 매칭 (강의 코드) <- rateDiscountPolicy, fixDiscountPolicy
            DiscountPolicy discountPolicy = policyMap.get(discountCode);
            return discountPolicy.discount(member, price);
        }

        /* 걍 해본 것 - 시작 문자열(prefix) : rate, fix 로 매칭 조회 */
        public int discountFindByPrefix(Member member, int price, String discountCode) {
            final int NOT_FOUND_POLICY = 0; //이렇게 값을 지정하면 안될 것 같은데...어떻게 해야하지..
            for (String policy : policyMap.keySet()) {
                if (policy.startsWith(discountCode)) {
                    DiscountPolicy discountPolicy = policyMap.get(policy);
                    return discountPolicy.discount(member, price);
                }
            }
            return NOT_FOUND_POLICY;
        }

        /* 걍 해본 것 - 자바 8 사용, 시작 문자열(prefix) : rate, fix 로 매칭 조회 */
        public int discountFindByPrefixWithJava8(Member member, int price, String discountCode) {
//            final int NOT_FOUND_POLICY = 0;
            return policyMap.keySet()
                    .stream()
                    .filter(policy -> policy.startsWith(discountCode))
                    .map(policyMap::get)
                    .findFirst()
                    .map(discountPolicy -> discountPolicy.discount(member, price))
                    .orElseThrow();
//                    .orElseGet(() -> NOT_FOUND_POLICY);
//                    .orElse(NOT_FOUND_POLICY);

        }

    }

}