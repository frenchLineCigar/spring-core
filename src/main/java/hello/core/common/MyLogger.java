package hello.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

/**
 * Created by frenchline707@gmail.com on 2020-10-12
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS) //프록시 방식 사용(진짜가 아닌 껍데기를 먼저 집어 넣는다)
public class MyLogger { //로그를 출력하기 위한 클래스 : 이 빈은 HTTP 요청 당 하나씩 생성되고, HTTP 요청이 끝나는 시점에 소멸된다

    private String uuid;
    private String requestURL;
    private String method;

    public void setRequestURL(String requestURL) { //빈이 생성되는 시점에는 알 수 없으므로, 외부에서 setter로 입력 받는다
        this.requestURL = requestURL;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void log(String message) { //기대하는 공통포멧
        System.out.println("[" + uuid + "]" + "[" + method + "]" + "[" + requestURL + "] " + message);
    }

    @PostConstruct
    public void init() { //초기화 메서드
        uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] request scope bean create: " + this);
    }

    @PreDestroy
    public void close() { //소멸 메서드, request scope는 소멸 호출됨
        System.out.println("[" + uuid + "] request scope bean close: " + this);
    }

}
