package hello.core.singleton;

/**
 * Created by frenchline707@gmail.com on 2020-09-29
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

/**
 * 상태를 유지할 경우 발생하는 문제점 해결
 * : 무상태(stateless)로 설계 -> 필드 대신에 자바에서 공유되지 않는, 지역변수, 파라미터, ThreadLocal 등을 사용
 */
public class StatefulService {

//    private int price; //상태를 유지하는 필드 10000 -> 20000

    public int order(String name, int price) {
        System.out.println("name = " + name + " price = " + price);
//        this.price = price; //여기가 문제!
        return price;
    }

//    public int getPrice() {
//        return price;
//    }

}
