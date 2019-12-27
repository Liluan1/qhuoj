package edu.qhu.qhuoj.repository;

import edu.qhu.qhuoj.entity.TestPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestPointRepository extends JpaRepository<TestPoint, Integer> {
    List<TestPoint> findByProblem_Id(int problemId);

}
