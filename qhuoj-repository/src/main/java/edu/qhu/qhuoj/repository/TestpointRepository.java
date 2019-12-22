package edu.qhu.qhuoj.repository;

import edu.qhu.qhuoj.entity.Testpoint;
import org.aspectj.weaver.ast.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestpointRepository extends JpaRepository<Testpoint, Integer> {
    List<Testpoint> findByProblem_Id(int problemId);

}
