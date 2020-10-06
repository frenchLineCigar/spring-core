package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import static org.springframework.context.annotation.ComponentScan.Filter;

/**
 * Created by frenchline707@gmail.com on 2020-09-29
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

/**
 * 컴포넌트 스캔의 탐색 위치와 기본 스캔 대상
 * 1) 탐색할 패키지의 시작 위치 지정 방법
 * 2) 컴포넌트 스캔 기본 대상
 */
@Configuration
@ComponentScan(
        excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = Configuration.class) // @Configuration 붙은 클래스는 제외 -> 기존 설정 코드들과 충돌을 피하기 위해
)
public class AutoAppConfig {

    /**
     * 필드 주입을 활용할 수 있는 경우
     * 2. 스프링 설정을 목적으로 하는 @Configuration 같은 곳에서만 특별한 용도로 사용 (ex. 아래와 같이 수동 빈 등록 시 의존관계 넣어줄 때)
     */
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    DiscountPolicy discountPolicy;

    @Bean
    OrderService orderService() {
        return new OrderServiceImpl(memberRepository, discountPolicy);
    }
//    위 코드는 아래와 같이 써도 됨
//    @Bean
//    OrderService orderService(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//        return new OrderServiceImpl(memberRepository, discountPolicy);
//    }


    // 수동 빈 등록 vs 자동 빈 등록 : 수동 빈 등록이 자동 빈 등록보다 우선권을 가진다 (수동 빈이 자동 빈을 오버라이딩 해버린다)
    @Bean(name = "memoryMemberRepository")
    MemberRepository memberRepository() {
        System.out.println("call AutoAppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    /**
     * [ 수동 빈 등록 시 남는 로그 ]
     *
     * Overriding bean definition for bean 'memoryMemberRepository' with a different definition:
     * replacing
     * [Generic bean: class [hello.core.member.MemoryMemberRepository]; scope=singleton; abstract=false; lazyInit=null; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null; defined in file [C:\...\core\out\production\classes\hello\core\member\MemoryMemberRepository.class]]
     * with
     * [Root bean: class [null]; scope=; abstract=false; lazyInit=null; autowireMode=3; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=autoAppConfig; factoryMethodName=memberRepository; initMethodName=null; destroyMethodName=(inferred); defined in hello.core.AutoAppConfig]
     *
     * 물론 개발자가 의도적으로 이런 결과를 기대했다면, 자동 보다는 수동이 우선권을 가지는 것이 좋다. 하지만 현실은 개발자가 의도적으로
     * 설정해서 이런 결과가 만들어지기 보다는 여러 설정들이 꼬여서 이런 결과가 만들어지는 경우가 대부분이다!
     * `그러면 정말 잡기 어려운 버그가 만들어진다. 항상 잡기 어려운 버그는 애매한 버그다.`
     * 그래서 최근 스프링 부트에서는 수동 빈 등록과 자동 빈 등록이 충돌나면 오류가 발생하도록 기본 값을 바꾸었다.
     * 스프링 부트인 `CoreApplication`을 실행해보면 오류를 볼 수 있다.
     */
}




