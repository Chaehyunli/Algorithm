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
            
            currentWeight -= queue.poll();
            
            if(currentWeight + truck_weights[truckIdx] <= weight){
                queue.offer(truck_weights[truckIdx]);
                currentWeight += truck_weights[truckIdx];
                truckIdx++;
            }else{
                queue.offer(0);
            }
        }
        
        return time + bridge_length;
    }
}