package com.start.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.start.error.ErrorEnum;
import com.start.error.StartException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class ReturnData implements Serializable {
    protected final static Logger logger = LoggerFactory.getLogger(ReturnData.class);

    private static final long serialVersionUID = 1L;

    public static final int CODE_SUCCESS = 1;  //沿袭十匠api的惯例，定义成功的代码为1

    public static final int CODE_FAILED = -1;

    private Object data;
    private int code;
    private String msg;

    public ReturnData() {}

    private ReturnData(int code, Object data, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ReturnData(ErrorEnum errorEnum) {
        code = errorEnum.code;
        msg = errorEnum.msg;
    }

    public ReturnData(StartException e) {
        code = e.getCode();
        data = null;
        msg = e.getMessage();
    }

    public ReturnData(StartException e, Object data) {
        code = e.getCode();
        this.data = data;
        msg = e.getMessage();
    }

    public static final ReturnData success() {
        return success(null);
    }

    public static final ReturnData success(Object data) {
        return new ReturnData(CODE_SUCCESS, data, "Success.");
    }

    public static final ReturnData failed(String msg) {
        return failed(null, msg);
    }

    public static final ReturnData failed(Object data, String msg) {
        return new ReturnData(CODE_FAILED, data, msg);
    }

    public static final ReturnData failed(int code,Object data, String msg) {
        return new ReturnData(code, data, msg);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return code == CODE_SUCCESS;
    }
}
