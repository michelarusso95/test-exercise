package exercise.testexercise.controller;

import exercise.testexercise.dto.PointDTO;
import exercise.testexercise.service.PointService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "/lines")
public class LineController {

    private final PointService pointService;

    public LineController(PointService pointService) {
        this.pointService = pointService;
    }

    @GetMapping(path = "/{n}")
    public ResponseEntity<List<Set<PointDTO>>> getLines(@PathVariable Integer n) {
        return ResponseEntity.ok(pointService.getLines(n));
    }
}
