package edu.qhu.qhuoj.entity;

import javax.persistence.*;

@Entity
@Table(name = "language")
public class Language {
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

    public String getCompileCommand() {
        return compileCommand;
    }

    public void setCompileCommand(String compileCommand) {
        this.compileCommand = compileCommand;
    }

    public String getRunningCommand() {
        return runningCommand;
    }

    public void setRunningCommand(String runningCommand) {
        this.runningCommand = runningCommand;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public String toString() {
        return "Language{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", suffix='" + suffix + '\'' +
                ", compileCommand='" + compileCommand + '\'' +
                ", runningCommand='" + runningCommand + '\'' +
                '}';
    }

    @Id
    @Column(name = "language_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "language_name")
    private String name;
    @Column(name = "language_suffix")
    private String suffix;
    @Column(name = "language_compile_command")
    private String compileCommand;
    @Column(name = "language_running_command")
    private String runningCommand;
}
