package hello.core.autowired;

import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

/**
 * Created by frenchline707@gmail.com on 2020-10-06
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

public class AutowiredTest {

    @Test
    void AutowiredOption() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class); //bean 등록
    }

    static class TestBean {

        @Autowired(required = false) //의존관계 주입 대상이 없으면 메서드 자체가 호출이 안됨
        public void setNoBean1(Member noBean1) { //Member는 스프링이 관리하는 빈이 아님 -> UnsatisfiedDependencyException (if required=true)
            System.out.println("setNoBean1 = " + noBean1); //호출 안함
        }

        @Autowired
        public void setNoBean2(@Nullable Member noBean2) { //Member는 스프링이 관리하는 빈이 아님 -> 없으면 null (@Nullable)
            System.out.println("setNoBean2 = " + noBean2); //null
        }

        @Autowired
        public void setNoBean3(Optional<Member> noBean3) { //Member는 스프링이 관리하는 빈이 아님 -> 없으면 Optional.empty
            System.out.println("setNoBean2 = " + noBean3); //Optional.empty
        }

        /**
         * 참고: `@Nullable`, `Optional`은 스프링 전반에 걸쳐서 지원된다. 예를 들어서 생성자 자동 주입에서 특정 필드에만 사용해도 된다.
         */
    }

}
