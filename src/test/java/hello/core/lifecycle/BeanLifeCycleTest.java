package hello.core.lifecycle;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Created by frenchline707@gmail.com on 2020-10-08
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
//        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext("hello.core.lifecycle");
//        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
//        for (String beanDefinitionName : beanDefinitionNames) {
//            System.out.println("beanDefinitionName = " + beanDefinitionName);
//        }
        NetworkClient client = ac.getBean(NetworkClient.class);
        Assertions.assertThat(client.getUrl()).isEqualTo(ac.getBean(LifeCycleConfig.class).connectUrl());
        ac.close(); //ApplicationContext를 닫음, 스프링 컨테이너 종료 호출
    }

    @Configuration
    @ComponentScan
    static class LifeCycleConfig {

        private static final String CONNECTION_URL = "http://hello-spring.dev";
        private static final String SUB_CONNECTION_URL = "1일1커밋.com";

        @Bean
        @Primary
        public String connectUrl() {
            return CONNECTION_URL;
        }

        @Bean
        public String connectUrl2() {
            return SUB_CONNECTION_URL;
        }

//        @Bean
//        public NetworkClient networkClient() {
//            NetworkClient networkClient = new NetworkClient();
//            networkClient.setUrl("http://hello-spring.dev"); //외부에서 값 셋팅하는 의미로
//            return networkClient;
//        }

    }

}
