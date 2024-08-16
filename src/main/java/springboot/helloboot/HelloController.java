package springboot.helloboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

public class HelloController {
    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    public String hello(String name) {
        //생성자 파라미터로 주입 하는 방식으로 변경
        //SimpleHelloService helloService = new SimpleHelloService();

        //유저 요청사항 검증(Object로 던져서 null 체크)
        return helloService.sayHello(Objects.requireNonNull(name));
    }
}
