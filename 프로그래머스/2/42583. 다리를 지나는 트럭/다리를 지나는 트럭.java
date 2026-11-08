import java.util.*;

class Solution {
    public int solution(int bridge_length, int weight, int[] truck_weights) {
        Queue<Integer> queue = new LinkedList<>();
        
        for(int i = 0; i < bridge_length; i++){
            queue.offer(0);
        }
        
        int time = 0;
        int currentWeight = 0;
        int truckIdx = 0;
        
        while(truckIdx < truck_weights.length){
            time++;
            
            // 시간이 지나서, 맨 앞 트럭이 빠저 나감
            currentWeight -= queue.poll();
            
            // 다음 대기 트럭가 다리에 올라갈 수 있으면
            if(currentWeight + truck_weights[truckIdx] <= weight){
                queue.offer(truck_weights[truckIdx]);
                currentWeight += truck_weights[truckIdx];
                truckIdx++;
            }else{
                queue.offer(0);
            }
        }
        
        // time은 마지막 트럭이 다리에 올라간 시간임
        // 마지막 트럭이 다리를 통과하기 위해서 bridge_length만큼 더 시간이 필요
        return time + bridge_length;
    }
}