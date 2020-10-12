package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Created by frenchline707@gmail.com on 2020-10-12
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

@Service
@RequiredArgsConstructor
public class LogDemoService {

    private final MyLogger myLogger;

    public void logic(String id) {
        myLogger.log("service id = " + id);
    }
}
