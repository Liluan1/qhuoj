package edu.qhu.qhuoj.repository;

import edu.qhu.qhuoj.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProblemRepository extends JpaRepository<Problem, Integer> {

    List<Problem> findByNameLike(String name);

    Problem findByName(String name);
}
