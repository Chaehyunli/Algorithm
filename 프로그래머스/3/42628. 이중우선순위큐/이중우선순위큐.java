import java.util.*;

class Solution {
    
    // "D 1" 처리용 우선선위 큐
    static PriorityQueue<Integer> maxQueue = new PriorityQueue<>((a, b) ->{
        return Integer.compare(b, a);
    });

    // "D -1" 처리용 우선선위 큐
    static PriorityQueue<Integer> minQueue = new PriorityQueue<>();

    // 현재 어떤 데이터가 있는지 관리
    static Map<Integer, Integer> map = new HashMap<>();
    
    static void insert(int value){
        maxQueue.offer(value);
        minQueue.offer(value);
        map.put(value, map.getOrDefault(value, 0) + 1);
    }
    
    static void clean(PriorityQueue<Integer> heap){
        while(!heap.isEmpty() && map.getOrDefault(heap.peek(), 0) == 0){
            heap.poll();
        }
    }
    
    static void deleteMin(){
        
        clean(minQueue);
        
        if (!minQueue.isEmpty()) {
            int min = minQueue.poll();
            map.put(min, map.get(min) - 1);
        }
    }
    
    static void deleteMax(){
        
        clean(maxQueue);
        
        if (!maxQueue.isEmpty()) {
            int max = maxQueue.poll();
            map.put(max, map.get(max) - 1);
        }
    }
    
    static int getMax(){
        clean(maxQueue);
        
        return maxQueue.peek();
    }
    
    static int getMin(){
        clean(minQueue);
        
        return minQueue.peek();
    }
    
    
    public int[] solution(String[] operations) {
        
        for(String operation : operations){
            String[] temp = operation.split(" ");
            String command = temp[0];
            int value = Integer.parseInt(temp[1]);
            
            if(command.equals("I")){
                insert(value);
            }else{
                if(value == 1){
                    deleteMax();
                }else{
                    deleteMin();
                }
            }
        }
        
        clean(maxQueue);
        clean(minQueue);
        
        if(!maxQueue.isEmpty() && !minQueue.isEmpty()){
            int max = getMax();
            int min = getMin();
            
            return new int[]{max, min};
        }
        
        return new int[]{0, 0};
    }
}