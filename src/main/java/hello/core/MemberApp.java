package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import hello.core.member.MemberServiceImpl;

public class MemberApp {

    public static void main(String[] args) {

//        MemberService memberService = new MemberServiceImpl();

//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();

        //스프링 컨테이너에 올라간 모든 객체들을 관리하는 AC라는걸 호출
        // ㄴ> XML로 작성할수도 있고, 이거처럼 애노테이션 기반으로 작성할 수 있음
        // ㄴ> AC 상위의 BeanFactory도 있지만 거의 사용x
        // ㄴ> 따라서, ApplicationContext를 스프링 컨테이너라고 통용적으로 말한다
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        //Bean으로 컨테이너에 올려둔걸 (이름으로 get, 반환Type)
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        //이로써 코드의 제어권을 작성자가 갖는 : 라이브러리가 아닌
        //프로그램 자신이 제어권을 갖는 : 프레임워크로 전환이 이루어진다
        //(AppConfig를 직접 작성하는게 아니라, 프레임워크에 맡긴다고 작성한것)

        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("new member = " + member.getName());
        System.out.println("findMember = " + findMember.getName());
    }
}
