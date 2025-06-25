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

    // Test method for both implementations
    private static boolean testCase(int[] nums1, int[] nums2, double expected, String testName) {
        MedianOfTwoSortedArrays solution = new MedianOfTwoSortedArrays();
        
        double result1 = solution.findMedianSortedArrays(nums1.clone(), nums2.clone());
        double result2 = solution.findMedianSortedArrays1(nums1.clone(), nums2.clone());
        
        boolean pass1 = Math.abs(result1 - expected) < 1e-9;
        boolean pass2 = Math.abs(result2 - expected) < 1e-9;
        
        System.out.println(testName + " - Binary Search: " + (pass1 ? "PASS" : "FAIL") + 
                          " (" + result1 + "/" + expected + ")");
        System.out.println(testName + " - Merge Sort: " + (pass2 ? "PASS" : "FAIL") + 
                          " (" + result2 + "/" + expected + ")");
        
        if (!pass1) System.out.println("  Binary Search Expected: " + expected + ", Got: " + result1);
        if (!pass2) System.out.println("  Merge Sort Expected: " + expected + ", Got: " + result2);
        
        return pass1 && pass2;
    }

    public static void main(String[] args) {
        System.out.println("=== MedianOfTwoSortedArrays Test Cases ===");
        
        int passCount = 0;
        int totalTests = 15;
        
        // Test Case 1: Basic example [1,3] + [2] = 2.0
        if (testCase(new int[]{1, 3}, new int[]{2}, 2.0, "Test 1: Basic Example")) passCount++;
        
        // Test Case 2: Even length [1,2] + [3,4] = 2.5
        if (testCase(new int[]{1, 2}, new int[]{3, 4}, 2.5, "Test 2: Even Length")) passCount++;
        
        // Test Case 3: Empty first array [] + [1] = 1.0
        if (testCase(new int[]{}, new int[]{1}, 1.0, "Test 3: Empty First Array")) passCount++;
        
        // Test Case 4: Empty second array [2] + [] = 2.0
        if (testCase(new int[]{2}, new int[]{}, 2.0, "Test 4: Empty Second Array")) passCount++;
        
        // Test Case 5: Single elements [1] + [2] = 1.5
        if (testCase(new int[]{1}, new int[]{2}, 1.5, "Test 5: Single Elements")) passCount++;
        
        // Test Case 6: Same elements [1,1] + [1,1] = 1.0
        if (testCase(new int[]{1, 1}, new int[]{1, 1}, 1.0, "Test 6: Same Elements")) passCount++;
        
        // Test Case 7: Negative numbers [-1,0] + [1,2] = 0.5
        if (testCase(new int[]{-1, 0}, new int[]{1, 2}, 0.5, "Test 7: Negative Numbers")) passCount++;
        
        // Test Case 8: Large difference [1,2] + [1000000] = 2.0
        if (testCase(new int[]{1, 2}, new int[]{1000000}, 2.0, "Test 8: Large Difference")) passCount++;
        
        // Test Case 9: Different lengths [1,2,3,4,5] + [6] = 3.5
        if (testCase(new int[]{1, 2, 3, 4, 5}, new int[]{6}, 3.5, "Test 9: Different Lengths")) passCount++;
        
        // Test Case 10: Interleaved [1,3,5] + [2,4,6] = 3.5
        if (testCase(new int[]{1, 3, 5}, new int[]{2, 4, 6}, 3.5, "Test 10: Interleaved")) passCount++;
        
        // Test Case 11: First array larger [1,2,3,4,5,6] + [7,8] = 4.5
        if (testCase(new int[]{1, 2, 3, 4, 5, 6}, new int[]{7, 8}, 4.5, "Test 11: First Array Larger")) passCount++;
        
        // Test Case 12: All elements in first array [1,2,3] + [4,5,6] = 3.5
        if (testCase(new int[]{1, 2, 3}, new int[]{4, 5, 6}, 3.5, "Test 12: Sequential Arrays")) passCount++;
        
        // Test Case 13: Reverse order second array [4,5,6] + [1,2,3] = 3.5
        if (testCase(new int[]{4, 5, 6}, new int[]{1, 2, 3}, 3.5, "Test 13: Reverse Order")) passCount++;
        
        // Test Case 14: Zero values [0,0] + [0,0] = 0.0
        if (testCase(new int[]{0, 0}, new int[]{0, 0}, 0.0, "Test 14: Zero Values")) passCount++;
        
        // Test Case 15: Large single array [1] + [2,3,4,5,6,7,8] = 4.5
        if (testCase(new int[]{1}, new int[]{2, 3, 4, 5, 6, 7, 8}, 4.5, "Test 15: Large Single Array")) passCount++;
        
        System.out.println("\n=== Test Results ===");
        System.out.println("Passed: " + passCount + "/" + totalTests);
        System.out.println("Success Rate: " + (passCount * 100.0 / totalTests) + "%");
        
        if (passCount == totalTests) {
            System.out.println("🎉 All tests passed!");
        } else {
            System.out.println("❌ Some tests failed. Please review the implementation.");
        }
        
        // Performance comparison demo
        System.out.println("\n=== Performance Demo ===");
        int[] largeArray1 = new int[1000];
        int[] largeArray2 = new int[1000];
        for (int i = 0; i < 1000; i++) {
            largeArray1[i] = i * 2;
            largeArray2[i] = i * 2 + 1;
        }
        
        MedianOfTwoSortedArrays solution = new MedianOfTwoSortedArrays();
        
        long startTime = System.nanoTime();
        solution.findMedianSortedArrays(largeArray1.clone(), largeArray2.clone());
        long binarySearchTime = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        solution.findMedianSortedArrays1(largeArray1.clone(), largeArray2.clone());
        long mergeSortTime = System.nanoTime() - startTime;
        
        System.out.println("Binary Search O(log(m+n)): " + binarySearchTime + " ns");
        System.out.println("Merge Sort O((m+n)log(m+n)): " + mergeSortTime + " ns");
        System.out.println("Speed Ratio: " + (mergeSortTime / (double) binarySearchTime) + "x");
        
        // Demo output from original main method
        System.out.println("\n=== Original Demo ===");
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

