package com.start.error;


import com.start.pojo.ReturnData;

public class StartException extends Exception {
    private int code;

    public StartException(String message) {
        super(message);
        code = ReturnData.CODE_FAILED;
    }

    public StartException(ErrorEnum errorEnum) {
        super(errorEnum.msg);
        code = errorEnum.code;
    }

    public StartException(int code, String message){
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
