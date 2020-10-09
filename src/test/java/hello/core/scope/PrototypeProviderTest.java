package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
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
     * 방법 2. ObjectFactory, ObjectProvider
     * : 지정한 빈을 컨테이너에서 대신 찾아주는 DL (Dependency Lookup) 서비스를 제공하는 것이 바로 `ObjectProvider`이다.
     * ( 참고로 과거에는 `ObjectFactory`가 있었는데, 여기에 편의 기능을 추가해서 `ObjectProvider`가 만들어졌다. )
     */
    @Scope("singleton") //<- 생략 가능, default가 @Scope("singleton")
    static class ClientBean {

        @Autowired
        private ObjectProvider<PrototypeBean> prototypeBeanProvider; //테스트니까 편하게 필드 주입함

        public int logic() {
            // ObjectProvider 사용해서 DL 받기
            PrototypeBean prototypeBean = prototypeBeanProvider.getObject(); //프로바이더가 제네릭에 지정한 타입의 빈을 대신 찾아주는 DL 기능만 제공
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
