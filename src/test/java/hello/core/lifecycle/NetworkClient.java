package hello.core.lifecycle;

/**
 * Created by frenchline707@gmail.com on 2020-10-08
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

public class NetworkClient {

    private String url; //접속 서버 url

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
    }

    public void setUrl(String url) { //url은 외부에서 setter로 넣을 수 있도록
        System.out.println("NetworkClient.setUrl, url = " + url);
        this.url = url;
    }

    //서비스 시작시 호출
    public void connect() {
        System.out.println("connect: " + url);
    }

    public void call(String message) {
        System.out.println("call: " + url + " message = " + message);
    }

    //서비스 종료시 호출
    public void disconnect() {
        System.out.println("close " + url);
    }

    public void init() { //프로퍼티 셋팅이 끝나면 호출 => 의존관계 주입이 끝나면 호출
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메시지");
    }

    public void close() { //빈이 종료될 때(스프링 컨테이너가 종료될 때) 호출
        System.out.println("NetworkClient.close");
        disconnect();
    }

}
