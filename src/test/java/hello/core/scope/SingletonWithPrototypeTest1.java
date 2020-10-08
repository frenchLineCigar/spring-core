package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by frenchline707@gmail.com on 2020-10-09
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind() { //스프링 컨테이너에 프로토타입 빈을 직접 요청
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        //클라이언트 A
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        Assertions.assertThat(prototypeBean1.getCount()).isEqualTo(1);

        //클라이언트 B
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        Assertions.assertThat(prototypeBean2.getCount()).isEqualTo(1);

        ac.close();
    }

    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init " + this); //this -> 현재 나를 찍어줌, 나의 참조값 확인
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }

    /**
     * 프로토타입 스코프 - 싱글톤 빈과 함께 사용시 문제점
     *
     * 스프링 컨테이너에 프로토타입 스코프의 빈을 요청하면 항상 새로운 객체 인스턴스를 생성해서 반환한다.
     * 하지만 (그러지 않을 때가 있다!!) 싱글톤 빈과 함께 사용할 때는 의도한 대로 잘 동작하지 않으므로 주의해야 한다.
     *
     * 코드는 스프링 컨테이너에 `프로토타입 빈을 직접 요청`하는 예제이다
     *
     * *스프링 컨테이너에 프로토타입 빈 직접 요청 1*
     *
     * 1. 클라이언트A는 스프링 컨테이너에 프로토타입 빈을 요청한다.
     * 2. 스프링 컨테이너는 프로토타입 빈을 새로 생성해서 반환(PrototypeBean@x01)한다. 해당 빈의 count 필드 값은 0이다.
     * 3. 클라이언트는 조회한 프로토타입 빈에 `addCount()`를 호출하면서 count 필드를 +1 한다.
     * 4. 결과적으로 프로토타입 빈(PrototypeBean@x01)의 count는 1이 된다.
     *
     * *스프링 컨테이너에 프로토타입 빈 직접 요청 2*
     *
     * 1. 클라이언트B는 스프링 컨테이너에 프로토타입 빈을 요청한다.
     * 2. 스프링 컨테이너는 프로토타입 빈을 새로 생성해서 반환(PrototypeBean@x01)한다. 해당 빈의 count 필드 값은 0이다.
     * 3. 클라이언트는 조회한 프로토타입 빈에 `addCount()`를 호출하면서 count 필드를 +1 한다.
     * 4. 결과적으로 프로토타입 빈(PrototypeBean@x02)의 count는 1이 된다.
     */
}
