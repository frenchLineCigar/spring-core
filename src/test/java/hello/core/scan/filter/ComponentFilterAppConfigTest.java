package hello.core.scan.filter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.context.annotation.ComponentScan.Filter;

/**
 * Created by frenchline707@gmail.com on 2020-09-29
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

/**
 * 나만의 Component 를 만들고, ComponentScan 타겟으로 추가/제외 하기
 * - includeFilters : 스프링 빈에 등록되는 대상
 * - excludeFilters : 스프링 빈에서 제외되는 대상
 */
public class ComponentFilterAppConfigTest {

    @Test
    void filterScan() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);
        BeanA beanA = ac.getBean("beanA", BeanA.class);
        assertThat(beanA).isNotNull();

        //BeanB beanB = ac.getBean("beanB", BeanB.class);
        assertThrows(NoSuchBeanDefinitionException.class, () -> ac.getBean("beanB", BeanB.class));
    }

    @Configuration
    @ComponentScan(
//            basePackages = "hello.core",
//            useDefaultFilters = false, // -> Indicates whether automatic detection of classes annotated with @Component @Repository, @Service, or @Controller should be enabled.
            includeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyIncludeComponent.class),
            excludeFilters = {
                    @Filter(type = FilterType.ANNOTATION, classes = MyExcludeComponent.class), // ANNOTATION : 기본값(생략가능), 애노테이션을 인식해서 동작
//                    @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = BeanA.class) // ASSIGNABLE_TYPE 지정한 타입과 자식 타입을 인식해서 동작
            }
    )
    static class ComponentFilterAppConfig {

    }
}
