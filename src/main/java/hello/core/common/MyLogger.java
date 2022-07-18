package hello.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

/*
provider 대신 scope의 proxyMode를 사용하면
의존성을 위해 호출될때? 다른 빈에서 부를때?
리퀘스트 이전에라도 빌드시에 proxy class가 주입되어진다
ㄴ> 또 CGLIB이 들어감 : 다시말해, [컨테이너에 있으면 그걸 리턴, 없으면 만들어서 리턴]이라는 껍데기 클래스가 주입되는것
(그럼 실제 리퀘스트가 이루어지면 대체되나?)
ㄴ> 실제 다른 클래스에서 호출되는 시점에 진짜가 대체된다. cglib 구조가 그럼 ㅇㅇ
 */
@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLogger {

    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message) {
        System.out.println("[" + uuid + "] [" + requestURL + "] [" + message + "]");
    }

    @PostConstruct
    public void init() {
        uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] request scope bean create : " + this);
    }

    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "] request scope bean close : " + this);
    }
}
