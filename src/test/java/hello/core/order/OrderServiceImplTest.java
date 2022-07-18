package hello.core.order;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class OrderServiceImplTest {

    /*
    의존관계주입을 생성자주입으로 선택하라고 하면서
    스프링에 전혀 의존하지 않는 테스트를 설계할수 있으며, 실무에서 많이 쓰인다는것을 보여주다가
    수업에서 그냥 일반적인 테스트를 하나 작성했다
     */
    @Test
    void createOrder() {
        MemoryMemberRepository memberRepository = new MemoryMemberRepository();
        memberRepository.save(new Member(1L, "name", Grade.VIP));

        OrderServiceImpl orderService = new OrderServiceImpl(memberRepository, new FixDiscountPolicy());
        Order order = orderService.createOrder(1L, "itemA", 10000);

        assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}