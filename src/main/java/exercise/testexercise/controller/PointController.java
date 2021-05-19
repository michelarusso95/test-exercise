package exercise.testexercise.controller;

import exercise.testexercise.dto.PointDTO;
import exercise.testexercise.service.PointService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/point")
public class PointController {

    private final PointService pointService;

    public PointController(PointService pointService) {
        this.pointService = pointService;
    }

    @PostMapping
    public ResponseEntity<?> addPoint(@RequestBody PointDTO pointDTO) {
        try {
            return ResponseEntity.ok(pointService.addPoint(pointDTO));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
