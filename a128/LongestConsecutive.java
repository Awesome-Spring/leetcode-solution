package a128;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * 最长连续序列
 * 给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
 *
 * 请你设计并实现时间复杂度为 O(n) 的算法解决此问题。
 *
 *
 *
 * 示例 1：
 *
 * 输入：nums = [100,4,200,1,3,2]
 * 输出：4
 * 解释：最长数字连续序列是 [1, 2, 3, 4]。它的长度为 4。
 * 示例 2：
 *
 * 输入：nums = [0,3,7,2,5,8,4,6,0,1]
 * 输出：9
 *
 *
 * 提示：
 *
 * 0 <= nums.length <= 105
 * -109 <= nums[i] <= 109
 */
public class LongestConsecutive {

    public int longestConsecutive(int[] nums) {
        if (nums.length == 0) return 0;
        
        // 使用HashSet存储所有数字，去重并支持O(1)查找
        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            numSet.add(num);
        }
        
        int maxLength = 0;
        
        // 遍历每个数字
        for (int num : numSet) {
            // 只有当当前数字是连续序列的起始数字时才开始计算
            // 即该数字的前一个数字不在集合中
            if (!numSet.contains(num - 1)) {
                int currentNum = num;
                int currentLength = 1;
                
                // 连续查找下一个数字
                while (numSet.contains(currentNum + 1)) {
                    currentNum++;
                    currentLength++;
                }
                
                // 更新最大长度
                maxLength = Math.max(maxLength, currentLength);
            }
        }
        
