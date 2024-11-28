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

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("abcabcbb"));
        System.out.println(longestSubstring("pwwkew"));
    }


}
