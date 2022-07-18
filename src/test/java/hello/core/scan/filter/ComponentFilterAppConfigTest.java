package hello.core.scan.filter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.context.annotation.ComponentScan.*;

public class ComponentFilterAppConfigTest {

    @Test
    void filterScan() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);

        BeanA beanA = ac.getBean("beanA", BeanA.class);
        assertThat(beanA).isNotNull();

        assertThatThrownBy(() -> ac.getBean("beanB", BeanB.class))
                .isInstanceOf(NoSuchBeanDefinitionException.class);
    }

    @Configuration
    @ComponentScan(
            //필터 타입에 5가지 종류가 있다
//            includeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyIncludeComponent.class),
//            excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyExcludeComponent.class)
            includeFilters = @Filter(classes = MyIncludeComponent.class),
            excludeFilters = @Filter(classes = MyExcludeComponent.class)
    )
    static class ComponentFilterAppConfig {
        /*
        include는 @Component를 최대한 활용 (스프링 기본 설정을 우선 활용)하므로 잘 안씀
        필요에 따라 exclude나 가끔 쓸까 싶다고 함
         */
    }

}
