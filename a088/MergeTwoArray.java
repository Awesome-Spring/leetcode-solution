package a088;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 给你两个按 非递减顺序 排列的整数数组 nums1 和 nums2，另有两个整数 m 和 n ，分别表示 nums1 和 nums2 中的元素数目。
 * <p>
 * 请你 合并 nums2 到 nums1 中，使合并后的数组同样按 非递减顺序 排列。
 * <p>
 * 注意：最终，合并后数组不应由函数返回，而是存储在数组 nums1 中。为了应对这种情况，nums1 的初始长度为 m + n，其中前 m 个元素表示应合并的元素，后 n 个元素为 0 ，应忽略。nums2 的长度为 n 。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
 * 输出：[1,2,2,3,5,6]
 * 解释：需要合并 [1,2,3] 和 [2,5,6] 。
 * 合并结果是 [1,2,2,3,5,6] ，其中斜体加粗标注的为 nums1 中的元素。
 * 示例 2：
 * <p>
 * 输入：nums1 = [1], m = 1, nums2 = [], n = 0
 * 输出：[1]
 * 解释：需要合并 [1] 和 [] 。
 * 合并结果是 [1] 。
 * 示例 3：
 * <p>
 * 输入：nums1 = [0], m = 0, nums2 = [1], n = 1
 * 输出：[1]
 * 解释：需要合并的数组是 [] 和 [1] 。
 * 合并结果是 [1] 。
 * 注意，因为 m = 0 ，所以 nums1 中没有元素。nums1 中仅存的 0 仅仅是为了确保合并结果可以顺利存放到 nums1 中。
 */
public class MergeTwoArray {

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // 从后往前合并，避免覆盖nums1中的有效元素
        int i = m - 1;      // nums1的最后一个有效元素索引
        int j = n - 1;      // nums2的最后一个元素索引  
        int k = m + n - 1;  // 合并后数组的最后一个位置
        
        // 从后往前比较，将较大的元素放到nums1的末尾
        while (i >= 0 && j >= 0) {
            if (nums1[i] > nums2[j]) {
                nums1[k] = nums1[i];
                i--;
            } else {
                nums1[k] = nums2[j];
                j--;
            }
            k--;
        }
        
        // 如果nums2还有剩余元素，复制到nums1中
        while (j >= 0) {
            nums1[k] = nums2[j];
            j--;
            k--;
        }
        
