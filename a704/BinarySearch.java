package a704;

import java.util.Arrays;

/**
 * 704. 二分查找
 * 给定一个 n 个元素有序的（升序）整型数组 nums 和一个目标值 target  ，写一个函数搜索 nums 中的 target，如果目标值存在返回下标，否则返回 -1。
 * <p>
 * <p>
 * 示例 1:
 * <p>
 * 输入: nums = [-1,0,3,5,9,12], target = 9
 * 输出: 4
 * 解释: 9 出现在 nums 中并且下标为 4
 * 示例 2:
 * <p>
 * 输入: nums = [-1,0,3,5,9,12], target = 2
 * 输出: -1
 * 解释: 2 不存在 nums 中因此返回 -1
 * <p>
 * <p>
 * 提示：
 * <p>
 * 你可以假设 nums 中的所有元素是不重复的。
 * n 将在 [1, 10000]之间。
 * nums 的每个元素都将在 [-9999, 9999]之间。
 */
public class BinarySearch {

    // Test method for all binary search implementations
    private static boolean testCase(int[] arr, int target, int expected, int expectedLeft, int expectedRight, String testName) {
        int result0 = binarySearch0(arr, target);
        int result1 = binarySearch1(arr, target);
        int resultLeft = mostLeft(arr, target);
        int resultRight = mostRight(arr, target);
        
        boolean pass0 = result0 == expected;
        boolean pass1 = result1 == expected;
        boolean passLeft = resultLeft == expectedLeft;
        boolean passRight = resultRight == expectedRight;
        
        System.out.println(testName + " - Standard0: " + (pass0 ? "PASS" : "FAIL") + 
                          " (" + result0 + "/" + expected + ")");
        System.out.println(testName + " - Standard1: " + (pass1 ? "PASS" : "FAIL") + 
                          " (" + result1 + "/" + expected + ")");
        System.out.println(testName + " - MostLeft: " + (passLeft ? "PASS" : "FAIL") + 
                          " (" + resultLeft + "/" + expectedLeft + ")");
        System.out.println(testName + " - MostRight: " + (passRight ? "PASS" : "FAIL") + 
                          " (" + resultRight + "/" + expectedRight + ")");
        
        if (!pass0 || !pass1 || !passLeft || !passRight) {
            System.out.println("  Array: " + Arrays.toString(arr) + ", Target: " + target);
        }
        
        return pass0 && pass1 && passLeft && passRight;
    }

    public static void main(String[] args) {
        System.out.println("=== BinarySearch Test Cases ===");
        
        int passCount = 0;
        int totalTests = 15;
        
        // Test Case 1: Basic example [-1,0,3,5,9,12] target=9 -> index 4
        if (testCase(new int[]{-1, 0, 3, 5, 9, 12}, 9, 4, 4, 4, "Test 1: Basic Found")) passCount++;
        
        // Test Case 2: Target not found [-1,0,3,5,9,12] target=2 -> -1
        if (testCase(new int[]{-1, 0, 3, 5, 9, 12}, 2, -1, -1, -1, "Test 2: Not Found")) passCount++;
        
        // Test Case 3: First element [-1,0,3,5,9,12] target=-1 -> index 0
        if (testCase(new int[]{-1, 0, 3, 5, 9, 12}, -1, 0, 0, 0, "Test 3: First Element")) passCount++;
        
        // Test Case 4: Last element [-1,0,3,5,9,12] target=12 -> index 5
        if (testCase(new int[]{-1, 0, 3, 5, 9, 12}, 12, 5, 5, 5, "Test 4: Last Element")) passCount++;
        
        // Test Case 5: Single element [5] target=5 -> index 0
        if (testCase(new int[]{5}, 5, 0, 0, 0, "Test 5: Single Element Found")) passCount++;
        
        // Test Case 6: Single element not found [5] target=3 -> -1
        if (testCase(new int[]{5}, 3, -1, -1, -1, "Test 6: Single Element Not Found")) passCount++;
        
        // Test Case 7: Duplicates with leftmost [0,1,1,2,2,3,4] target=2 -> any 2 index, left=3, right=4
        if (testCase(new int[]{0, 1, 1, 2, 2, 3, 4}, 2, 3, 3, 4, "Test 7: Duplicates")) passCount++;
        
        // Test Case 8: All same elements [2,2,2,2,2] target=2 -> any index, left=0, right=4
        if (testCase(new int[]{2, 2, 2, 2, 2}, 2, 0, 0, 4, "Test 8: All Same")) passCount++;
        
        // Test Case 9: Target smaller than all [1,2,3,4,5] target=0 -> -1
        if (testCase(new int[]{1, 2, 3, 4, 5}, 0, -1, -1, -1, "Test 9: Target Too Small")) passCount++;
        
        // Test Case 10: Target larger than all [1,2,3,4,5] target=6 -> -1
        if (testCase(new int[]{1, 2, 3, 4, 5}, 6, -1, -1, -1, "Test 10: Target Too Large")) passCount++;
        
        // Test Case 11: Two elements [1,3] target=1 -> index 0
        if (testCase(new int[]{1, 3}, 1, 0, 0, 0, "Test 11: Two Elements First")) passCount++;
        
        // Test Case 12: Two elements [1,3] target=3 -> index 1
        if (testCase(new int[]{1, 3}, 3, 1, 1, 1, "Test 12: Two Elements Second")) passCount++;
        
        // Test Case 13: Negative numbers [-5,-3,-1,0,2,4] target=-3 -> index 1
        if (testCase(new int[]{-5, -3, -1, 0, 2, 4}, -3, 1, 1, 1, "Test 13: Negative Numbers")) passCount++;
        
        // Test Case 14: Large array (even length) with duplicates [1,1,2,2,3,3,4,4] target=3 -> any 3 index, left=4, right=5
        if (testCase(new int[]{1, 1, 2, 2, 3, 3, 4, 4}, 3, 4, 4, 5, "Test 14: Even Length Duplicates")) passCount++;
        
        // Test Case 15: Boundary values [Integer.MIN_VALUE, 0, Integer.MAX_VALUE] target=0 -> index 1
        if (testCase(new int[]{Integer.MIN_VALUE, 0, Integer.MAX_VALUE}, 0, 1, 1, 1, "Test 15: Boundary Values")) passCount++;
        
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
        int[] largeArray = new int[100000];
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = i * 2; // Even numbers: 0, 2, 4, 6, ...
        }
        
