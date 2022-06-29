package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

public class AppConfig {

    //서비스 구현체 선택 부분
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    //도메인(정책) 구현체 선택 부분
    //db 전환시 테이블,원소가 되는 부분이 도메인 아닌가?
    //그러면 이 부분은 컨트롤러-서비스-도메인-리포지토리 구분에서 서비스영역일까?
    //서비스가 참조하니까 도메인영역? <-> 리포지토리는 참조하지 않으니까 서비스영역은 아닌듯
    /*
    나름 정리해보자면 컨트롤러 - 서비스 - 리포지토리
                        - 도메인 - 정책
     */
    private DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }

    //리포지토리 구현체 선택 부분
    private MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}
