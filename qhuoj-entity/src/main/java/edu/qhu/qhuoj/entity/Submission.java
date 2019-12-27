package edu.qhu.qhuoj.entity;

import org.hibernate.annotations.Target;

import javax.persistence.*;

@Entity
@Table(name = "submission")
public class Submission {
    public Submission() {
    }

    public Submission(int id, int usedTime, int usedMemory, String judgeResult, int judgeScore, String judgeLog) {
        this.id = id;
        this.usedTime = usedTime;
        this.usedMemory = usedMemory;
        this.judgeResult = judgeResult;
        this.judgeScore = judgeScore;
        this.judgeLog = judgeLog;
    }

    public Submission(Problem problem, User user, Language language, String code) {
        this.problem = problem;
        this.user = user;
        this.language = language;
        this.code = code;
    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public int getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(int usedTime) {
        this.usedTime = usedTime;
    }

    public int getUsedMemory() {
        return usedMemory;
    }

    public void setUsedMemory(int usedMemory) {
        this.usedMemory = usedMemory;
    }

    public String getJudgeResult() {
        return judgeResult;
    }

    public void setJudgeResult(String judgeResult) {
        this.judgeResult = judgeResult;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getJudgeScore() {
        return judgeScore;
    }

    public void setJudgeScore(int judgeScore) {
        this.judgeScore = judgeScore;
    }

    public String getJudgeLog() {
        return judgeLog;
    }

    public void setJudgeLog(String judgeLog) {
        this.judgeLog = judgeLog;
    }

    @Override
    public String toString() {
        return "Submission{" +
                "id=" + id +
                ", problem=" + problem +
                ", user=" + user +
                ", language=" + language +
                ", usedTime=" + usedTime +
                ", usedMemory=" + usedMemory +
                ", judgeResult='" + judgeResult + '\'' +
                ", code='" + code + '\'' +
                ", judgeScore=" + judgeScore +
                ", judgeLog='" + judgeLog + '\'' +
                '}';
    }

    @Id
    @Column(name = "submission_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "problem_id")
    private Problem problem;
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @Target(value = User.class)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "language_id")
    private Language language;
    @Column(name = "submission_used_time")
    private int usedTime;
    @Column(name = "submission_used_memory")
    private int usedMemory;
    @Column(name = "submission_judge_result")
    private String judgeResult;
    @Column(name = "submission_code")
    private String code;
    @Column(name = "submission_judge_score")
    private int judgeScore;
    @Column(name = "submission_judge_log")
    private String judgeLog;
}
