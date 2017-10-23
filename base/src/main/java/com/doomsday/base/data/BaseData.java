package com.doomsday.base.data;

/**
 * Created by Administrator on 2016/12/20.
 */

public class BaseData {

    /**
     * status : failed
     * code : 1001
     * errmsg : 非法Token
     */

    protected String status;
    protected int code;
    protected String errmsg;
    protected int length;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    protected String msg;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
