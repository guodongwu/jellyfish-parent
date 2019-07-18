package com.jellyfish.enums;

public enum  ResultCode {
    SUCCESS("0000", "成功"),
    ERROR("9999", "失败"),
    WAIT("1111", "正在处理结果");
    private String code;
    private String msg;
    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
