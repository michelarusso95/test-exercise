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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PointControllerTest {

    private static final String BASE_PATH = "/point";

    @MockBean
    PointService pointService;
    private TestRestTemplate testRestTemplate = new TestRestTemplate();
    @LocalServerPort
    private int port;

    @Test
    void addPoint() {
        PointDTO pointDTO = new PointDTO();
        pointDTO.setX(Math.random());
        pointDTO.setY(Math.random());

        when(pointService.addPoint(any(PointDTO.class))).thenReturn(pointDTO);

        HttpEntity<PointDTO> entity = new HttpEntity<>(new PointDTO());

        ResponseEntity<PointDTO> responseEntity =
                testRestTemplate.exchange(
                        createURLWithPort(),
                        HttpMethod.POST,
                        entity,
                        PointDTO.class);
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCode().value());
        assertEquals(pointDTO, responseEntity.getBody());
        Mockito.reset(pointService);
    }

    @Test
    void addPointReturnsBadRequestIfPointIsDuplicate() {
        when(pointService.addPoint(any(PointDTO.class))).thenThrow(IllegalArgumentException.class);

        HttpEntity<PointDTO> entity = new HttpEntity<>(new PointDTO());

        ResponseEntity<PointDTO> responseEntity =
                testRestTemplate.exchange(
                        createURLWithPort(),
                        HttpMethod.POST,
                        entity,
                        PointDTO.class);
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCode().value());
        Mockito.reset(pointService);
    }

    private String createURLWithPort() {
        return "http://localhost:" + port + BASE_PATH;
    }
}
