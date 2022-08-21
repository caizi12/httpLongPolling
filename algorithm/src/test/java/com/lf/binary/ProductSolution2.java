package com.lf.binary;

public class ProductSolution2 {
    //求数组中最大子积
    public int maximumProduct(int[] nums) {
        int maxValue = 0;
        int tmpMax = 1;
        int tmpMin = 1;

        for (int i =0; i<nums.length;i++) {
            //如果当前值小于0
            if(nums[i] < 0){
                int tmp = tmpMax;
                tmpMax = tmpMin;
                tmpMin = tmp;
            }

            //与前乘积结果取最大
            tmpMax = Math.max(tmpMax * nums[i],nums[i]);
            //与前乘积结果取最小
            tmpMin = Math.min(tmpMin * nums[i],nums[i]);

            maxValue = Math.max(tmpMax,maxValue);
        }

       return maxValue;
    }

    public static void main(String[] args) {
        ProductSolution2 m = new ProductSolution2();
        int a[] = {1,-5,-4, 2, 3,-2,0};
        System.out.println(m.maximumProduct(a));
    }
}
