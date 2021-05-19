package exercise.testexercise.service;

import exercise.testexercise.dto.PointDTO;
import exercise.testexercise.mapper.PointMapper;
import exercise.testexercise.model.Point;
import exercise.testexercise.repository.PointRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class PointServiceTest {

    @MockBean
    private PointRepository pointRepository;
    @MockBean
    private PointMapper pointMapper;

    @Autowired
    private PointService testInstance = new PointService(pointRepository, pointMapper);

    @Test
    void deletePoints() {
        testInstance.deletePoints();

        verify(pointRepository).deleteAll();
    }

    @Test
    void getPoints() {
        PointDTO pointDTO = createDTO();
        when(pointRepository.findAll()).thenReturn(List.of(new Point()));
        when(pointMapper.mapFromPoint(any(Point.class))).thenReturn(pointDTO);

        List<PointDTO> points = testInstance.getPoints();

        assertEquals(pointDTO.getX(), points.get(0).getX());
        assertEquals(pointDTO.getY(), points.get(0).getY());
    }

    @Test
    void addPoint() {
        PointDTO point = createDTO();
        when(pointRepository.existsByXAndY(any(double.class), any(double.class))).thenReturn(false);
        when(pointMapper.mapFromPointDTO(point)).thenReturn(new Point());
        when(pointRepository.save(any(Point.class))).thenReturn(new Point());
        when(pointMapper.mapFromPoint(any(Point.class))).thenReturn(point);

        PointDTO pointDTO = testInstance.addPoint(point);

        assertEquals(point.getX(), pointDTO.getX());
        assertEquals(point.getY(), pointDTO.getY());
    }

    @Test
    void addPointThrowsExceptionIfPointIsDuplicate() {
        PointDTO point = createDTO();
        when(pointRepository.existsByXAndY(any(double.class), any(double.class))).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> testInstance.addPoint(point));
    }

    @Test
    void getLines() {
        Point p1 = createPoint(1, 1);
        Point p2 = createPoint(2, 2);
        Point p3 = createPoint(3, 3);
        when(pointRepository.findAll()).thenReturn(List.of(p1, p2, p3));
        when(pointMapper.mapFromPoint(any(Point.class))).thenReturn(createDTO(), createDTO(), createDTO());

        List<Set<PointDTO>> lines = testInstance.getLines(3);

        assertEquals(1, lines.size());
        assertEquals(3, lines.get(0).size());
    }

    @Test
    void getLinesReturnsEmptyListIfNoLineHasGivenNumberOfPoints() {
        Point p1 = createPoint(1, 2);
        Point p2 = createPoint(5, 3);
        Point p3 = createPoint(4, 1);
        when(pointRepository.findAll()).thenReturn(List.of(p1, p2, p3));

        List<Set<PointDTO>> lines = testInstance.getLines(3);

        assertTrue(lines.isEmpty());
    }

    @Test
    void getLinesWhenXIsConstant() {
        Point p1 = createPoint(3, 1);
        Point p2 = createPoint(3, 2);
        Point p3 = createPoint(3, 3);
        when(pointRepository.findAll()).thenReturn(List.of(p1, p2, p3));
        when(pointMapper.mapFromPoint(any(Point.class))).thenReturn(createDTO(), createDTO(), createDTO());

        List<Set<PointDTO>> lines = testInstance.getLines(3);

        assertEquals(1, lines.size());
        assertEquals(3, lines.get(0).size());
    }

    @Test
    void getLinesWhenYIsConstant() {
        Point p1 = createPoint(1, 3);
        Point p2 = createPoint(2, 3);
        Point p3 = createPoint(3, 3);
        when(pointRepository.findAll()).thenReturn(List.of(p1, p2, p3));
        when(pointMapper.mapFromPoint(any(Point.class))).thenReturn(createDTO(), createDTO(), createDTO());

        List<Set<PointDTO>> lines = testInstance.getLines(3);

        assertEquals(1, lines.size());
        assertEquals(3, lines.get(0).size());
    }

    @Test
    void getLinesWhenXIsOrigin() {
        Point p1 = createPoint(0, 1);
        Point p2 = createPoint(0, 2);
        Point p3 = createPoint(0, 3);
        when(pointRepository.findAll()).thenReturn(List.of(p1, p2, p3));
        when(pointMapper.mapFromPoint(any(Point.class))).thenReturn(createDTO(), createDTO(), createDTO());

        List<Set<PointDTO>> lines = testInstance.getLines(3);

        assertEquals(1, lines.size());
        assertEquals(3, lines.get(0).size());
    }

    @Test
    void getLinesWhenYIsOrigin() {
        Point p1 = createPoint(1, 0);
        Point p2 = createPoint(2, 0);
        Point p3 = createPoint(3, 0);
        when(pointRepository.findAll()).thenReturn(List.of(p1, p2, p3));
        when(pointMapper.mapFromPoint(any(Point.class))).thenReturn(createDTO(), createDTO(), createDTO());

        List<Set<PointDTO>> lines = testInstance.getLines(3);

        assertEquals(1, lines.size());
        assertEquals(3, lines.get(0).size());
    }

    private Point createPoint(double x, double y) {
        Point point = new Point();
        point.setId(UUID.randomUUID());
        point.setX(x);
        point.setY(y);
        return point;
    }

    private PointDTO createDTO() {
        PointDTO pointDTO = new PointDTO();
        pointDTO.setX(Math.random());
        pointDTO.setY(Math.random());
        return pointDTO;
    }
}
