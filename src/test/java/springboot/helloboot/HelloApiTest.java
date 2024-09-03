package springboot.helloboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloApiTest {

    @Test
    void helloApi() {
        // http localhost:8080/hello?name=Spring
        // HTTPie
        TestRestTemplate testRest = new TestRestTemplate();

        ResponseEntity<String> res =
                testRest.getForEntity("http://localhost:8080/hello?name={name}", String.class, "Spring");

        // 검증 (status code 200, header(content-type text/plain), body hello Spring)
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);    //http status에 Enum 값과 비교
        assertThat(res.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE)).startsWith(MediaType.TEXT_PLAIN_VALUE);
        assertThat(res.getBody()).isEqualTo("Hello Spring");
    }

    @Test
    void failsHelloApi() {
        // http localhost:8080/hello?name=Spring
        // HTTPie
        TestRestTemplate testRest = new TestRestTemplate();

        ResponseEntity<String> res =
                testRest.getForEntity("http://localhost:8080/hello?name=", String.class);

        // 검증 (status code 200, header(content-type text/plain), body hello Spring)
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
