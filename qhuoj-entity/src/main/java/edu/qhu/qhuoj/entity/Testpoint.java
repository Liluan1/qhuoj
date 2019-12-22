package edu.qhu.qhuoj.entity;

import javax.persistence.*;

@Entity
@Table(name = "checkpoint")
public class Testpoint {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    @Override
    public String toString() {
        return "Testpoint{" +
                "id=" + id +
                ", problem=" + problem +
                ", score=" + score +
                ", input='" + input + '\'' +
                ", output='" + output + '\'' +
                '}';
    }

    @Id
    @Column(name = "checkpoint_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(targetEntity = Problem.class)
    @JoinColumn(name = "problem_id")
    private Problem problem;
    @Column(name = "checkpoint_score")
    private int score;
    @Column(name = "checkpoint_input")
    private String input;
    @Column(name = "checkpoint_output")
    private String output;

}
