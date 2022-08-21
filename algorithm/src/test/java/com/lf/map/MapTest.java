package com.lf.map;

import java.util.HashMap;
import java.util.Map;

public class MapTest {
    private int no;

    @Override
    public int hashCode() {
        return 1;
    }

    public MapTest(int no) {
        this.no = no;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        return this.no == ((MapTest)obj).no;
    }

    public static void main(String[] args) {
        System.out.println(new Integer(1) == new Integer(2));
        Map map = new HashMap<>();
        int i = 0;
        while (i++ < 10){
            MapTest mapTest = new MapTest(i);
            map.put(mapTest,mapTest);
        }
        System.out.println(map.size());
    }
}
