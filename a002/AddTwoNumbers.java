package a002;

/**
 * 给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
 * <p>
 * 请你将两个数相加，并以相同形式返回一个表示和的链表。
 * <p>
 * 你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 * 输入：l1 = [2,4,3], l2 = [5,6,4]
 * 输出：[7,0,8]
 * 解释：342 + 465 = 807.
 * 示例 2：
 * <p>
 * 输入：l1 = [0], l2 = [0]
 * 输出：[0]
 * 示例 3：
 * <p>
 * 输入：l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
 * 9,9,9,9
 * 输出：[8,9,9,9,0,0,0,1]
 */
public class AddTwoNumbers {


    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    /**
     * 解决思路
     * <p>
     * 使用一个哨兵节点 dummy 来辅助构建结果链表，它的 next 指针将始终指向结果链表的头节点。 通过一个 current 指针来追踪当前链表的尾部，直接将新节点挂接到 current 上，避免单独处理链表的头节点问题，使代码更简洁。
     * 遍历l1 和 l2两个链表，逐位相加两个链表中对应节点的值。 如果某个链表较短，则用 0 作为其值进行补充。 保持进位 carry 的更新，用来记录上一位的进位值。
     * 处理进位 每次计算当前位的总和：sum = val1 + val2 + carry，其中： val1 和 val2 是两个链表当前节点的值（为空时记为 0）。 carry 是上一位的进位值（初始为 0）。 将总和分解为两部分： 当前位的值：sum % 10，用来创建一个新节点。 进位值：sum / 10，将在下一次迭代中加到总和中。 在链表遍历完成后，如果 carry 不为 0，需额外创建一个值为 carry 的新节点。
     * 终止条件 遍历的条件是：只要 l1 或 l2 未遍历完，或者还有进位 carry，循环就会继续。 这样可以自然处理链表长度不等的情况，以及最后的进位问题。
     * `
     */
    public ListNode addTwoNumbers1(ListNode l1, ListNode l2) {
        //虚拟节点
        ListNode dummyHead = new ListNode(-1);
        //游标节点,一直往下移动
        ListNode currentNode = dummyHead;
        int carry = 0;//节点相加和的进位
        //终止条件是：只要 l1 或 l2 未遍历完，或者还有进位 carry，循环就会继续
        while (l1 != null || l2 != null || carry != 0) {
            int temp1 = l1 != null ? l1.val : 0;
            int temp2 = l2 != null ? l2.val : 0;
            int sum = temp1 + temp2 + carry;
            int newNodeVal = sum % 10;//余数
            currentNode.next = new ListNode(newNodeVal);
            currentNode = currentNode.next;
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
            //更新进位
            carry = sum / 10;
        }
        return dummyHead.next;
    }