        int target = 99998; // Target near the end
        
        long startTime = System.nanoTime();
        int result0 = binarySearch0(largeArray, target);
        long time0 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result1 = binarySearch1(largeArray, target);
        long time1 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int builtInResult = Arrays.binarySearch(largeArray, target);
        long builtInTime = System.nanoTime() - startTime;
        
        System.out.println("Large array search for " + target + ":");
        System.out.println("Custom binarySearch0: " + result0 + " (" + time0 + " ns)");
        System.out.println("Custom binarySearch1: " + result1 + " (" + time1 + " ns)");
        System.out.println("Built-in Arrays.binarySearch: " + builtInResult + " (" + builtInTime + " ns)");
        System.out.println("Time complexity: O(log n) for all implementations");
        
        // Algorithm analysis
        System.out.println("\n=== Algorithm Analysis ===");
        System.out.println("Standard Binary Search:");
        System.out.println("- Time: O(log n) - halves search space each iteration");
        System.out.println("- Space: O(1) - constant space usage");
        System.out.println("- Range: [left, right] with while(i <= j)");
        
        System.out.println("\nAlternative Binary Search:");
        System.out.println("- Time: O(log n) - same complexity");
        System.out.println("- Space: O(1) - constant space usage");
        System.out.println("- Range: [left, right) with while(i < j)");
        
        System.out.println("\nLeftmost/Rightmost Search:");
        System.out.println("- Time: O(log n) - continues searching even after finding target");
        System.out.println("- Space: O(1) - constant space usage");
        System.out.println("- Use case: Finding boundaries in sorted arrays with duplicates");
        
        // Original demo preserved
        System.out.println("\n=== Original Demo ===");
        int[] a = new int[]{-1, 0, 3, 5, 9, 12};
        target = 9;
        System.out.println("binarySearch0 vs Arrays.binarySearch: " + 
                          (binarySearch0(a, target) == Arrays.binarySearch(a, target)));
        
        System.out.println("mostLeft([0,1,1,2,2,3,4], 2) == 3: " + 
                          (mostLeft(new int[]{0, 1, 1, 2, 2, 3, 4}, 2) == 3));
        System.out.println("mostRight([0,1,1,2,2,3,4], 2) == 4: " + 
                          (mostRight(new int[]{0, 1, 1, 2, 2, 3, 4}, 2) == 4));
        
        String ip = "10.0.0.1 10.0.0.2 10.0.0.3";
        String trim = ip.replaceAll("10.0.0.2", "").trim();
        System.out.println("IP manipulation demo: " + trim);
        System.out.println("Split result: " + Arrays.toString(trim.split(" +")));
    }

    private static int binarySearch0(int[] a, int target) {
        int i = 0, j = a.length - 1;
        while (i <= j) {
            int m = (i + j) >>> 1;//防止越界
            if (target > a[m]) {//在右半区
                i = m + 1;
            } else if (target < a[m]) { //在左半区
                j = m - 1;
            } else {
                return m;
            }
        }
        return -1;
    }

    private static int binarySearch1(int[] a, int target) {
        int i = 0, j = a.length;
        while (i < j) {
            int m = (i + j) >>> 1;
            if (target > a[m]) {//在右半区
                i = m + 1;
            } else if (target < a[m]) { //在左半区
                j = m;
            } else {
                return m;
            }
        }
        return -1;
    }

    // 总是返回最左边的相等得索引   [0,1,1,2,2,3,4]  2 => 3
    private static int mostLeft(int[] a, int target) {
        int i = 0, j = a.length - 1;
        int temp = -1; // 用于记录目标值的索引

        while (i <= j) {
            int m = (i + j) >>> 1; // 计算中间索引
            if (target > a[m]) { // 如果目标值大于中间元素，搜索右半区
                i = m + 1;
            } else if (target < a[m]) { // 如果目标值小于或等于中间元素，搜索左半区
                j = m - 1;
            } else {
                temp = m; // 更新 temp 以指向可能的最左侧索引
                j = m - 1;//继续向左查找
            }
        }
        return temp; // 返回最左边的相等得索引，如果没有找到则返回 -1
    }

    // 总是返回最右边的相等得索引   [0,1,1,2,2,3,4]  2 => 4
    private static int mostRight(int[] a, int target) {
        int i = 0, j = a.length - 1;
        int temp = -1; // 用于记录目标值的索引

        while (i <= j) {
            int m = (i + j) >>> 1; // 计算中间索引
            if (target < a[m]) { // 如果目标值小于中间元素，搜索左半区
                j = m - 1;
            } else if (target > a[m]) { // 如果目标值大于于或等于中间元素，搜索右半区
                i = m + 1;
            } else {
                temp = m; // 更新 temp 以指向可能的最左侧索引
                i = m + 1;//继续向右查找

            }
        }
        return temp; // 返回最右边的相等得索引，如果没有找到则返回 -1
    }

}
