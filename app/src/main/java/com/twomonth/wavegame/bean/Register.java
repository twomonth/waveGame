package com.twomonth.wavegame.bean;

public class Register {

    /**
     * code : 200
     * message : 登录完成
     * user_id : 640
     */

    private int code;
    private String message;
    private int user_id;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Register{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", user_id=" + user_id +
                '}';
    }
}
