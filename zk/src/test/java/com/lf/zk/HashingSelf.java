package com.lf.zk;

import java.util.SortedMap;
import java.util.TreeMap;

public class HashingSelf {
    public static String[] SERVER_IPS = {"127.0.1.3","128.2.1.9","129.2.1.9"};

    public static SortedMap<Integer,String> nodeMap = new TreeMap();

    public static int VIRTUAL_NUM = 10;

    static {
        for (String ip:SERVER_IPS){
            for(int i =0;i<VIRTUAL_NUM;i++){
                String vir_ip = ip+"#v"+i;
                int hashCode = getHash(vir_ip);
                nodeMap.put(hashCode,vir_ip);
                System.out.println("虚拟节点："+vir_ip+", hash:"+hashCode);
            }
        }
    }

    public static void main(String[] args) {
        getServerIp("1f6");
        getServerIp("783tes12313t");

        getServerIp("99023423sdfa4");
    }

    public static String getServerIp(String dataKey){
        int hashCode = getHash(dataKey);
        SortedMap<Integer,String> sortedMap = nodeMap.tailMap(hashCode);
        Integer nodeKey = null;
        if(sortedMap == null || sortedMap.isEmpty()){
            nodeKey = nodeMap.firstKey();
        }else {
            nodeKey= sortedMap.firstKey();
        }
        String vir_ip = nodeMap.get(nodeKey);
        String  realIp= vir_ip.substring(0,vir_ip.indexOf("#v"));
        System.out.println("结果>key："+dataKey+",v_ip:"+vir_ip+",real_ip:"+realIp);
        return realIp;
    }

    public static int getHash(String str){
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < str.length(); i++)
            hash = (hash ^ str.charAt(i)) * p;
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        // 如果算出来的值为负数则取其绝对值
        if (hash < 0)
            hash = Math.abs(hash);
        return hash;
    }
}
