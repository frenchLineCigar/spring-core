package hello.core.discount;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Created by frenchline707@gmail.com on 2020-10-08
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

/**
 * - 비즈니스 로직 중에서 `다형성을 적극 활용할 때, 수동 빈 등록을 사용`하면 코드 파악을 용이하게 한다
 * DiscountPolicy 스프링 빈 등록을 자동 -> 수동 으로 변경
 */
@Configuration
public class DiscountPolicyConfig {

    @Bean
    @Primary //기본 할인 정책은 정률 할인
    public DiscountPolicy rateDiscountPolicy() {
        return new RateDiscountPolicy();
    }

    @Bean
    public DiscountPolicy fixDiscountPolicy() {
        return new FixDiscountPolicy();
    }
}
