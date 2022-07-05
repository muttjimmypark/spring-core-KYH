package hello.core.scan.filter;

import java.lang.annotation.*;

@Target(ElementType.TYPE)   //TYPE : 클래스 레벨에 붙는 애노테이션임을 설정
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyExcludeComponent {
}
