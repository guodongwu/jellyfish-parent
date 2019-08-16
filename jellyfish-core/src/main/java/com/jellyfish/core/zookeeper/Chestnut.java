package com.jellyfish.core.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

public class Chestnut implements Watcher {
    private static CountDownLatch countDownLatch=new CountDownLatch(1);
    private static ZooKeeper zk;
    private static Stat stat=new Stat();
    public static void main(String[] args) throws IOException, InterruptedException {
        String host="192.168.83.128:2181";
        List<String> children=new ArrayList<>();
        zk=new ZooKeeper(host,2000,new Chestnut());
        if(ZooKeeper.States.CONNECTING==zk.getState()){
            countDownLatch.await();

        }

        if(zk!=null){
            String zpath="/";
            try {
                System.out.println("sleep:"+new String(zk.getData("/FirstZnode",true,stat)));
                zk.setData("/FirstZnode","222".getBytes(),stat.getVersion());
                Thread.sleep(1000*10);
                children=zk.getChildren(zpath,false);
                for (String child:children){
                    System.out.println(child);
                }
                stat=zk.exists("/FirstZnode",false);
                if(stat==null)
                    zk.create("/FirstZnode","first".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                stat=new Stat();
                zk.close();
            }

        }


    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if(Event.KeeperState.SyncConnected==watchedEvent.getState()){
            if(Event.EventType.None==watchedEvent.getType() && null==watchedEvent.getPath()){
                countDownLatch.countDown();
            }else if(watchedEvent.getType()== Event.EventType.NodeDataChanged){
                try {
                    System.out.println("process:"+new String(zk.getData(watchedEvent.getPath(),true,stat)));
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
