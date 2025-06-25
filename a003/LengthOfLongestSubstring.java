package a003;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长
 * 子串
 * 的长度。
 * <p>
 * <p>
 * <p>
 * 示例 1:
 * <p>
 * 输入: s = "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 * 示例 2:
 * <p>
 * 输入: s = "bbbbb"
 * 输出: 1
 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 * 示例 3:
 * <p>
 * 输入: s = "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 * 请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
 * <p>
 * <p>
 * 提示：
 * <p>
 * 0 <= s.length <= 5 * 104
 * s 由英文字母、数字、符号和空格组成
 */
public class LengthOfLongestSubstring {

    //滑动窗口
    public static int lengthOfLongestSubstring(String s) {
        // pwwkew   =>  wke  =>3
        List<Character> window = new LinkedList<>();
        int max = 0;
        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            int index = window.indexOf(c);
            while (index >= 0) {
                window.removeFirst();
                index--;
            }
            window.addLast(c);
            max = Math.max(window.size(), max);
        }
        return max;
    }

    //最长子串
    public static String longestSubstring(String s) {
        // pwwkew   =>  wke
        List<Character> window = new LinkedList<>();
        int max = 0;
        String longestSubstring = "";
        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            int index = window.indexOf(c);
            while (index >= 0) {
                window.removeFirst();
                index--;
            }
            window.addLast(c);
            if (window.size() > longestSubstring.length()) {
                longestSubstring = window.stream().map(String::valueOf).collect(Collectors.joining()); // 拼接字符串
            }
        }
        return longestSubstring;
    }

    // Test method
    private static boolean testCase(String input, int expectedLength, String expectedSubstring, String testName) {
        int actualLength = lengthOfLongestSubstring(input);
        String actualSubstring = longestSubstring(input);
        
        boolean lengthPass = actualLength == expectedLength;
        // For substring, we only check if the length matches and it's a valid substring
        boolean substringPass = actualSubstring.length() == expectedLength && 
                               input.contains(actualSubstring) && 
                               hasNoDuplicates(actualSubstring);
        
        System.out.println(testName + " - Length: " + (lengthPass ? "PASS" : "FAIL") + 
                          " (" + actualLength + "/" + expectedLength + ")");
        System.out.println(testName + " - Substring: " + (substringPass ? "PASS" : "FAIL") + 
                          " (\"" + actualSubstring + "\")");
        
        if (!lengthPass) {
            System.out.println("  Length Expected: " + expectedLength + ", Got: " + actualLength);
        }
        if (!substringPass) {
            System.out.println("  Substring issue: length=" + actualSubstring.length() + 
                             ", contains=" + input.contains(actualSubstring) + 
                             ", noDuplicates=" + hasNoDuplicates(actualSubstring));
        }
        
        return lengthPass && substringPass;
    }
    
    // Helper method to check if string has no duplicate characters
    private static boolean hasNoDuplicates(String s) {
        return s.chars().distinct().count() == s.length();
    }

    public static void main(String[] args) {
        System.out.println("=== LengthOfLongestSubstring Test Cases ===");
        
        int passCount = 0;
        int totalTests = 12;
        
        // Test Case 1: Basic example "abcabcbb" -> 3 ("abc")
        if (testCase("abcabcbb", 3, "abc", "Test 1: Basic Example")) passCount++;
        
        // Test Case 2: All same characters "bbbbb" -> 1 ("b")
        if (testCase("bbbbb", 1, "b", "Test 2: All Same Characters")) passCount++;
        
        // Test Case 3: Example from problem "pwwkew" -> 3 ("wke" or "kew")
        if (testCase("pwwkew", 3, "wke", "Test 3: Problem Example")) passCount++;
        
        // Test Case 4: Empty string "" -> 0
        if (testCase("", 0, "", "Test 4: Empty String")) passCount++;
        
        // Test Case 5: Single character "a" -> 1 ("a")
        if (testCase("a", 1, "a", "Test 5: Single Character")) passCount++;
        
        // Test Case 6: No repeating characters "abcdef" -> 6 ("abcdef")
        if (testCase("abcdef", 6, "abcdef", "Test 6: No Repeating")) passCount++;
        
        // Test Case 7: All different then repeat "abcdea" -> 5 ("bcdea")
        if (testCase("abcdea", 5, "bcdea", "Test 7: Cycle Pattern")) passCount++;
        
        // Test Case 8: Special characters and spaces "a!b@c# d" -> 8 ("a!b@c# d")
        if (testCase("a!b@c# d", 8, "a!b@c# d", "Test 8: Special Characters")) passCount++;
        
        // Test Case 9: Numbers "123123" -> 3 ("123")
        if (testCase("123123", 3, "123", "Test 9: Numbers")) passCount++;
        
        // Test Case 10: Mixed case "AaBbCc" -> 6 ("AaBbCc")
        if (testCase("AaBbCc", 6, "AaBbCc", "Test 10: Mixed Case")) passCount++;
        
        // Test Case 11: Long string with pattern "abcabcabcabc" -> 3 ("abc")
        if (testCase("abcabcabcabc", 3, "abc", "Test 11: Long Pattern")) passCount++;
        
        // Test Case 12: Complex pattern "dvdf" -> 3 ("vdf")
        if (testCase("dvdf", 3, "vdf", "Test 12: Complex Pattern")) passCount++;
        
        System.out.println("\n=== Test Results ===");
        System.out.println("Passed: " + passCount + "/" + totalTests);
        System.out.println("Success Rate: " + (passCount * 100.0 / totalTests) + "%");
        
        if (passCount == totalTests) {
            System.out.println("🎉 All tests passed!");
        } else {
            System.out.println("❌ Some tests failed. Please review the implementation.");
        }
        
        // Demo output from original main method
        System.out.println("\n=== Original Demo ===");
        System.out.println("lengthOfLongestSubstring(\"abcabcbb\"): " + lengthOfLongestSubstring("abcabcbb"));
        System.out.println("longestSubstring(\"pwwkew\"): " + longestSubstring("pwwkew"));
    }


}
