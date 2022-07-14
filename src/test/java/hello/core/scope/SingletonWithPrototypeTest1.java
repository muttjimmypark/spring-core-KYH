package hello.core.scope;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.Assertions.*;

public class SingletonWithPrototypeTest1 {

    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init" + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }

    /*
    이렇게 해서는 싱글톤 생명주기에 프로토타입 스코프가 박혀있기 때문에
    의도대로 프로토타입 스코프를 중복되지않게 생성할 수 없다
     */
//    @Scope("singleton")
//    static class ClientBean {
//        private final PrototypeBean prototypeBean;
//
//        public ClientBean(PrototypeBean prototypeBean) {
//            this.prototypeBean = prototypeBean;
//        }
//
//        public int logic() {
//            prototypeBean.addCount();
//            return prototypeBean.getCount();
//        }
//    }
    /*
    방법 1. ObjectProvider<T>를 사용
    다양한 부가기능 메서드를 제공한다는 장점
     */
//    @Scope("singleton")
//    static class ClientBean {
//        private ObjectProvider<PrototypeBean> prototypeBeanProvider;
//
//        public ClientBean(ObjectProvider<PrototypeBean> prototypeBeanProvider) {
//            this.prototypeBeanProvider = prototypeBeanProvider;
//        }
//        //다 되는데 RequiredArgsConstructor는 왜 적용이 안될까?
//        //별도의 애노테이션이 있나...
//
//        public int logic() {
//            PrototypeBean prototypeBean = prototypeBeanProvider.getObject();
//            prototypeBean.addCount();
//            return prototypeBean.getCount();
//        }
//    }
    /*
    방법 2. 자바표준(JSR-330) Provider<T>를 사용
    자바 표준이라 기능이 간결하고, 다른 컨테이너와도 호환되는게 장점
     */
    @Scope("singleton")
    static class ClientBean {
        private Provider<PrototypeBean> prototypeBeanProvider;

        public ClientBean(Provider<PrototypeBean> prototypeBeanProvider) {
            this.prototypeBeanProvider = prototypeBeanProvider;
        }

        public int logic() {
            PrototypeBean prototypeBean = prototypeBeanProvider.get();
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }
    /*
    결국 이 두가지의 방법은
    좁게봐서 프로토타입 스코프로 정의한 빈이 의도대로 활용되게끔만을 위한 것이 아니라
    넓게봐서 여러모로 DL(의존성 탐색)이 필요할때 쓰는 방법이라고 알면 되겠다
    (기존 di 관점에서 봤을때
    늦게 주입해야한다던지, 경우에따라 다른걸 주입받는다던지, 순환의존성이 필요하다던지)
    거의 안쓰지만, 코드 분석시에 가끔 발견됨...?
     */

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);
    }

    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext(PrototypeBean.class, ClientBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);
    }
}
