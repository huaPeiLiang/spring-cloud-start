package com.start.error;

public enum ErrorEnum {

    ;

    /**
     * code
     */
    public final Integer code;

    /**
     * message
     */
    public final String msg;

    private ErrorEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static Integer msgOf(String msg){
        for (ErrorEnum e : ErrorEnum.values()) {
            if (e.msg.equalsIgnoreCase(msg)) {
                return e.code;
            }
        }
        return -1;
    }
}
