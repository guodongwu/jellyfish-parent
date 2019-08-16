package com.jellyfish.core.queue.listener;

import com.jellyfish.core.queue.SysLogQueue;
import com.jellyfish.core.queue.service.IQueue;
import com.jellyfish.entity.SysLog;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class SysLogQueueListener implements Runnable {
    private List<SysLog> queueDataList;
    private IQueue<SysLog> process;

    public SysLogQueueListener(IQueue<SysLog> process){
        this.process=process;
        queueDataList=new ArrayList<SysLog>(SysLogQueue.DEFAULT_HANDLE_SIZE);
    }
    @Override
    public void run() {
        SysLog sysLog=null;
        System.out.println("开始了哈");
        long startTime=System.currentTimeMillis();
        while (true){
            try {
                SysLogQueue queue = SysLogQueue.getInstance();
                sysLog = queue.poll();
                if (null != sysLog) {
                    queueDataList.add(sysLog);
                }
                if (queueDataList.size() >= SysLogQueue.DEFAULT_HANDLE_SIZE) {
                    startTime = batchProcess(queueDataList);
                    continue;

                }
                long currentTime = System.currentTimeMillis();
                System.out.println(currentTime - startTime-SysLogQueue.DEFAULT_HANDLE_TIME);
                if (currentTime - startTime > SysLogQueue.DEFAULT_HANDLE_TIME) {
                    startTime = batchProcess(queueDataList);
                    continue;
                }
                System.out.println(String.format("未满足队列批量处理条件继续等待,defaultTime：%s,handleTime：%s,size：%s,queueSize：%s",
                        SysLogQueue.DEFAULT_HANDLE_TIME, currentTime - startTime, queueDataList.size(), queue.size()));
            }catch (Exception ex){
                ex.printStackTrace();
                continue;
            }
        }
    }

    private long batchProcess(List<SysLog> queueDataList) {
      try{
          process.processData(queueDataList);
      }catch (Exception ex){

      }finally {
          this.clearQueueDataList();
      }
      return System.currentTimeMillis();
    }

    private void clearQueueDataList() {
        queueDataList=null;
        queueDataList=new ArrayList<SysLog>();
    }
}
