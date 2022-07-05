package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너의 문제점")
    void pureContainer() {

        AppConfig appConfig = new AppConfig();

        //조회 1
        MemberService memberService1 = appConfig.memberService();

        //조회 2
        MemberService memberService2 = appConfig.memberService();

        //참조값이 다르다
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);
        assertThat(memberService1).isNotEqualTo(memberService2);

        //싱글톤으로 생성되지 않았다는것을 확인하기위해 이번경우는 sameAs를 쓰는게 맞는듯?
        //equalTo : equals 처럼 내용 비교, sameAs : == 처럼 인스턴스가 같은지 주소값 비교
        assertThat(memberService1).isNotSameAs(memberService2);
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest() {
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        System.out.println("singletonService1 = " + singletonService1);
        System.out.println("singletonService2 = " + singletonService2);
        assertThat(singletonService1).isSameAs(singletonService2);

        /*
        클라이언트가 구체 클래스에 의존하게 되므로
        DIP 위반, OCP 위반 가능성이 높아진다
        테스트, 속성변경, 초기화가 어렵다 + 자식클래스 생성 어렵다
        ㄴ> 유연성이 떨어진다
        안티 패턴으로 불린다

        스프링에 의존하면 컨테이너가 모든 객체를 싱글톤으로 관리하므로
        문제점을 전부 해결한다
        */
    }

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer() {

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        //조회 1
        MemberService memberService1 = ac.getBean("memberService", MemberService.class);

        //조회 2
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);

        //참조값이 같다
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);
        assertThat(memberService1).isSameAs(memberService2);
    }
}