        // 如果nums1还有剩余元素，它们已经在正确位置，无需移动
    }
    
    // Alternative implementation using auxiliary array
    public void mergeWithAuxArray(int[] nums1, int m, int[] nums2, int n) {
        int[] aux = new int[m];
        // Copy nums1's valid elements to auxiliary array
        System.arraycopy(nums1, 0, aux, 0, m);
        
        int i = 0, j = 0, k = 0;
        
        // Merge aux and nums2 into nums1
        while (i < m && j < n) {
            if (aux[i] <= nums2[j]) {
                nums1[k++] = aux[i++];
            } else {
                nums1[k++] = nums2[j++];
            }
        }
        
        // Copy remaining elements
        while (i < m) {
            nums1[k++] = aux[i++];
        }
        while (j < n) {
            nums1[k++] = nums2[j++];
        }
    }

    // Test method for both implementations
    private static boolean testCase(int[] nums1Input, int m, int[] nums2, int n, int[] expected, String testName) {
        // Test in-place merge
        int[] nums1 = nums1Input.clone();
        MergeTwoArray solution = new MergeTwoArray();
        solution.merge(nums1, m, nums2.clone(), n);
        
        boolean pass1 = java.util.Arrays.equals(nums1, expected);
        
        // Test auxiliary array merge
        int[] nums1Aux = nums1Input.clone();
        solution.mergeWithAuxArray(nums1Aux, m, nums2.clone(), n);
        
        boolean pass2 = java.util.Arrays.equals(nums1Aux, expected);
        
        System.out.println(testName + " - In-place: " + (pass1 ? "PASS" : "FAIL"));
        System.out.println(testName + " - Auxiliary: " + (pass2 ? "PASS" : "FAIL"));
        
        if (!pass1) {
            System.out.println("  In-place Expected: " + java.util.Arrays.toString(expected) + 
                             ", Got: " + java.util.Arrays.toString(nums1));
        }
        if (!pass2) {
            System.out.println("  Auxiliary Expected: " + java.util.Arrays.toString(expected) + 
                             ", Got: " + java.util.Arrays.toString(nums1Aux));
        }
        
        return pass1 && pass2;
    }

    public static void main(String[] args) {
        System.out.println("=== MergeTwoArray Test Cases ===");
        
        int passCount = 0;
        int totalTests = 10;
        
        // Test Case 1: Basic example [1,2,3,0,0,0] m=3, [2,5,6] n=3 -> [1,2,2,3,5,6]
        if (testCase(new int[]{1, 2, 3, 0, 0, 0}, 3, new int[]{2, 5, 6}, 3, 
                    new int[]{1, 2, 2, 3, 5, 6}, "Test 1: Basic Example")) passCount++;
        
        // Test Case 2: Second array empty [1] m=1, [] n=0 -> [1]
        if (testCase(new int[]{1}, 1, new int[]{}, 0, 
                    new int[]{1}, "Test 2: Second Array Empty")) passCount++;
        
        // Test Case 3: First array empty [0] m=0, [1] n=1 -> [1]
        if (testCase(new int[]{0}, 0, new int[]{1}, 1, 
                    new int[]{1}, "Test 3: First Array Empty")) passCount++;
        
        // Test Case 4: All elements from first array smaller [1,2,3,0,0,0] m=3, [4,5,6] n=3 -> [1,2,3,4,5,6]
        if (testCase(new int[]{1, 2, 3, 0, 0, 0}, 3, new int[]{4, 5, 6}, 3, 
                    new int[]{1, 2, 3, 4, 5, 6}, "Test 4: Sequential Order")) passCount++;
        
        // Test Case 5: All elements from second array smaller [4,5,6,0,0,0] m=3, [1,2,3] n=3 -> [1,2,3,4,5,6]
        if (testCase(new int[]{4, 5, 6, 0, 0, 0}, 3, new int[]{1, 2, 3}, 3, 
                    new int[]{1, 2, 3, 4, 5, 6}, "Test 5: Reverse Order")) passCount++;
        
        // Test Case 6: Interleaved elements [1,3,5,0,0,0] m=3, [2,4,6] n=3 -> [1,2,3,4,5,6]
        if (testCase(new int[]{1, 3, 5, 0, 0, 0}, 3, new int[]{2, 4, 6}, 3, 
                    new int[]{1, 2, 3, 4, 5, 6}, "Test 6: Interleaved")) passCount++;
        
        // Test Case 7: Duplicate elements [1,2,3,0,0,0] m=3, [2,3,4] n=3 -> [1,2,2,3,3,4]
        if (testCase(new int[]{1, 2, 3, 0, 0, 0}, 3, new int[]{2, 3, 4}, 3, 
                    new int[]{1, 2, 2, 3, 3, 4}, "Test 7: Duplicates")) passCount++;
        
        // Test Case 8: Single element arrays [2,0] m=1, [1] n=1 -> [1,2]
        if (testCase(new int[]{2, 0}, 1, new int[]{1}, 1, 
                    new int[]{1, 2}, "Test 8: Single Elements")) passCount++;
        
        // Test Case 9: Negative numbers [-1,0,1,0,0,0] m=3, [-2,2,3] n=3 -> [-2,-1,0,1,2,3]
        if (testCase(new int[]{-1, 0, 1, 0, 0, 0}, 3, new int[]{-2, 2, 3}, 3, 
                    new int[]{-2, -1, 0, 1, 2, 3}, "Test 9: Negative Numbers")) passCount++;
        
        // Test Case 10: Different sizes [1,2,0,0,0,0,0] m=2, [3,4,5,6,7] n=5 -> [1,2,3,4,5,6,7]
        if (testCase(new int[]{1, 2, 0, 0, 0, 0, 0}, 2, new int[]{3, 4, 5, 6, 7}, 5, 
                    new int[]{1, 2, 3, 4, 5, 6, 7}, "Test 10: Different Sizes")) passCount++;
        
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
        int[] largeNums1 = new int[2000];
        int[] largeNums2 = new int[1000];
        
        // Fill with sorted data
        for (int i = 0; i < 1000; i++) {
            largeNums1[i] = i * 2;
            largeNums2[i] = i * 2 + 1;
        }
        
        MergeTwoArray solution = new MergeTwoArray();
        
        long startTime = System.nanoTime();
        solution.merge(largeNums1.clone(), 1000, largeNums2.clone(), 1000);
        long inPlaceTime = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        solution.mergeWithAuxArray(largeNums1.clone(), 1000, largeNums2.clone(), 1000);
        long auxTime = System.nanoTime() - startTime;
        
        System.out.println("In-place merge: " + inPlaceTime + " ns");
        System.out.println("Auxiliary array merge: " + auxTime + " ns");
        System.out.println("Space complexity: In-place O(1) vs Auxiliary O(m)");
        
        // Demo with Java Proxy Pattern
        System.out.println("\n=== Java Proxy Pattern Demo ===");
        Hello h = (Hello)Proxy.newProxyInstance(Hello.class.getClassLoader(), new Class[]{Hello.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (method.getName().equals("hello")) {
                    System.out.println("Intercepted: hello," + args[0]);
                }
                return proxy;
            }
        });
        h.hello("world");
        System.out.println("Proxy pattern demonstrates dynamic method interception.");
    }

    interface  Hello{
        void hello(String name);
        void morning(String name);
    }
}
