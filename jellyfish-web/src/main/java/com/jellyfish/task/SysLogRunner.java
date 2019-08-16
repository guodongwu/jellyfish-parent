package com.jellyfish.task;

import com.jellyfish.core.queue.listener.SysLogQueueListener;
import com.jellyfish.core.queue.service.impl.SysLogQueueCustomer;
import com.jellyfish.entity.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SysLogRunner implements CommandLineRunner {
    @Autowired
    private SysLogQueueCustomer<SysLog> sysLogQueueCustomer;
    @Override
    public void run(String... args) throws Exception {
        SysLogQueueListener listener=new SysLogQueueListener(sysLogQueueCustomer);
        new Thread(listener).start();
    }
}