    //递归法
    public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        return addTwoNumbers(l1, l2, 0);
    }

    private ListNode addTwoNumbers(ListNode l1, ListNode l2, int carry) {
        if (l1 == null && l2 == null && carry == 0) {
            return null;
        }
        int sum = l1 == null ? (l2 == null ? carry : l2.val + carry) : (l2 == null ? l1.val + carry : l1.val + l2.val + carry);
        int newNodeVal = sum % 10; //余数
        carry = sum / 10;//节点相加和的进位
        ListNode currentNode = new ListNode(newNodeVal);
        currentNode.next = addTwoNumbers(l1 != null ? l1.next : null, l2 != null ? l2.next : null, carry);

        return currentNode;
    }

    // Helper method to create linked list from array
    private static ListNode createList(int[] arr) {
        if (arr.length == 0) return null;
        ListNode head = new ListNode(arr[0]);
        ListNode current = head;
        for (int i = 1; i < arr.length; i++) {
            current.next = new ListNode(arr[i]);
            current = current.next;
        }
        return head;
    }

    // Helper method to convert linked list to array for testing
    private static int[] listToArray(ListNode head) {
        java.util.List<Integer> result = new java.util.ArrayList<>();
        while (head != null) {
            result.add(head.val);
            head = head.next;
        }
        return result.stream().mapToInt(i -> i).toArray();
    }

    // Test method
    private static boolean testCase(int[] l1Arr, int[] l2Arr, int[] expected, String testName) {
        AddTwoNumbers solution = new AddTwoNumbers();
        ListNode l1 = createList(l1Arr);
        ListNode l2 = createList(l2Arr);
        
        // Test iterative method
        ListNode result1 = solution.addTwoNumbers1(l1, l2);
        int[] actual1 = listToArray(result1);
        
        // Recreate lists for recursive method (since they were modified)
        l1 = createList(l1Arr);
        l2 = createList(l2Arr);
        ListNode result2 = solution.addTwoNumbers2(l1, l2);
        int[] actual2 = listToArray(result2);
        
        boolean pass1 = java.util.Arrays.equals(actual1, expected);
        boolean pass2 = java.util.Arrays.equals(actual2, expected);
        
        System.out.println(testName + " - Iterative: " + (pass1 ? "PASS" : "FAIL"));
        System.out.println(testName + " - Recursive: " + (pass2 ? "PASS" : "FAIL"));
        if (!pass1) System.out.println("  Expected: " + java.util.Arrays.toString(expected) + ", Got: " + java.util.Arrays.toString(actual1));
        if (!pass2) System.out.println("  Expected: " + java.util.Arrays.toString(expected) + ", Got: " + java.util.Arrays.toString(actual2));
        
        return pass1 && pass2;
    }

    public static void main(String[] args) {
        System.out.println("=== AddTwoNumbers Test Cases ===");
        
        int passCount = 0;
        int totalTests = 8;
        
        // Test Case 1: Basic example [2,4,3] + [5,6,4] = [7,0,8] (342 + 465 = 807)
        if (testCase(new int[]{2,4,3}, new int[]{5,6,4}, new int[]{7,0,8}, "Test 1: Basic Addition")) passCount++;
        
        // Test Case 2: Zero + Zero = Zero
        if (testCase(new int[]{0}, new int[]{0}, new int[]{0}, "Test 2: Zero Addition")) passCount++;
        
        // Test Case 3: Different lengths with carry [9,9,9,9,9,9,9] + [9,9,9,9] = [8,9,9,9,0,0,0,1]
        if (testCase(new int[]{9,9,9,9,9,9,9}, new int[]{9,9,9,9}, new int[]{8,9,9,9,0,0,0,1}, "Test 3: Different Lengths with Carry")) passCount++;
        
        // Test Case 4: Single digit with carry [5] + [5] = [0,1]
        if (testCase(new int[]{5}, new int[]{5}, new int[]{0,1}, "Test 4: Single Digit Carry")) passCount++;
        
        // Test Case 5: One empty list simulation [1,2] + [0] = [1,2] (but we can't have empty lists, so use single zero)
        if (testCase(new int[]{1,2,3}, new int[]{0}, new int[]{1,2,3}, "Test 5: Adding Zero")) passCount++;
        
        // Test Case 6: Maximum single digits [9] + [9] = [8,1]
        if (testCase(new int[]{9}, new int[]{9}, new int[]{8,1}, "Test 6: Max Single Digits")) passCount++;
        
        // Test Case 7: Multiple carries [9,9] + [1] = [0,0,1]
        if (testCase(new int[]{9,9}, new int[]{1}, new int[]{0,0,1}, "Test 7: Multiple Carries")) passCount++;
        
        // Test Case 8: Large numbers [1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1] + [5,6,4] = [6,6,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1]
        if (testCase(new int[]{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1}, 
                    new int[]{5,6,4}, 
                    new int[]{6,6,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1}, 
                    "Test 8: Large Numbers")) passCount++;
        
        System.out.println("\n=== Test Results ===");
        System.out.println("Passed: " + passCount + "/" + totalTests);
        System.out.println("Success Rate: " + (passCount * 100.0 / totalTests) + "%");
        
        if (passCount == totalTests) {
            System.out.println("🎉 All tests passed!");
        } else {
            System.out.println("❌ Some tests failed. Please review the implementation.");
        }
    }

}
