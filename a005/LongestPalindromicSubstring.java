package a005;

/**
 * 最长回文子串
 * 给你一个字符串 s，找到 s 中最长的回文子串。
 * 
 * 回文串是一个正读和反读都一样的字符串。
 * 
 * 示例 1：
 * 输入：s = "babad"
 * 输出："bab"
 * 解释："aba" 也是一个有效答案。
 * 
 * 示例 2：
 * 输入：s = "cbbd"
 * 输出："bb"
 * 
 * 提示：
 * 1 <= s.length <= 1000
 * s 仅由数字和英文字母组成
 */
public class LongestPalindromicSubstring {
    
    /**
     * 方法1：中心扩展算法
     * 时间复杂度：O(n²) - 对每个可能的中心进行扩展
     * 空间复杂度：O(1) - 只使用常量额外空间
     * 
     * 思路：遍历每个可能的回文中心，包括单个字符和两个字符之间，
     * 然后向两边扩展，直到不能构成回文为止
     */
    public String longestPalindrome(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }
        
        String longest = "";
        
        for (int i = 0; i < s.length(); i++) {
            // 奇数长度回文：以i为中心
            String palindrome1 = expandAroundCenter(s, i, i);
            // 偶数长度回文：以i和i+1之间为中心
            String palindrome2 = expandAroundCenter(s, i, i + 1);
            
            // 更新最长回文
            if (palindrome1.length() > longest.length()) {
                longest = palindrome1;
            }
            if (palindrome2.length() > longest.length()) {
                longest = palindrome2;
            }
        }
        
