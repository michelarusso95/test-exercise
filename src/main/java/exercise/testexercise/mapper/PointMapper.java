package exercise.testexercise.mapper;

import exercise.testexercise.dto.PointDTO;
import exercise.testexercise.model.Point;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PointMapper {

    Point mapFromPointDTO(PointDTO pointDTO);
    PointDTO mapFromPoint(Point point);
}
