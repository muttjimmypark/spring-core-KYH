package hello.core.singleton;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    static class TestConfig {

        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }

    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        //두개의 주문이 들어와서 쓰레드가 두개가 된 상태
        statefulService1.order("userA", 10000);
        statefulService2.order("userB", 20000);

        //1번 쓰레드를 조회하기 전에 2번 쓰레드 주문이 들어가버리면
        int price = statefulService1.getPrice();
        //싱글톤이라서 2번 쓰레드 주문값이 나와버린다
        System.out.println("price = " + price);

        //무상태로 설계하라는 말이 이거임
        //싱글톤 서비스가 필드를 갖지 않고
        //예를 들어 주문order의 return값으로 price가 나오게끔해서 다음 절차에 반영되게 코딩한다던지
    }
}