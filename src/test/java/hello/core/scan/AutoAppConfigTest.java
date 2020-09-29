package hello.core.scan;

import hello.core.AutoAppConfig;
import hello.core.member.MemberService;
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
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);

        MemberService memberService = ac.getBean(MemberService.class);
        //System.out.println(Arrays.toString(ac.getBeanNamesForAnnotation(Component.class))); //[autoAppConfig, rateDiscountPolicy, memberServiceImpl, memoryMemberRepository, orderServiceImpl]
        //System.out.println(Arrays.toString(ac.getBeanNamesForType(MemberService.class))); //[memberServiceImpl]
        assertThat(memberService).isInstanceOf(MemberService.class);

    }
}
