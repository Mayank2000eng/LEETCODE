/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode rotateRight(ListNode head, int k) {
        if(head==null||k==0) return head;
        int c=0;
        ListNode t1=head;
        while(t1!=null){
            c++;
            t1=t1.next;
        }
        if(c==1) return head;
        System.out.println(c);
        k=k%c;
        if(k==0) return head;
        t1=head;
        int r=c-k;
        ListNode t=null;
        while(r>0){
            t=t1;
            t1=t1.next;
            r--;
        }
        if(t==null) return head;
        t.next=null;
        ListNode h1=t1;
        while(t1.next!=null) t1=t1.next;
        t1.next=head;
        return h1;
    }
}
