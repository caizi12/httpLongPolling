package com.lf.zk.ratio;

import java.math.BigDecimal;
import java.util.*;

/**
 * 计算乘积占比
 */
public class ProductRatio {

    public static void main(String[] args) {

        Map<Integer, Integer> paramData = new HashMap<Integer, Integer>();
        for (int i = 0; i < 3; i++) {
            paramData.put(i, new Random().nextInt(100) +  new Random().nextInt(100));
        }

        System.out.println(calcuate(950000, paramData));
    }

    public static long calcuate(int topNum, Map<Integer, Integer> paramData) {


        long descartesNum = 1;
        for (Map.Entry<Integer, Integer> entry : paramData.entrySet()) {
            descartesNum *= entry.getValue();
        }

        if(descartesNum<=topNum){
            return descartesNum;
        }

        //计算待要减少的倍数
        double reduceMultiple =descartesNum / topNum;
        //计算倍数因子，计算倍数的N次方根
         double multipleDivisor = StrictMath.pow(reduceMultiple, 1.0 / paramData.size());
        double tagTypeMaxSize = 0;
        BigDecimal num1 = null;
        BigDecimal num2 = null;

        long newDescartesNum = 1;
        for (Map.Entry<Integer, Integer> entry : paramData.entrySet()) {
            num1 = new BigDecimal(entry.getValue());
            num2 = new BigDecimal(multipleDivisor);

            tagTypeMaxSize = num1.divide(num2, BigDecimal.ROUND_DOWN).intValue();
            newDescartesNum *= tagTypeMaxSize;
        }

        return newDescartesNum;
    }
}
