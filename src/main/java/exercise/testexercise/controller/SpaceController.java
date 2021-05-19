package exercise.testexercise.controller;

import exercise.testexercise.dto.PointDTO;
import exercise.testexercise.service.PointService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/space")
public class SpaceController {

    public static final String EMPTY_BODY_RESPONSE = "{}";


    private final PointService pointService;

    public SpaceController(PointService pointService) {
        this.pointService = pointService;
    }

    @GetMapping
    public ResponseEntity<List<PointDTO>> getPoints() {
        return ResponseEntity.ok(pointService.getPoints());
    }


    @DeleteMapping
    public ResponseEntity<String> deletePoints() {
        pointService.deletePoints();
        return ResponseEntity.ok(EMPTY_BODY_RESPONSE);
    }
}
