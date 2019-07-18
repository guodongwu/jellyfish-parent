package com.jellyfish.common;


import com.jellyfish.enums.ResultCode;

import java.io.Serializable;

public class JsonResult implements Serializable {

    private String status;
    private Object data;
    private String message;
    public JsonResult() {

    }
    public JsonResult(String status, Object data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
    public JsonResult(ResultCode resultCode){
        status = ResultCode.SUCCESS.getCode();
        message=ResultCode.SUCCESS.getMsg();
    }
    public JsonResult(ResultCode resultCode,Object data){
        status = ResultCode.SUCCESS.getCode();
        this.data=data;
        message=ResultCode.SUCCESS.getMsg();
    }
    public JsonResult(Throwable e){
        status = ResultCode.ERROR.getCode();
        data=null;
        message=e.getMessage();
    }

    public JsonResult(Object data){
        status = ResultCode.SUCCESS.getCode();
        this.data=data;
        message=ResultCode.SUCCESS.getMsg();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}