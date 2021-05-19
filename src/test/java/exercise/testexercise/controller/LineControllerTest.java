package exercise.testexercise.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import exercise.testexercise.dto.PointDTO;
import exercise.testexercise.service.PointService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class LineControllerTest {

    private static final String BASE_PATH = "/lines";
    private static final Integer N = 2;

    @MockBean
    PointService pointService;
    private TestRestTemplate testRestTemplate = new TestRestTemplate();
    @LocalServerPort
    private int port;

    @Test
    void getLines() {
        PointDTO point1 = new PointDTO();
        point1.setX(Math.random());
        point1.setY(Math.random());
        PointDTO point2 = new PointDTO();
        point2.setX(Math.random());
        point2.setY(Math.random());
        List<Set<PointDTO>> lines = Arrays.asList(Set.of(point1, point2));

        when(pointService.getLines(any())).thenReturn(lines);

        Map<String, Integer> uriParams = new HashMap<>();
        uriParams.put("n", N);
        UriComponentsBuilder uriComponentsBuilder =
                UriComponentsBuilder.fromUriString(createURLWithPort("/{n}"));

        ResponseEntity<String> responseEntity =
                testRestTemplate.exchange(
                        uriComponentsBuilder.buildAndExpand(uriParams).toUriString(),
                        HttpMethod.GET,
                        HttpEntity.EMPTY,
                        String.class);
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCode().value());
        assertTrue(responseEntity.getBody().contains(String.valueOf(point1.getX())));
        assertTrue(responseEntity.getBody().contains(String.valueOf(point1.getY())));
        assertTrue(responseEntity.getBody().contains(String.valueOf(point2.getX())));
        assertTrue(responseEntity.getBody().contains(String.valueOf(point2.getY())));
        Mockito.reset(pointService);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + BASE_PATH + uri;
    }
}