        return maxLength;
    }
    
    // Alternative implementation using Union-Find (并查集)
    public int longestConsecutiveUnionFind(int[] nums) {
        if (nums.length == 0) return 0;
        
        java.util.Map<Integer, Integer> parent = new java.util.HashMap<>();
        java.util.Map<Integer, Integer> size = new java.util.HashMap<>();
        
        // Initialize each number as its own parent
        for (int num : nums) {
            if (!parent.containsKey(num)) {
                parent.put(num, num);
                size.put(num, 1);
            }
        }
        
        // Union consecutive numbers
        for (int num : nums) {
            if (parent.containsKey(num + 1)) {
                union(parent, size, num, num + 1);
            }
        }
        
        // Find maximum size
        int maxSize = 0;
        for (int s : size.values()) {
            maxSize = Math.max(maxSize, s);
        }
        
        return maxSize;
    }
    
    private int find(java.util.Map<Integer, Integer> parent, int x) {
        if (parent.get(x) != x) {
            parent.put(x, find(parent, parent.get(x))); // Path compression
        }
        return parent.get(x);
    }
    
    private void union(java.util.Map<Integer, Integer> parent, java.util.Map<Integer, Integer> size, int x, int y) {
        int rootX = find(parent, x);
        int rootY = find(parent, y);
        
        if (rootX != rootY) {
            // Union by size
            if (size.get(rootX) < size.get(rootY)) {
                parent.put(rootX, rootY);
                size.put(rootY, size.get(rootX) + size.get(rootY));
            } else {
                parent.put(rootY, rootX);
                size.put(rootX, size.get(rootX) + size.get(rootY));
            }
        }
    }
    
    // Test method for both implementations
    private static boolean testCase(int[] nums, int expected, String testName) {
        LongestConsecutive solution = new LongestConsecutive();
        
        int result1 = solution.longestConsecutive(nums.clone());
        int result2 = solution.longestConsecutiveUnionFind(nums.clone());
        
        boolean pass1 = result1 == expected;
        boolean pass2 = result2 == expected;
        
        System.out.println(testName + " - HashSet: " + (pass1 ? "PASS" : "FAIL") + 
                          " (" + result1 + "/" + expected + ")");
        System.out.println(testName + " - Union-Find: " + (pass2 ? "PASS" : "FAIL") + 
                          " (" + result2 + "/" + expected + ")");
        
        if (!pass1) System.out.println("  HashSet Expected: " + expected + ", Got: " + result1);
        if (!pass2) System.out.println("  Union-Find Expected: " + expected + ", Got: " + result2);
        
        return pass1 && pass2;
    }

    public static void main(String[] args) {
        System.out.println("=== LongestConsecutive Test Cases ===");
        
        int passCount = 0;
        int totalTests = 12;
        
        // Test Case 1: Basic example [100,4,200,1,3,2] -> 4 (sequence: 1,2,3,4)
        if (testCase(new int[]{100, 4, 200, 1, 3, 2}, 4, "Test 1: Basic Example")) passCount++;
        
        // Test Case 2: All consecutive [0,3,7,2,5,8,4,6,0,1] -> 9 (sequence: 0,1,2,3,4,5,6,7,8)
        if (testCase(new int[]{0, 3, 7, 2, 5, 8, 4, 6, 0, 1}, 9, "Test 2: Almost All Consecutive")) passCount++;
        
        // Test Case 3: Empty array [] -> 0
        if (testCase(new int[]{}, 0, "Test 3: Empty Array")) passCount++;
        
        // Test Case 4: Single element [1] -> 1
        if (testCase(new int[]{1}, 1, "Test 4: Single Element")) passCount++;
        
        // Test Case 5: All same elements [1,1,1,1] -> 1
        if (testCase(new int[]{1, 1, 1, 1}, 1, "Test 5: All Same Elements")) passCount++;
        
        // Test Case 6: No consecutive [1,3,5,7,9] -> 1
        if (testCase(new int[]{1, 3, 5, 7, 9}, 1, "Test 6: No Consecutive")) passCount++;
        
        // Test Case 7: Two sequences [1,2,3,10,11,12,13] -> 4 (longer sequence: 10,11,12,13)
        if (testCase(new int[]{1, 2, 3, 10, 11, 12, 13}, 4, "Test 7: Two Sequences")) passCount++;
        
        // Test Case 8: Negative numbers [-1,-2,0,1,2] -> 5 (sequence: -2,-1,0,1,2)
        if (testCase(new int[]{-1, -2, 0, 1, 2}, 5, "Test 8: Negative Numbers")) passCount++;
        
        // Test Case 9: Large gap [1,2,3,1000000] -> 3 (sequence: 1,2,3)
        if (testCase(new int[]{1, 2, 3, 1000000}, 3, "Test 9: Large Gap")) passCount++;
        
        // Test Case 10: Mixed order [4,2,1,3] -> 4 (sequence: 1,2,3,4)
        if (testCase(new int[]{4, 2, 1, 3}, 4, "Test 10: Mixed Order")) passCount++;
        
        // Test Case 11: Duplicates with gaps [1,2,0,1] -> 3 (sequence: 0,1,2)
        if (testCase(new int[]{1, 2, 0, 1}, 3, "Test 11: Duplicates with Gaps")) passCount++;
        
        // Test Case 12: Large consecutive sequence [0,1,2,3,4,5,6,7,8,9] -> 10
        if (testCase(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, 10, "Test 12: Large Consecutive")) passCount++;
        
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
        int[] largeArray = new int[10000];
        
        // Create array with multiple consecutive sequences
        for (int i = 0; i < 3333; i++) {
            largeArray[i] = i;                    // Sequence 0-3332
            largeArray[i + 3333] = i + 5000;      // Sequence 5000-8332  
            largeArray[i + 6666] = i + 10000;     // Sequence 10000-13332
        }
        
        // Shuffle the array
        java.util.List<Integer> list = new java.util.ArrayList<>();
        for (int num : largeArray) {
            list.add(num);
        }
        java.util.Collections.shuffle(list);
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = list.get(i);
        }
        
        LongestConsecutive solution = new LongestConsecutive();
        
        long startTime = System.nanoTime();
        int result1 = solution.longestConsecutive(largeArray);
        long hashSetTime = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result2 = solution.longestConsecutiveUnionFind(largeArray);
        long unionFindTime = System.nanoTime() - startTime;
        
        System.out.println("Large array result: " + result1 + " (expected: 3333)");
        System.out.println("HashSet approach: " + hashSetTime + " ns");
        System.out.println("Union-Find approach: " + unionFindTime + " ns");
        System.out.println("Both approaches achieve O(n) time complexity");
        
        // Algorithm complexity explanation
        System.out.println("\n=== Algorithm Analysis ===");
        System.out.println("HashSet Approach:");
        System.out.println("- Time: O(n) - each element visited at most twice");
        System.out.println("- Space: O(n) - for the HashSet storage");
        System.out.println("- Strategy: Only start counting from sequence beginnings");
        
        System.out.println("\nUnion-Find Approach:");
        System.out.println("- Time: O(n) - with path compression and union by rank");
        System.out.println("- Space: O(n) - for parent and size maps");
        System.out.println("- Strategy: Union consecutive numbers and find largest component");
    }
}
