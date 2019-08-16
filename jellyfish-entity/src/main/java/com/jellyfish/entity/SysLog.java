package com.jellyfish.entity;

import lombok.Data;

@Data
public class SysLog {
    private String logId;
    private String logName;
    private String event;
    private String createTime;
    private String methodName;
    private String methodPath;
    private String operater;
    private String param;
    private String result;
    private String code;
}
