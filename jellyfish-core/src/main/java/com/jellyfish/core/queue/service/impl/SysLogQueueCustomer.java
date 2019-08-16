package com.jellyfish.core.queue.service.impl;

import com.jellyfish.core.queue.service.IQueue;
import com.jellyfish.entity.SysLog;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysLogQueueCustomer<T> implements IQueue<SysLog> {
    @Override
    public void processData(List<SysLog> list) {
        if(null!=list && list.size()>0){
            System.out.println("入库:"+list.size());
        }
    }
}
