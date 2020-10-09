package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by frenchline707@gmail.com on 2020-10-09
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

/**
 * 싱글톤 빈과 프로토타입 빈을 함께 사용할 때, 어떻게 하면 사용할 때 마다 항상 새로운 프로토타입 빈을 생성할 수 있을까?
 */
public class PrototypeProviderTest {

    @Test
    void providerTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        Assertions.assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        Assertions.assertThat(count2).isEqualTo(1);

    }

    /**
     * 방법 1. 스프링 컨테이너에 요청
     * : 가장 간단한 방법은 싱글톤 빈이 프로토타입을 사용할 때 마다 스프링 컨테이너에 새로 요청하는 것이다.
     */
    static class ClientBean {

        @Autowired
        private ApplicationContext ac;

        public int logic() {
            //logic() 호출 시 마다 스프링 컨테이너에 새로 요청해서 프로토타입 빈을 새롭게 생성 받아서 쓴다
            PrototypeBean prototypeBean = ac.getBean(PrototypeBean.class);
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }

    }

    /* 프로토타입 빈 */
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
        public void init() { //프로토타입 빈도 초기화 메서드는 호출 O
            System.out.println("PrototypeBean.init " + this); //this -> 현재 나를 찍어줌, 나의 참조값 확인
        }

        @PreDestroy
        public void destroy() { //프로토타입 빈이므로 스프링 컨테이너 종료 시점에 이 소멸 메서드는 호출 X
            System.out.println("PrototypeBean.destroy");
        }
    }

}
