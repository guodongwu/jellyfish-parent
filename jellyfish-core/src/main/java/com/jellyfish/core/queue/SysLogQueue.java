package com.jellyfish.core.queue;

import com.jellyfish.entity.SysLog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Component
public class SysLogQueue {
    public static  int QUEUE_MAX_SIZE;
    public static  int DEFAULT_HANDLE_SIZE;
    public  static  int DEFAULT_HANDLE_TIME;
    @Value("${system.log.queue.maxsize}")
    private void setQueueMaxSize(int maxsize) {
        QUEUE_MAX_SIZE = maxsize;
        blockingQueue = new ArrayBlockingQueue<SysLog>(QUEUE_MAX_SIZE);
    }

    @Value("${system.log.queue.default.handle.size}")
    private void setDefaultHandleSize(int handleSize) {
        DEFAULT_HANDLE_SIZE = handleSize;
    }

    @Value("${system.log.queue.default.handle.time}")
    private void setDefaultHandleTime(int handleTime) {
        DEFAULT_HANDLE_TIME = handleTime;
    }
    private static BlockingQueue<SysLog> blockingQueue;
    private static final SysLogQueue instance = new SysLogQueue();
    private SysLogQueue(){}
    public  static SysLogQueue getInstance(){
        return instance;
    }
    public boolean push(SysLog sysLog){
        return SysLogQueue.blockingQueue.add(sysLog);
    }

    public SysLog poll(){

        SysLog result=null;
        try{
            result=SysLogQueue.blockingQueue.take();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return  result;
    }
    public int size(){
        return  SysLogQueue.blockingQueue.size();
    }
}
