package exercise.testexercise.service;

import exercise.testexercise.dto.PointDTO;
import exercise.testexercise.mapper.PointMapper;
import exercise.testexercise.model.Point;
import exercise.testexercise.repository.PointRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class PointService {

    private static final String ERROR_DUPLICATE = "Point with given coordinates is already present.";

    private final PointRepository pointRepository;
    private final PointMapper pointMapper;

    public PointService(PointRepository pointRepository, PointMapper pointMapper) {
        this.pointRepository = pointRepository;
        this.pointMapper = pointMapper;
    }

    public void deletePoints() {
        pointRepository.deleteAll();
    }

    public List<PointDTO> getPoints() {
        return pointRepository.findAll().stream()
                .map(pointMapper::mapFromPoint)
                .collect(Collectors.toList());
    }

    public PointDTO addPoint(PointDTO pointDTO) {
        if (pointRepository.existsByXAndY(pointDTO.getX(), pointDTO.getY())) {
            throw new IllegalArgumentException(ERROR_DUPLICATE);
        }
        Point point = pointMapper.mapFromPointDTO(pointDTO);
        return pointMapper.mapFromPoint(pointRepository.save(point));
    }

    public List<Set<PointDTO>> getLines(Integer n) {
        List<Set<PointDTO>> result = new ArrayList<>();
        List<Point> points = pointRepository.findAll();
        for (int i = 0; i < points.size() - 1; i++) {
            for (int j = i+1; j < points.size(); j++) {
                Point pointA = points.get(i);
                Point pointB = points.get(j);
                Set<PointDTO> includedPoints = points.stream()
                        .filter(belongsToLine(pointA, pointB))
                        .map(pointMapper::mapFromPoint)
                        .collect(Collectors.toSet());
                boolean unique = result.stream().noneMatch(line -> line.containsAll(includedPoints));
                if (includedPoints.size() >= n && unique) {
                    result.add(includedPoints);
                }
            }
        }
        return result;
    }

    private Predicate<Point> belongsToLine(Point p1, Point p2) {
        if (p1.getX() == p2.getX()) {
            return point -> point.getX() == p1.getX();
        } else if (p1.getY() == p2.getY()) {
            return point -> point.getY() == p1.getY();
        }
        double m = getSlope(p1, p2);
        double q = p1.getY() - m*p1.getX();
        return point -> point.getY() == m*point.getX() + q;
    }

    private double getSlope(Point pointA, Point pointB) {
        double y = pointA.getY() - pointB.getY();
        double x = pointA.getX() - pointB.getX();
        return y/x;
    }
}
