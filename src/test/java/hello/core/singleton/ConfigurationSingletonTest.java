package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        assertThat(memberService.getMemberRepository()).isSameAs(orderService.getMemberRepository());
        assertThat(memberRepository).isSameAs(memberService.getMemberRepository());

    }

    //AppConfig에 soutm을 통해 로드되는걸 확인해봐도
    //서비스가 불릴때마다 리포지토리가 계속 호출되야하는데
    //각 한번씩만 호출되면서 싱글톤을 보장한다


    @Test
    void configurationDeep() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);

        System.out.println("bean.getClass() = " + bean.getClass());
        //bean.getClass() = class hello.core.AppConfig$$EnhancerBySpringCGLIB$$b61fb99a
        //스프링이 CGLIB이라는 바이트코드 조작 라이브러리를 통해
        //내가 만든 클래스를 상속받는 임의의 클래스를 만들어서 Bean으로 컨테이너에 등록하는 원리다
        //이때 싱글톤을 보장하는 내용이 첨가되게 된다

        //의존관계 명세한 config 클래스에 @Configuration 애노테이션이 없다면
        //컨테이너에서 관리하지 않는 Bean이 되므로 싱글톤 보장이 되지 않고 부르는데로 새로 불린다
    }
}
