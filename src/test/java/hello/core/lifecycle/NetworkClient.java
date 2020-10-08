package hello.core.lifecycle;

/**
 * Created by frenchline707@gmail.com on 2020-10-08
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

public class NetworkClient {

    private String url; //접속해야 될 서버의 url

    public NetworkClient() { //생성할 때
        System.out.println("생성자 호출, url = " + url);
        connect(); //네트워크에 연결하고
        call("초기화 연결 메시지"); //메세지를 저쪽 서버에 보내는 의미
    }

    public void setUrl(String url) { //url은 외부에서 setter로 넣을 수 있도록
        this.url = url;
    }

    //서비스 시작시 호출
    public void connect() { //네트워크 연결
        System.out.println("connect: = " + url); //이 url에 연결하라는 의미로
    }

    public void call(String message) { //(연결이 된 상태에서) 연결한 서버에 메세지를 던짐
        System.out.println("call: " + url + " message = " + message);
    }

    //서비스 종료시 호출
    public void disconnect() {
        System.out.println("close " + url);
    }

}
