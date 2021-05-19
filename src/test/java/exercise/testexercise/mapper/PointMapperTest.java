package exercise.testexercise.mapper;

import exercise.testexercise.dto.PointDTO;
import exercise.testexercise.model.Point;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PointMapperTest {

    private PointMapper mapper = Mappers.getMapper(PointMapper.class);

    @Test
    void mapFromPoint() {
        Point point = new Point();
        point.setX(Math.random());
        point.setY(Math.random());

        PointDTO pointDTO = mapper.mapFromPoint(point);

        assertEquals(point.getX(), pointDTO.getX());
        assertEquals(point.getY(), pointDTO.getY());
    }

    @Test
    void mapFromPointDTO() {
        PointDTO pointDTO = new PointDTO();
        pointDTO.setX(Math.random());
        pointDTO.setY(Math.random());

        Point point = mapper.mapFromPointDTO(pointDTO);

        assertEquals(pointDTO.getX(), point.getX());
        assertEquals(pointDTO.getY(), point.getY());
    }
}
