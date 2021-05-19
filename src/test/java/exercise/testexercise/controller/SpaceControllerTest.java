package exercise.testexercise.controller;

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

import static exercise.testexercise.controller.SpaceController.EMPTY_BODY_RESPONSE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class SpaceControllerTest {

    private static final String BASE_PATH = "/space";

    @MockBean
    PointService pointService;
    private TestRestTemplate testRestTemplate = new TestRestTemplate();
    @LocalServerPort
    private int port;

    @Test
    void getPoints() {
        PointDTO point1 = new PointDTO();
        point1.setX(Math.random());
        point1.setY(Math.random());
        PointDTO point2 = new PointDTO();
        point2.setX(Math.random());
        point2.setY(Math.random());
        List<PointDTO> lines = Arrays.asList(point1, point2);

        when(pointService.getPoints()).thenReturn(lines);

        ResponseEntity<String> responseEntity =
                testRestTemplate.exchange(
                        createURLWithPort(),
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

    @Test
    void deletePoints() {
        doNothing().when(pointService).deletePoints();

        ResponseEntity<String> responseEntity =
                testRestTemplate.exchange(
                        createURLWithPort(),
                        HttpMethod.DELETE,
                        HttpEntity.EMPTY,
                        String.class);
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCode().value());
        assertEquals(EMPTY_BODY_RESPONSE, responseEntity.getBody());
        Mockito.reset(pointService);
    }

    private String createURLWithPort() {
        return "http://localhost:" + port + BASE_PATH;
    }
}
