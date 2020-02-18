/**
 * 
 */
package com.neel.example;

/**
 * @author nsalbarde
 *
 */
public class AddTwoNumber {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ListNode n1 = new ListNode(6);
		ListNode n2 = new ListNode(4);
		n2.next = n1;
		ListNode n3 = new ListNode(5);
		n3.next = n2;
		
		ListNode m1 = new ListNode(8);
		ListNode m2 = new ListNode(6);
		m2.next = m1;
		ListNode m3 = new ListNode(5);
		m3.next = m2;
		
		ListNode ans = addTwoNumbers(n3, m3); 
		while(ans != null) {
			System.out.println(ans.val);
			ans = ans.next;
		}
	}
	
	public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		return addTwoNums(l1, l2, 0);
	}
	
	private static ListNode addTwoNums(ListNode l1, ListNode l2, int carry) {
		ListNode head = null;
		if(l1 != null || l2 != null || carry != 0)
		{
			int val1 = l1 != null ? l1.val : 0;
			int val2 = l2 != null ? l2.val : 0;
			int sum = val1 + val2 + carry;
			
			if(sum > 9) {
				carry = sum / 10;
				sum = sum % 10;
			}else {
				carry = 0;
			}
			head = new ListNode(sum);
			head.val = sum;
			System.out.println("Node created with : "+head.val);
			l1 = l1 != null ? l1.next : null;
			l2 = l2 != null ? l2.next : null;
			head.next = addTwoNums(l1, l2, carry);	
		}
		return head;
	}
}

class ListNode {
	  int val;
	  ListNode next;
	  ListNode(int x) { val = x; }
}
