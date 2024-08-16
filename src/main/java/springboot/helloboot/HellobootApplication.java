package springboot.helloboot;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.IOException;

public class HellobootApplication {

    public static void main(String[] args) {
        //DispatcherServilet사용시 WebApplicationContext 타입을 전송
        GenericWebApplicationContext applicationContext = new GenericWebApplicationContext();
        /*
            SpringContainer
            싱글톤 패턴 처럼 사용(하나의 객체를 재사용)
            스프링 컨테이너 = 싱글톤 레지스트리
            필요로 하는 object를 한번만 만든다.getBean 요청시 (FrontConterller와 새로 추가될 Servlet은 같은 HelloController object를 사용한다.)
            
        */
        //GenericApplicationContext applicationContext = new GenericApplicationContext();
        //object를 직접 만들어서 넣어주는것도 가능하나 일반적으로 어떤 클래스는 사용할것인가 메타 정보를 넣어준다.

        /* 빈등록 */
        applicationContext.registerBean(HelloController.class);
        /*
             DI - 인터페스를 중간에 두고 코드 레벨의 의존고나계를 제거 한 다음, 동적으로 스프링컨테이너 적용
             Assembler를 통해서 둘 사이의 연관관계를 주입이라는 방법을 사용해서 지정하도록
        */
        applicationContext.registerBean(SimpleHelloService.class); //HelloServiceInterface
        applicationContext.refresh(); //초기화 object 만들어줌

        //독립실행이 가능한 Servlet Container
        /*
            특정한 구현에 종속되지 않게 webServer추상화 하여 만들어 놨음
            ex)
            JettyServletWebServerFactory serverFactory = new JettyServletWebServerFactory();
        */
        ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
        /*  Servlet 기술만 사용
            Servlet을 ServletContainer에 추가
            Spring에 웹 모듈에 들어 있는 인터페이스 이므로 익명클래스를 만들어 처리
        */
        WebServer webServer = serverFactory.getWebServer(new ServletContextInitializer[]{servletContext -> {
            //DispatcherServilet으로 변경(WebApplicationContext 타입을 전송)
            servletContext.addServlet("dispatcherServilet", new DispatcherServlet(applicationContext)
            //SpringContainer 처리로 인해 주석 처리
            //HelloController 매 요청마다 새로운 인스턴스 생성 할 필요없음
            //HelloController helloController = new HelloController();
            /*
            servletContext.addServlet("frontcontroller", new HttpServlet() {

                @Override
                protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

                        FrontController(인증, 보안, 다국어, 공통 기능 등등 처리)
                            1. 서블릿코드 안에 중복된 코드 처리를 보완.
                            2. 웹 요청을 직접 적으로 Request 와 Response 유연하게 처리.

                    //GET "/hello"
                    if (req.getRequestURI().equals("/hello") && req.getMethod().equals(HttpMethod.GET.name())) {
                        String name = req.getParameter("name");
                            helloController 에서 값을 꺼내는 부분은 FrontController이 담당
                            웹MVC에서 바인딩 되는 원리는 많은 지식과 복잡하지만
                            바인딩의 기본적인 원리는 웹 요청이 어떻게 생겼는지 알고 직접적으로 액세스하는 FrontController와 같은 코드에서
                            이걸 처리하는 오브젝트에게 평범한 데이터 타입으로 전환해서 넘겨주는 작업

                        //Spring Container 처리
                        HelloController helloController = applicationContext.getBean(HelloController.class);//object 타입 참조 처리(bean이름 or 클래스 타입 사용)
                        String ret = helloController.hello(name);

                        //웹 응답 처리(200 코드는 명시적으로 사용 안함)
                        //resp.setStatus(HttpStatus.OK.value());                                   //상태코드

                        //메서드사용 변경
                        resp.setContentType(MediaType.TEXT_PLAIN_VALUE);
                        //resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);    //header

                        //FrontController 기능 분리
                        //resp.getWriter().println("Hello " + name);                             //header에 타입에 해당하는 body
                        resp.getWriter().println(ret);
                    } else {
                        resp.setStatus(HttpStatus.NOT_FOUND.value());
                    }

                } */
                //FrontController 처리
            ).addMapping("/*");
            //}).addMapping("/hello");
        }});
        webServer.start();
    }

}
