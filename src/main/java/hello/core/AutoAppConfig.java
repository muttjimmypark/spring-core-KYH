package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

//모든 빈을 탐색해서 자동으로 컨테이너에 끌어들임
//원래 만들어둔 AppConfig, TestConfig 등을 스캔하지 않도록 수동 설정 포함
@Configuration
@ComponentScan(
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {
    /*
    내용 없이 @Component 기재한 클래스들을 스캔해서
    @Autowired 기재한 생성자들끼리
    알아서 자동 의존관계 설정 시켜준다
     */
}
