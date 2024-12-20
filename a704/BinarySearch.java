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

    public static void main(String[] args) {

        int[] a = new int[]{-1, 0, 3, 5, 9, 12};
        int target = 9;
        assert binarySearch0(a, target) == Arrays.binarySearch(a, target);

        System.out.println(mostLeft(new int[]{0, 1, 1, 2, 2, 3, 4}, 2) == 3);
        System.out.println(mostRight(new int[]{0, 1, 1, 2, 2, 3, 4}, 2) == 4);

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
