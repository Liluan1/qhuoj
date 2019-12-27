package edu.qhu.qhuoj.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ResponseMsg {
    private String time;
    private String status;
    private Object message;
    private String path;

    public static ResponseMsg error(Object message, String path){
        LocalDateTime rightNow = LocalDateTime.now();
        String time = rightNow.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // String time = dateFormat.format(new Date());
        return new ResponseMsg(time, "error", message, path);
    }

    public static ResponseMsg success(Object message, String path){
        LocalDateTime rightNow = LocalDateTime.now();
        String time = rightNow.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return new ResponseMsg(time, "success", message, path);
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public ResponseMsg(String time, String status, Object message, String path) {
        this.time = time;
        this.status = status;
        this.message = message;
        this.path = path;
    }
}
