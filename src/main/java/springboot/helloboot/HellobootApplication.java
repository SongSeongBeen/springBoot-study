package springboot.helloboot;

import org.springframework.boot.SpringApplication;
import springboot.config.MySpringBootApplication;
/*
    Configuration을 사용하는 클래스가 AnnotationConfig를 이용하는 애플리케이션 컨텍스에 처음 등록된다.
    Configuration 붙은 클래스는 BeanFactory Method를 가지는 것 이상으로 전체 애플리케이션을 구성하는데 필요한 중요한 정보를 많이 넣을 수 있다.
*/

@MySpringBootApplication
/*
@Configuration
@ComponentScan  //컴넌트가 붙은 클래스를 빈으로 등록
*/
public class HellobootApplication {
    /* spring boot 랑 동일하게 구성 Config Class로 이동
    @Bean
    public ServletWebServerFactory servletWebServerFactory() {
        return new TomcatServletWebServerFactory();
    }

    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }
    */
    public static void main(String[] args) {
        //SpringApplication으로 원복
        SpringApplication.run(HellobootApplication.class, args);
        // MySpringApplication.run(HellobootApplication.class, args);
    }

}


