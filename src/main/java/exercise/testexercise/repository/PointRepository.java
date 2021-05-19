package exercise.testexercise.repository;

import exercise.testexercise.model.Point;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PointRepository extends CrudRepository<Point, UUID> {

    List<Point> findAll();
    boolean existsByXAndY(double x, double y);
}