        return longest;
    }
    
    /**
     * 辅助方法：从中心向两边扩展
     */
    private String expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        // 返回有效回文子串
        return s.substring(left + 1, right);
    }
    
    /**
     * 方法2：动态规划算法
     * 时间复杂度：O(n²) - 填充dp表
     * 空间复杂度：O(n²) - dp表存储
     * 
     * 思路：dp[i][j]表示从i到j的子串是否为回文
     * 状态转移：dp[i][j] = (s[i] == s[j]) && dp[i+1][j-1]
     */
    public String longestPalindromeDP(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }
        
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        String longest = s.substring(0, 1); // 初始化为第一个字符
        
        // 所有单个字符都是回文
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
        }
        
        // 检查长度为2的子串
        for (int i = 0; i < n - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                dp[i][i + 1] = true;
                longest = s.substring(i, i + 2);
            }
        }
        
        // 检查长度大于2的子串
        for (int len = 3; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                if (s.charAt(i) == s.charAt(j) && dp[i + 1][j - 1]) {
                    dp[i][j] = true;
                    longest = s.substring(i, j + 1);
                }
            }
        }
        
        return longest;
    }
    
    /**
     * 方法3：Manacher算法 (马拉车算法)
     * 时间复杂度：O(n) - 线性时间
     * 空间复杂度：O(n) - 预处理字符串
     * 
     * 思路：通过预处理消除奇偶长度差异，使用已知信息避免重复计算
     */
    public String longestPalindromeManacher(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }
        
        // 预处理：在每个字符间插入'#'，统一奇偶处理
        String processed = preprocess(s);
        int n = processed.length();
        int[] radius = new int[n]; // 每个位置的回文半径
        int center = 0; // 当前回文的中心
        int right = 0;  // 当前回文的右边界
        
        int maxLen = 0;
        int maxCenter = 0;
        
        for (int i = 0; i < n; i++) {
            // 利用回文的对称性
            int mirror = 2 * center - i;
            
            if (i < right) {
                radius[i] = Math.min(right - i, radius[mirror]);
            }
            
            // 尝试扩展
            try {
                while (i + radius[i] + 1 < n && i - radius[i] - 1 >= 0 &&
                       processed.charAt(i + radius[i] + 1) == processed.charAt(i - radius[i] - 1)) {
                    radius[i]++;
                }
            } catch (StringIndexOutOfBoundsException e) {
                // 边界处理
            }
            
            // 更新中心和右边界
            if (i + radius[i] > right) {
                center = i;
                right = i + radius[i];
            }
            
            // 更新最长回文
            if (radius[i] > maxLen) {
                maxLen = radius[i];
                maxCenter = i;
            }
        }
        
        // 从处理后的字符串中提取原始回文
        int start = (maxCenter - maxLen) / 2;
        return s.substring(start, start + maxLen);
    }
    
    /**
     * 预处理字符串：在字符间插入'#'
     */
    private String preprocess(String s) {
        StringBuilder sb = new StringBuilder();
        sb.append('^'); // 开始标记，避免边界检查
        for (int i = 0; i < s.length(); i++) {
            sb.append('#');
            sb.append(s.charAt(i));
        }
        sb.append('#');
        sb.append('$'); // 结束标记，避免边界检查
        return sb.toString();
    }
    
    // 测试方法
    private static boolean testCase(String input, String expected, String testName, LongestPalindromicSubstring solution) {
        String result1 = solution.longestPalindrome(input);
        String result2 = solution.longestPalindromeDP(input);
        String result3 = solution.longestPalindromeManacher(input);
        
        // 验证结果是否为回文且长度正确
        boolean pass1 = isPalindrome(result1) && result1.length() == expected.length();
        boolean pass2 = isPalindrome(result2) && result2.length() == expected.length();
        boolean pass3 = isPalindrome(result3) && result3.length() == expected.length();
        
        System.out.println(testName + " - Expand: " + (pass1 ? "PASS" : "FAIL") + 
                          " (\"" + result1 + "\")");
        System.out.println(testName + " - DP: " + (pass2 ? "PASS" : "FAIL") + 
                          " (\"" + result2 + "\")");
        System.out.println(testName + " - Manacher: " + (pass3 ? "PASS" : "FAIL") + 
                          " (\"" + result3 + "\")");
        
        if (!pass1 || !pass2 || !pass3) {
            System.out.println("  Input: \"" + input + "\", Expected length: " + expected.length());
            if (!pass1) System.out.println("  Expand failed: " + result1);
            if (!pass2) System.out.println("  DP failed: " + result2);
            if (!pass3) System.out.println("  Manacher failed: " + result3);
        }
        
        return pass1 && pass2 && pass3;
    }
    
    // 验证字符串是否为回文
    private static boolean isPalindrome(String s) {
        if (s == null) return false;
        int left = 0, right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("=== LongestPalindromicSubstring Test Cases ===");
        
        LongestPalindromicSubstring solution = new LongestPalindromicSubstring();
        int passCount = 0;
        int totalTests = 18;
        
        // Test Case 1: Basic example "babad" -> "bab" or "aba" (length 3)
        if (testCase("babad", "bab", "Test 1: Basic Example", solution)) passCount++;
        
        // Test Case 2: Even length palindrome "cbbd" -> "bb" (length 2)
        if (testCase("cbbd", "bb", "Test 2: Even Length", solution)) passCount++;
        
        // Test Case 3: Single character "a" -> "a" (length 1)
        if (testCase("a", "a", "Test 3: Single Character", solution)) passCount++;
        
        // Test Case 4: Two identical characters "aa" -> "aa" (length 2)
        if (testCase("aa", "aa", "Test 4: Two Identical", solution)) passCount++;
        
        // Test Case 5: Two different characters "ab" -> "a" or "b" (length 1)
        if (testCase("ab", "a", "Test 5: Two Different", solution)) passCount++;
        
        // Test Case 6: Entire string is palindrome "racecar" -> "racecar" (length 7)
        if (testCase("racecar", "racecar", "Test 6: Entire Palindrome", solution)) passCount++;
        
        // Test Case 7: No palindrome longer than 1 "abcdef" -> any single char (length 1)
        if (testCase("abcdef", "a", "Test 7: No Long Palindrome", solution)) passCount++;
        
        // Test Case 8: Multiple palindromes "abacabad" -> "abacaba" (length 7)
        if (testCase("abacabad", "abacaba", "Test 8: Multiple Palindromes", solution)) passCount++;
        
        // Test Case 9: Nested palindromes "bananas" -> "anana" (length 5)
        if (testCase("bananas", "anana", "Test 9: Nested Palindromes", solution)) passCount++;
        
        // Test Case 10: With numbers "12321abc" -> "12321" (length 5)
        if (testCase("12321abc", "12321", "Test 10: With Numbers", solution)) passCount++;
        
        // Test Case 11: Repeated patterns "aaabaaaa" -> "aaabaaa" (length 7)
        if (testCase("aaabaaaa", "aaabaaa", "Test 11: Repeated Patterns", solution)) passCount++;
        
        // Test Case 12: All same characters "aaaa" -> "aaaa" (length 4)
        if (testCase("aaaa", "aaaa", "Test 12: All Same", solution)) passCount++;
        
        // Test Case 13: Complex pattern "civilwartestingwhetherthatpeoplevilwar" -> check manually
        String complex = "civilwartestingwhetherthatpeoplevilwar";
        String complexResult = solution.longestPalindrome(complex);
        boolean complexPass = isPalindrome(complexResult);
        System.out.println("Test 13: Complex Pattern - " + (complexPass ? "PASS" : "FAIL") + 
                          " (\"" + complexResult + "\", length=" + complexResult.length() + ")");
        if (complexPass) passCount++;
        
        // Test Case 14: Alternating pattern "ababababa" -> "ababababa" (length 9)
        if (testCase("ababababa", "ababababa", "Test 14: Alternating", solution)) passCount++;
        
        // Test Case 15: Edge case with special chars "a!b!a" -> "a!b!a" (length 5) 
        if (testCase("a!b!a", "a!b!a", "Test 15: Special Characters", solution)) passCount++;
        
        // Test Case 16: Long palindrome in middle "xyzabccbaxyz" -> "abccba" (length 6)
        if (testCase("xyzabccbaxyz", "abccba", "Test 16: Middle Palindrome", solution)) passCount++;
        
        // Test Case 17: Partial overlap "abcdeffedcba" -> entire string (length 12)
        if (testCase("abcdeffedcba", "abcdeffedcba", "Test 17: Full Palindrome", solution)) passCount++;
        
        // Test Case 18: Mixed case (case sensitive) "Aa" -> "A" or "a" (length 1)
        if (testCase("Aa", "A", "Test 18: Mixed Case", solution)) passCount++;
        
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
        String longString = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz" + 
                           "zyxwvutsrqponmlkjihgfedcbazyxwvutsrqponmlkjihgfedcba";
        
        long startTime, endTime;
        
        // Test Expand Around Centers
        startTime = System.nanoTime();
        String result1 = solution.longestPalindrome(longString);
        endTime = System.nanoTime();
        long expandTime = endTime - startTime;
        
        // Test Dynamic Programming
        startTime = System.nanoTime();
        String result2 = solution.longestPalindromeDP(longString);
        endTime = System.nanoTime();
        long dpTime = endTime - startTime;
        
        // Test Manacher's Algorithm
        startTime = System.nanoTime();
        String result3 = solution.longestPalindromeManacher(longString);
        endTime = System.nanoTime();
        long manacherTime = endTime - startTime;
        
        System.out.println("Long string length: " + longString.length());
        System.out.println("Expand Around Centers: " + result1.length() + " chars (" + expandTime + " ns)");
        System.out.println("Dynamic Programming: " + result2.length() + " chars (" + dpTime + " ns)");
        System.out.println("Manacher's Algorithm: " + result3.length() + " chars (" + manacherTime + " ns)");
        
        // Algorithm complexity analysis
        System.out.println("\n=== Algorithm Analysis ===");
        System.out.println("Expand Around Centers:");
        System.out.println("- Time: O(n²) - Check each possible center");
        System.out.println("- Space: O(1) - Constant extra space");
        System.out.println("- Best for: Most intuitive, good balance of simplicity and efficiency");
        
        System.out.println("\nDynamic Programming:");
        System.out.println("- Time: O(n²) - Fill DP table");
        System.out.println("- Space: O(n²) - DP table storage");
        System.out.println("- Best for: When you need to know all palindromic substrings");
        
        System.out.println("\nManacher's Algorithm:");
        System.out.println("- Time: O(n) - Linear time with preprocessing");
        System.out.println("- Space: O(n) - Preprocessing and radius array");
        System.out.println("- Best for: Optimal performance on very large strings");
    }
}