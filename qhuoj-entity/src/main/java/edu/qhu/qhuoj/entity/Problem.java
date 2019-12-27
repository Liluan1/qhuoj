package edu.qhu.qhuoj.entity;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.persistence.*;

@Entity
@Table(name = "problem")
public class Problem {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getMemoryLimit() {
        return memoryLimit;
    }

    public void setMemoryLimit(int memoryLimit) {
        this.memoryLimit = memoryLimit;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    @Override
    public String toString() {
        return "Problem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", example='" + example + '\'' +
                ", note='" + note + '\'' +
                '}';
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_id")
    private int id;

    @Column(name = "problem_name")
    private String name;

    @Column(name = "problem_content")
    private String content;

    @Column(name = "problem_examle")
    private String example;

    @Column(name = "problem_note")
    private String note;

    @Column(name = "problem_timeLimit")
    private int timeLimit;

    @Column(name = "problem_memoryLimit")
    private int memoryLimit;

    @Column(name = "problem_total_score")
    private int totalScore;
}
