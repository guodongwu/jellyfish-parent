package com.jellyfish.entity;

import lombok.Data;

@Data
public class Log {
    private String logId;
    private String logName;
    private String event;
    private String createTime;

}
