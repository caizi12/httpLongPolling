package com.lf.sum;

/**
 * 两链接相加输出新链表
 */
public class LinkTwoNumAdd {

    public ListNode addNums(ListNode l1, ListNode l2) {
        ListNode result = new ListNode();
        int carray = 0;
        ListNode currNode = null;
        while (l1 != null || l2 != null) {
            int valA = l1 == null ? 0 : l1.val;
            int valB = l2 == null ? 0 : l2.val;
            int currentSum = valA + valB + carray;
            currNode = new ListNode(currentSum % 10);
            result.next = currNode;
            carray = currentSum / 10;
            if (l1 != null) {
                l1 = l1.next;
            }

            if (l2 != null) {
                l2 = l2.next;
            }
        }

        if (carray == 1 && currNode != null) {
            currNode.next = new ListNode(1);
        }
        return result;
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // 课程 A -》 链表 -> 基础篇
        ListNode dummy = new ListNode();
        ListNode curr = dummy;
        int carry = 0;
        while (l1 != null || l2 != null) {
            int x = l1 == null ? 0 : l1.val;
            int y = l2 == null ? 0 : l2.val;

            int total = x + y + carry;
            curr.next = new ListNode(total % 10);
            // bug 修复：视频中忘了移动 curr 指针了
            curr = curr.next;
            carry = total / 10;

            if (l1 != null) { l1 = l1.next; }
            if (l2 != null) { l2 = l2.next; }
        }
        if (carry != 0) { curr.next = new ListNode(carry); }
        return dummy.next;
    }

    public static class ListNode {
        public int val;
        public ListNode next;

        public ListNode() {
        }

        public ListNode(int val) {
            this.val = val;
        }
    }

}
