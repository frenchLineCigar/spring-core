package hello.core.order;

/**
 * Created by frenchline707@gmail.com on 2020-09-28
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

public interface OrderService {
    Order createOrder(Long memberId, String itemName, int itemPrice);
}
