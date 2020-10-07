package hello.core.scan;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.DiscountPolicyConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.order.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by frenchline707@gmail.com on 2020-09-29
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

public class AutoAppConfigTest {

    @Test
    void basicScan() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountPolicyConfig.class);

        MemberService memberService = ac.getBean(MemberService.class);
        //System.out.println(Arrays.toString(ac.getBeanNamesForAnnotation(Component.class))); //[autoAppConfig, rateDiscountPolicy, memberServiceImpl, memoryMemberRepository, orderServiceImpl]
        //System.out.println(Arrays.toString(ac.getBeanNamesForType(MemberService.class))); //[memberServiceImpl]
        assertThat(memberService).isInstanceOf(MemberService.class);

        // OrderServiceImpl 에서 필드 주입(Field injection)이 되는지 확인
        OrderServiceImpl bean = ac.getBean(OrderServiceImpl.class);
        MemberRepository memberRepository = bean.getMemberRepository();
        DiscountPolicy discountPolicy = bean.getDiscountPolicy();
        System.out.println("memberRepository = " + memberRepository);
        System.out.println("discountPolicy = " + discountPolicy);

    }
}
