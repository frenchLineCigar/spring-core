package hello.core.annotation;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by frenchline707@gmail.com on 2020-10-07
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

/**
 * 애노테이션 직접 만들어 활용하기
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Qualifier("mainDiscountPolicy")
public @interface MainDiscountPolicy { //코드 추적이 가능 : MainDiscountPolicy에 커서 위치한 뒤, Ctrl + B(또는 클릭) (Win OS)
}
