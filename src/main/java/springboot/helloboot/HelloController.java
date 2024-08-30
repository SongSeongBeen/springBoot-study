package springboot.helloboot;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/*
    1. DispatcherServlet은 ApplicationContext를 생성자로 받아 빈을 찾아 웹요청 처리할 수 있는 매핑 정보의 클래스를 찾는다.
    2. 매핑에 사용할 매핑테이블을 만들어 놓는다.
    3. Method 레벨까지 다 찾을려면 힘드니 @RequestMapping 어노테이션을 사용하여 클래스 레벨에 어노테이션을 추가
    -----------------------
    @RestController 해당 어노테이션은 DispatcherServlet 하고 직접 관련은 없음
    스프링 컨테이너를 사용하는 또 다른 방법을 적용하는 시점에 필요한 어노테이션
    -----------------------
    @Controller
    스프링 부트 3에서 스프링 버전이 업데이트가 되면서 @RequestMapping만으로 DispatcherServlet이 인식하던 기능이 더 이상 지원되지 않고 @Controller까지 등록이 필요
*/
@RestController
//@Controller
//@RequestMapping("/hello")
//@MyComponent  //BeanFactory Method 대신 스프링 컨테이너 컴포넌트 명시
public class HelloController {
    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    //해당 어노테이션이 없으면 지금 상황에서는 view가 없는 상태(String 값을 그대로 web응답 body에 넣어서 전달하게 하는 텍스트 플레인 처리). 404 error
    //@Rest 라는 이름이 붙으면 @ResponseBody 있따고 가정함.
    @GetMapping("/hello")
    //@ResponseBody
    public String hello(String name) {
        //생성자 파라미터 주입 하는 방식으로 변경
        //SimpleHelloService helloService = new SimpleHelloService();

        //유저 요청사항 검증(Object로 던져서 null 체크)
        return helloService.sayHello(Objects.requireNonNull(name));
    }
}
