//package com.lf.zk;
//
//import java.io.IOException;
//
//import org.apache.zookeeper.CreateMode;
//import org.apache.zookeeper.KeeperException;
//import org.apache.zookeeper.WatchedEvent;
//import org.apache.zookeeper.Watcher;
//import org.apache.zookeeper.ZooDefs.Ids;
//import org.apache.zookeeper.ZooKeeper;
//import org.apache.zookeeper.data.Stat;
//
//public class ZkClientTest {
//    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
//        ZooKeeper zk = new ZooKeeper("127.0.0.1:4181", 5000, new Watcher() {
//            public void process(WatchedEvent watchedEvent) {
//                System.out.println(watchedEvent.toString());
//            }
//        });
//
//        System.out.println("-----"+new String(zk.getData("/test",true,new Stat())));
//        zk.create("/h2","myFirst1".getBytes(), Ids.READ_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
//        System.out.println("-----path-/lll:"+new String(zk.getData("/h2",true,new Stat())));
//    }
//}
