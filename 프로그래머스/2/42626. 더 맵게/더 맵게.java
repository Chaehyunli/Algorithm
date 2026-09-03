import java.util.*;

class Solution {
    public int solution(int[] scoville, int K) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        
        for(int i = 0; i < scoville.length; i++){
            queue.offer(scoville[i]);
        }
        
        int count = 0;
        
        while(queue.peek() < K){
            
            if (queue.size() < 2) {
                return -1;
            }
            
            // 가장 맵지 않은 음식의 스코빌 지수
            int current1 = queue.poll();
            // 두 번째로 맵지 않은 음식의 스코빌 지수
            int current2 = queue.poll();
            
            int mixed = current1 + current2 * 2;
            
            queue.offer(mixed);
            
            count++;
        }
        
        return count;
    }
}