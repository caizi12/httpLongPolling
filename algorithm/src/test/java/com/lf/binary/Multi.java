package com.lf.binary;

public class Multi {
    //数组中求任意三个数最大乘积
    public int maximumProduct(int nums[]) {
        //最小，第二小
        int[] minNums = {Integer.MAX_VALUE, Integer.MAX_VALUE};
        //最大，第二大，第三大
        int[] maxNums = {Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
        for (int x : nums) {
            if (x < minNums[0]) {
                minNums[1] = minNums[0];
                minNums[0] = x;
            } else if (x < minNums[1]) {
                minNums[1] = x;
            }

            if (x > maxNums[0]) {
                maxNums[2] = maxNums[1];
                maxNums[1] = maxNums[0];
                maxNums[0] = x;
            } else if (x > maxNums[1]) {
                maxNums[2] = maxNums[1];
                maxNums[1] = x;
            } else if (x > maxNums[2]) {
                maxNums[2] = x;
            }

        }

        return Math.max(minNums[0] * minNums[1] * maxNums[0], maxNums[0] * maxNums[1] * maxNums[2]);
    }

    public static void main(String[] args) {
        Multi m = new Multi();
        int a[] = {1, 2, 3};
        long aa= (long)1000 * 1000 * 1000;
        System.out.println(aa);
        System.out.println(m.maximumProduct(a));
    }
}
