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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

public class HellobootApplication {

    public static void main(String[] args) {

        /*
            특정한 구현에 종속되지 않게 webServer추상화 하여 만들어 놨음
            ex)
            JettyServletWebServerFactory serverFactory = new JettyServletWebServerFactory();
        */
        TomcatServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
        /*
            Servlet을 ServletContainer에 추가
            Spring에 웹 모듈에 들어 있는 인터페이스 이므로 익명클래스를 만들어 처리
        */
        WebServer webServer = serverFactory.getWebServer(servletContext -> {
            servletContext.addServlet("frontcontroller", new HttpServlet() {
                @Override
                protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                    /*
                        FrontController(인증, 보안, 다국어, 공통 기능 등등 처리)
                            1. 서블릿코드 안에 중복된 코드 처리를 보완.
                            2. 웹 요청을 직접 적으로 Request 와 Response 유연하게 처리.
                    */

                    //파라미터 처리
                    if (req.getRequestURI().equals("/hello") && req.getMethod().equals(HttpMethod.GET.name())) {
                        String name = req.getParameter("name");

                        //웹 응답 처리
                        resp.setStatus(HttpStatus.OK.value());                                   //상태코드
                        resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);    //header
                        resp.getWriter().println("Hello " + name);                               //header에 해당하는 body
                    } else if (req.getRequestURI().equals("/user")) {
                        //
                    } else {
                        resp.setStatus(HttpStatus.NOT_FOUND.value());
                    }

                }
            //FrontController 처리
            }).addMapping("/*");
            //}).addMapping("/hello");
        });
        webServer.start();
    }

}
