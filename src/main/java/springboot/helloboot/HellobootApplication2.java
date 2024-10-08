package springboot.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
/*
    Configuration을 사용하는 클래스가 AnnotationConfig를 이용하는 애플리케이션 컨텍스에 처음 등록된다.
    Configuration 붙은 클래스는 BeanFactory Method를 가지는 것 이상으로 전체 애플리케이션을 구성하는데 필요한 중요한 정보를 많이 넣을 수 있다.
*/

//@Configuration
//@ComponentScan  //컴넌트가 붙은 클래스를 빈으로 등록
//public class HellobootApplication2 {

    //@Bean
    //public ServletWebServerFactory servletWebServerFactory() {
    //    return new TomcatServletWebServerFactory();
    //}

    //@Bean
    //public DispatcherServlet dispatcherServlet() {
    //    return new DispatcherServlet();
    //}
    /*
    //BeanFactory Method 사용 방식
    @Bean
    public HelloController helloController (HelloService helloService) {
        return new HelloController(helloService);
    }
    @Bean
    public HelloService helloService () {
        return new SimpleHelloService();
    }
    */
    /*
    public static void main(String[] args) {
        //Java 코드로 만든 Configuration 사용으로 인한 변경
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext() {
        //GenericWebApplicationContext applicationContext = new GenericWebApplicationContext() { //DispatcherServilet 사용시 WebApplicationContext 타입을 전송
            @Override
            protected void onRefresh() {
                super.onRefresh();
                ServletWebServerFactory servletWebServerFactory = this.getBean(ServletWebServerFactory.class);
                DispatcherServlet dispatcherServlet = this.getBean(DispatcherServlet.class);

                //없어도 스프링 컨테이너가 해준다.(디스패처 서블릿 type hierarchy 살펴보기 Ctrl+Shift+H)
                //dispatcherServlet.setApplicationContext(this); //생성자 없이 생성하여 스프링 컨테이너 주입

                //Bean Factory Method 재구성
                WebServer webServer = servletWebServerFactory.getWebServer(servletContext -> {
                   servletContext.addServlet("dispatcherServlet", dispatcherServlet)
                           .addMapping("/*");
                });

                ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
                WebServer webServer = serverFactory.getWebServer(servletContext -> {
                    servletContext.addServlet("dispatcherServlet", new DispatcherServlet(this) )//자기자신 참고
                            .addMapping("/*");
                });


               // webServer.start();
            }
        };
        */

        /*
            SpringContainer
            싱글톤 패턴 처럼 사용(하나의 객체를 재사용)
            스프링 컨테이너 = 싱글톤 레지스트리
            필요로 하는 object를 한번만 만든다.getBean 요청시 (FrontConterller와 새로 추가될 Servlet은 같은 HelloController object를 사용한다.)
            
        */
        //GenericApplicationContext applicationContext = new GenericApplicationContext();
        //object를 직접 만들어서 넣어주는것도 가능하나 일반적으로 어떤 클래스는 사용할것인가 메타 정보를 넣어준다.

        /* 빈등록 */
        //applicationContext.registerBean(HelloController.class);
        /*
             DI - 인터페스를 중간에 두고 코드 레벨의 의존고나계를 제거 한 다음, 동적으로 스프링컨테이너 적용
             Assembler를 통해서 둘 사이의 연관관계를 주입이라는 방법을 사용해서 지정하도록
        */
        //Java 코드로 만든 Configuration 사용으로 인한 변경(클래스로 변경)
        //applicationContext.register(HellobootApplication2.class);
        //applicationContext.registerBean(SimpleHelloService.class); //HelloServiceInterface
        //applicationContext.refresh(); //초기화작업(Template method 패턴을 사용 Hook 메소드 주입 onRefresh)   object 만들어줌

        //독립실행이 가능한 Servlet Container
        /*
            특정한 구현에 종속되지 않게 webServer추상화 하여 만들어 놨음
            ex)
            JettyServletWebServerFactory serverFactory = new JettyServletWebServerFactory();
        */


        //초기화작업(Template method 패턴을 사용 Hook 메소드 주입 onRefresh)
        //ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
        /*  Servlet 기술만 사용
            Servlet을 ServletContainer에 추가
            Spring에 웹 모듈에 들어 있는 인터페이스 이므로 익명클래스를 만들어 처리
        */
        //DispatcherServilet으로 변경(WebApplicationContext 타입을 전송)
        //WebServer webServer = serverFactory.getWebServer(new ServletContextInitializer[]{servletContext -> {
        //WebServer webServer = serverFactory.getWebServer(servletContext -> {
        //   servletContext.addServlet("dispatcherServilet"
        //            , new DispatcherServlet(applicationContext)
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
         //   ).addMapping("/*");
            //}).addMapping("/hello");
        //}});
       // });
       //webServer.start();
//    }

//}
