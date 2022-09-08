package com.lf.sum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 数组中找出三数相加等于0的所有结果
 */

public class TheeSumAdd {
    /**
     * 考虑到3个正整数是无法求和得到0的，所以，可以考虑先给数组排序，
     * 这样考察到以正整数开始的3个数时，就无需继续查找了。
     *
     * 从0到length-3, 依次取出nums[i]作为a，b在i+1的位置，c在length-1的位置。
     * 若a+b+c=0，则将a,b,c加入到结果集，并且b,c各自去重后，循环继续，直到b,c相遇；
     * 若a+b+c<0,则b向右移动1位，循环继续，直到b,c相遇；
     * 否则，c向左移动1位，循环继续，直到b,c相遇。
     *
     * 外层是对a的循环，外层是对b，c相遇前的循环，时间复杂度O(n*n)
     *
     * 作者：hqy_zsxx
     * 链接：https://leetcode.cn/problems/3sum/solution/15-san-shu-zhi-he-by-bryan-35-j164/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     *
     * @param nums
     * @return
     */
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length < 3) {
            return result;
        }
        Arrays.sort(nums);
        int len = nums.length;
        int left = 0, right = 0;
        for (int i = 0; i < len - 2; i++) {
            if (nums[i] > 0) { break; }
            if (i > 0 && nums[i] == nums[i - 1]) { continue; }
            left = i + 1;
            right = len - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == 0) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    left++;
                    right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return result;
    }

    public static List<List<Integer>> threeSum2(int nums[]) {
        if (nums == null || nums.length < 3) {
            return null;
        }
        List<List<Integer>> result = new ArrayList();
        int left = 0;
        int right = 0;
        //排序
        Arrays.sort(nums);
        //-3,-2,0,1,2
        for (int i = 0; i < nums.length -2; i++) {

            if(nums[i] > 0){
                break;
            }

            //有重复的元素直接移动
            if (i >0  && nums[i] == nums[i - 1]) {
                continue;
            }
            left = i + 1;
            right = nums.length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == 0) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    while (left < right && nums[left] == nums[left+1]){
                        left++;
                    }
                    while (left < right && nums[right] == nums[right-1]){
                        right--;
                    }
                    left++;
                    right--;
                } else if (sum > 0) {
                    right--;
                } else {
                    left++;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int sums[] = {-1,0,1,2,-1,-4};
        int sums2[] = {1, 2, 3, -1, 0, -2, -4, 5, 0, 0};
        System.out.println(threeSum(sums));
        System.out.println("--------");
        System.out.println(threeSum2(sums));
    }

}
