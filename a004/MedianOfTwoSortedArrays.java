package a004;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * 寻找两个正序数组的中位数
 * 给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的 中位数 。
 * <p>
 * 算法的时间复杂度应该为 O(log (m+n)) 。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums1 = [1,3], nums2 = [2]
 * 输出：2.00000
 * 解释：合并数组 = [1,2,3] ，中位数 2
 * 示例 2：
 * <p>
 * 输入：nums1 = [1,2], nums2 = [3,4]
 * 输出：2.50000
 * 解释：合并数组 = [1,2,3,4] ，中位数 (2 + 3) / 2 = 2.5
 * <p>
 * <p>
 * <p>
 * <p>
 * 提示：
 * <p>
 * nums1.length == m
 * nums2.length == n
 * 0 <= m <= 1000
 * 0 <= n <= 1000
 * 1 <= m + n <= 2000
 * -106 <= nums1[i], nums2[i] <= 106
 */
public class MedianOfTwoSortedArrays {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // 确保 nums1 是较短的数组
        if (nums1.length > nums2.length) {
            int[] temp = nums1;
            nums1 = nums2;
            nums2 = temp;
        }

        int m = nums1.length;
        int n = nums2.length;
        int imin = 0, imax = m, halfLen = (m + n + 1) / 2;

        while (imin <= imax) {
            int i = (imin + imax) / 2; // nums1 的切分点
            int j = halfLen - i; // nums2 的切分点

            if (i < m && nums2[j - 1] > nums1[i]) {
                // i 太小, 增加 i
                imin = i + 1;
            } else if (i > 0 && nums1[i - 1] > nums2[j]) {
                // i 太大, 减小 i
                imax = i - 1;
            } else {
                // i 是完美的切分点
                int maxOfLeft;
                if (i == 0) {
                    maxOfLeft = nums2[j - 1];
                } else if (j == 0) {
                    maxOfLeft = nums1[i - 1];
                } else {
                    maxOfLeft = Math.max(nums1[i - 1], nums2[j - 1]);
                }

                if ((m + n) % 2 == 0) {
                    int minOfRight;
                    if (i == m) {
                        minOfRight = nums2[j];
                    } else if (j == n) {
                        minOfRight = nums1[i];
                    } else {
                        minOfRight = Math.min(nums1[i], nums2[j]);
                    }
                    return (maxOfLeft + minOfRight) / 2.0;
                } else {
                    return maxOfLeft;
                }
            }
        }
        throw new IllegalArgumentException("Input arrays are not sorted.");
    }

    public double findMedianSortedArrays1(int[] nums1, int[] nums2) {
        ArrayList<Integer> list = new ArrayList<>();
        int m = nums1.length;
        int n = nums2.length;
        Arrays.stream(nums1).forEach(list::add);
        Arrays.stream(nums2).forEach(list::add);
        Collections.sort(list);
        int count = list.size();
        if (count % 2 != 0) {
            int index = (count / 2)  ;
            return list.get(index).doubleValue();
        } else {
            int half = (count / 2);
            return (list.get(half-1) + list.get(half)) / 2.0;
        }
    }

    public static void main(String[] args) {
        MedianOfTwoSortedArrays solution = new MedianOfTwoSortedArrays();
        int[] nums1 = {1, 3};
        int[] nums2 = {2};
        double median = solution.findMedianSortedArrays1(nums1, nums2);
        System.out.println("The median is: " + median); // 输出 2.0

        nums1 = new int[]{1, 2};
        nums2 = new int[]{3, 4};
         median = solution.findMedianSortedArrays1(nums1, nums2);
        System.out.println("The median is: " + median); // 输出 2.5
    }
}

