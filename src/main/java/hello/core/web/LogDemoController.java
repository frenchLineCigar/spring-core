package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by frenchline707@gmail.com on 2020-10-12
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

@Controller
@RequiredArgsConstructor
public class LogDemoController { //로거 작동 확인용 테스트 컨트롤러

    private final LogDemoService logDemoService;
    private final MyLogger myLogger; //프록시 객체 덕분에 편리하게 request scope를 사용하고, 진짜 객체 조회를 꼭 필요한 시점까지 지연처리

    @RequestMapping(value = "log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) throws InterruptedException {
        String requestURL = request.getRequestURL().toString(); //요청 URL
        String method = request.getMethod(); //요청 메서드

        System.out.println("myLogger = " + myLogger.getClass());

        myLogger.setRequestURL(requestURL); //기능을 실제 호출하는 시점, 이때 진짜를 찾아서 동작한다.
        myLogger.setMethod(method);

        myLogger.log("controller test");
        Thread.sleep(1000);
        logDemoService.logic("testId"); //서비스에서도 같은 빈에 접근
        return "OK";
    }

    /*
    HttpServletRequest : 자바에서 제공하는 표준 서블릿 규약을 적용하여 HTTP Request 정보(고객 요청 정보)를 받을 수 있다
     */
}
