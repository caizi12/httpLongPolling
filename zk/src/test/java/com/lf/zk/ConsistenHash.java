package com.lf.zk;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 一致性 Hash 算法
 */
public class ConsistenHash {

    private static String[] serverIps= new String[]{"127.0.1","127.0","127.9.3.1","127"};

    private  static SortedMap<Integer,String> sortedMap = new TreeMap();


    static {
        for (String serverIp:serverIps){
            int hash = getHashForLength(serverIp);
            System.out.println(serverIp+" hash 值为:"+hash);
            sortedMap.put(hash,serverIp);
        }
    }

    public static void main(String[] args) {
        String[] keys = new String[]{"1","太阳12","月亮4545","124343433","21222211231231"};
        for (String key:keys){
            System.out.println("key:"+key+" 所在的 IP 为："+getServer(key));
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

        // 如果算出来的值为负数则取其绝对值
        if (hash < 0)
            hash = Math.abs(hash);
        return hash;
    }
}
