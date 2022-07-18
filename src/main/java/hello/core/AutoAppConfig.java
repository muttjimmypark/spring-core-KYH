package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

//모든 빈을 탐색해서 자동으로 컨테이너에 끌어들임
//원래 만들어둔 AppConfig, TestConfig 등을 스캔하지 않도록 수동 설정 포함
@Configuration
@ComponentScan(
//        basePackages = "hello.core.member", //디폴트는 자기자신의 소속과 그 하위 패키지 <- 프로젝트 최상단에 위치시키면 안건드려도 되겠지
//        basePackageClasses = AutoAppConfig.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {
    /*
    내용 없이 @Component 기재한 클래스들을 스캔해서
    @Autowired 기재한 생성자들끼리
    알아서 자동 의존관계 설정 시켜준다
     */

    //수동Bean vs 자동Bean 우선권은?
    //수동 빈 이름이 일부러 겹치도록 해보면
    //수동작성 빈이 자동 위에 오버라이딩 되면서 우선시 된다
//    @Bean(name = "memoryMemberRepository")
//    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//    }
    /*
    작성자가 완벽히 의도해서 작성한거라면 이게 맞는건데
    이게 실수라면 거슬러 올라가서 문제해결하기가 너무 꼬여있고 잡기 어려운 버그가 된다
    그래서 최근에는 스프링부트가 이거도 자동vs자동 처럼 에러 발생하도록 기본값이 변경되었다
     */
}
