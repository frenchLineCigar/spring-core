package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
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
    private final ObjectProvider<MyLogger> myLoggerProvider; //MyLogger 를 주입받는 게 아니라, MyLogger 를 찾을 수 있는(DL, Dependency Lookup) 빈이 주입 됨

    @RequestMapping(value = "log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) throws InterruptedException {
        String requestURL = request.getRequestURL().toString(); //요청 URL
        String method = request.getMethod(); //요청 메서드
        MyLogger myLogger = myLoggerProvider.getObject(); //프로바이더에 DL, 이 시점에 request 스코프 빈이 생성된다
        myLogger.setRequestURL(requestURL);
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
