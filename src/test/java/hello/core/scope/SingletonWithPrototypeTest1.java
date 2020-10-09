package hello.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        //클라이언트 B
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);

        ac.close();
    }

    @Test
    void singletonClientUsePrototype() { //싱글톤 빈과 프로토타입 빈 함께 사용시 문제점
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);
        //클라이언트 A
        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic(); //생성시점에 주입된 프로토타입 빈을 계속 사용 -> 프로토타입 빈을 새로 생성하지 않는다
        assertThat(count1).isEqualTo(1);

        //클라이언트 B
        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic(); //생성시점에 주입된 프로토타입 빈을 계속 사용
        assertThat(count2).isEqualTo(1);

    }

    /* 싱글톤 빈 */
    @Scope("singleton") //-> 싱글톤이 default 라서 사실 @Scope 애노테이션 자체를 안적어도 됨
    static class ClientBean { //싱글톤 빈이 의존관계 주입을 통해서 프로토타입 빈을 주입받아 사용

//        private final PrototypeBean prototypeBean; //생성시점에 주입

        @Autowired
        ApplicationContext applicationContext;

//        @Autowired
//        public ClientBean(PrototypeBean prototypeBean) { //생성시점에 프로토타입 빈 주입 요청
//            this.prototypeBean = prototypeBean;
//        }

        public int logic() {
            PrototypeBean prototypeBean = applicationContext.getBean(PrototypeBean.class); //logic()을 호출할 때 마다 스프링 컨테이너에 프로토타입 빈 요청하고, 스프링 컨테이너는 다시 생성해서 반환
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
