package com.lf.binary;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestSum {

    // 三数之和
    // 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有和为 0 且不重复的三元组。

    // 注意：答案中不可以包含重复的三元组。

    // 示例 1：

    // 输入：nums = [-1,0,1,2,-1,-4]
    // 输出：[[-1,-1,2],[-1,0,1]]
    // 示例 2：

    // 输入：nums = [0,1,1]
    // 输出：[]
    // 示例 3：

    // 输入：nums = [0,0,0]
    // 输出：[[0,0,0]]

    // 提示：

    // 3 <= nums.length <= 3000
    // -105 <= nums[i] <= 105

    public List<List<Integer>> threeSum(int[] nums) {

        List<List<Integer>> result = new ArrayList();

        for (int i = 0; i < nums.length; i++) {
            for (int j = 1; j < nums.length; j++) {
                for (int k = 2; k < nums.length; k++) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        List<Integer> tmpResult = new ArrayList();
                        tmpResult.add(nums[i]);
                        tmpResult.add(nums[j]);
                        tmpResult.add(nums[k]);
                        result.add(tmpResult);
                    }
                }
            }
        }
        return distinctResult(result);
    }

    public List<List<Integer>> distinctResult(List<List<Integer>> result) {
        Set<Integer> set = new HashSet();
        List<List<Integer>> newResult = new ArrayList();
        for (List<Integer> nums : result) {
            int charSum = 0;
            for (List<Integer> num : result) {
                charSum += ("" + num).charAt(0);
            }

            Integer tmpSum = Integer.valueOf(charSum);
            if (!set.contains(tmpSum)) {
                newResult.add(nums);
                set.add(tmpSum);
            }
        }
        return newResult;
    }

    public static void main(String[] args) {
        System.out.println("Hello LeetCoder");
        TestSum main = new TestSum();
        int nums[] = {-1, 0, 1, 2, -1, -4,-2,-5,1,4};
        System.out.println(main.threeSum(nums));

        System.out.println("-2".charAt(0));
    }
}

