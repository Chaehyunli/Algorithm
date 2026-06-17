import java.util.*;

class Solution {
    public int solution(int[] queue1, int[] queue2) {
        
        Queue<Integer> q1 = new LinkedList<>();
        Queue<Integer> q2 = new LinkedList<>();
        
        long sum1 = 0;
        long sum2 = 0;
        
        for(int q : queue1){
            sum1 += q;
            q1.add(q);
        }
        
        for(int q : queue2){
            sum2 += q;
            q2.add(q);
        }
        
        
        if((sum1 + sum2) % 2 != 0){
            return -1;
        }
        
        long target = (sum1 + sum2) / 2;
        int count1 = 0;
        int count2 = 0;
        
        int Maxcount = (queue1.length + queue2.length) * 2;
        
        
        while(count1 + count2 < Maxcount){
            if (sum1 == target) {
                return count1 + count2;
            }
            
            if (sum1 > target) {
                // q1의 합이 더 크면 q1에서 빼서 q2로 이동
                int val = q1.poll();
                sum1 -= val;
                q2.add(val);
                count1++;
            } else {
                // q1의 합이 더 작으면 q2에서 빼서 q1로 이동
                int val = q2.poll();
                sum1 += val;
                q1.add(val);
                count2++;
            }
        }
        
        return -1;
    }
}