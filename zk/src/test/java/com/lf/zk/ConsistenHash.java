package com.lf.zk;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * һ���� Hash �㷨
 */
public class ConsistenHash {

    private static String[] serverIps= new String[]{"127.0.1","127.0","127.9.3.1","127"};

    private  static SortedMap<Integer,String> sortedMap = new TreeMap();


    static {
        for (String serverIp:serverIps){
            int hash = getHashForLength(serverIp);
            System.out.println(serverIp+" hash ֵΪ:"+hash);
            sortedMap.put(hash,serverIp);
        }
    }

    public static void main(String[] args) {
        String[] keys = new String[]{"1","̫��12","����4545","124343433","21222211231231"};
        for (String key:keys){
            System.out.println("key:"+key+" ���ڵ� IP Ϊ��"+getServer(key));
        }
    }

    private static String getServer(String key){
        int hash = getHashForLength(key);
        SortedMap<Integer,String> tailMap = sortedMap.tailMap(hash);
        String serverIp = null;
        if(tailMap.isEmpty()){
            serverIp =sortedMap.get(sortedMap.firstKey());
        }else {
           Integer firstKey = tailMap.firstKey();
           serverIp = tailMap.get(firstKey);
        }
        return serverIp;
    }

    private static int getHashForLength(String str) {
        return str.length();
    }

    private static int getHash(String str) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < str.length(); i++)
            hash = (hash ^ str.charAt(i)) * p;
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;

        // ����������ֵΪ������ȡ�����ֵ
        if (hash < 0)
            hash = Math.abs(hash);
        return hash;
    }
}
