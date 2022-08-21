package lf;

import java.util.HashMap;
import java.util.Map;

public class MapTest {
    private int no;

    @Override
    public int hashCode() {
        if (no < 8) {
            return 100;
        }
        return no % 2;
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

    /**
     * map结论
     *
     * 当数组扩容到64，某个数组内元素达到8，该节点则会转换为红黑树，红黑树查找复杂度为logn
     *  转红黑树只是达到数量为8的
     * @param args
     */

    public static void main(String[] args) {
        Map map = new HashMap<>();
        int i = 0;
        while (i++ < 100) {
            MapTest mapTest = new MapTest(i);
            map.put(mapTest, mapTest);
        }
        System.out.println(map.size());
    }
}
