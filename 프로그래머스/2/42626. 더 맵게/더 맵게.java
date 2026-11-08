import java.util.*;

class Solution {
    public int solution(int[] scoville, int K) {
        
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        
        // 모든 스코빌 지수를 우선순위 큐에 넣음
        for(int i = 0; i < scoville.length; i++){
            queue.offer(scoville[i]);
        }
        
        // 반복해야하는 최소 횟수
        int answer = 0;
        
        while(queue.peek() < K){
            
            // 모든 음식의 스코빌 지수를 K 이상으로 만들 수 없으면 -1
            // 반복문에서 개수가 2개 미만인데, K 보다 작으면 불가능한 케이스임
            if(queue.size() < 2){
                return -1;
            }
            
            int current1 = queue.poll();
            int current2 = queue.poll();
            
            queue.offer(current1 + current2 * 2);
            
            answer++;
        }
        
        return answer;
    }
}