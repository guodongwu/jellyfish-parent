package com.jellyfish.task;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.jellyfish.entity.Log;
import org.apache.commons.lang3.RandomUtils;
import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Component
public class LogTask implements Runnable {

    private static LinkedBlockingDeque<Log> loglists=new LinkedBlockingDeque<>();
    private static ExecutorService executor;
    private static  ThreadFactory threadFactory;

    static {
        threadFactory=new ThreadFactoryBuilder().setNameFormat("log-pool-%d").build();
        executor=new ScheduledThreadPoolExecutor(1);
        executor.execute(new LogTask());
    }
    public  static void addLog(Log log){
        loglists.add(log);
    }
    @Override
    public void run() {
        List<Log> items=new ArrayList<>();
        while (true){
            Log item=loglists.peek();
            if(item==null && items.size()>0){
                insertLog(items);
            }
            Log log=deleteItem();
            items.add(log);
        }
    }

    private void insertLog(List<Log> items) {
        System.out.println(items.toString());
        try {
            File tofile = new File("d:\\log20190731.log");
            FileWriter fw = new FileWriter(tofile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            for (Log log:items){
                pw.println(log.toString());
            }
            pw.close();
            bw.close();
            fw.close();
        } catch (IOException e) {
        }
        items.clear();
    }

    private Log deleteItem() {
        Log log=null;
        try {
            log=loglists.takeFirst();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  log;
    }

    // @Scheduled(fixedDelay = 2000)
    public void addLog1() throws InterruptedException {
        Log log=new Log();
        log.setLogId(String.valueOf(RandomUtils.nextInt()));
        log.setCreateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        log.setLogName("biubiu:"+log.getLogId());
        log.setEvent("event:"+log.getLogName());
        System.out.println("=======填装弹药1========");
        LogTask.addLog(log);
        Thread.sleep(5000);
    }
    /*public static void main(String[] args) {
        new Thread(new LogTask()).start();
    }*/
    // @Scheduled(fixedDelay = 2000)
    public void addLog2() throws InterruptedException {
        Log log=new Log();
        log.setLogId(String.valueOf(RandomUtils.nextInt()));
        log.setCreateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        log.setLogName("biubiu:"+log.getLogId());
        log.setEvent("event:"+log.getLogName());
        System.out.println("=======填装弹药2========");
        LogTask.addLog(log);
    }
    /*public static void main(String[] args) {
        new Thread(new LogTask()).start();
    }*/
}
