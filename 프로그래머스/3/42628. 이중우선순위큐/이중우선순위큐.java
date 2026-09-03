import java.util.*;

class Solution {
    
    public PriorityQueue<Integer> min_queue = new PriorityQueue<>();
    public PriorityQueue<Integer> max_queue = new PriorityQueue<>(Collections.reverseOrder());
    public Map<Integer, Integer> map = new HashMap<>();
    
    // 삽입
    public void insert(int val) {
        min_queue.offer(val);
        max_queue.offer(val);
        map.put(val, map.getOrDefault(val, 0) + 1);
    }
    
    // 최댓값 삭제
    public void deleteMax() {
        cleanUp(max_queue);
        if (!max_queue.isEmpty()) {
            int max = max_queue.poll();
            map.put(max, map.get(max) - 1);
        }
    }

    // 최솟값 삭제
    public void deleteMin() {
        cleanUp(min_queue);
        if (!min_queue.isEmpty()) {
            int min = min_queue.poll();
            map.put(min, map.get(min) - 1);
        }
    }
    
    // 이미 다른 쪽에서 삭제된 원소 제거(동기화 작업)
    private void cleanUp(PriorityQueue<Integer> heap) {
        
        // map에서 개수가 0이라고 나온 것은 모두 버림
        while (!heap.isEmpty() && map.getOrDefault(heap.peek(), 0) == 0) {
            heap.poll();
        }
    }
    
    public int getMax() {
        cleanUp(max_queue);
        return max_queue.isEmpty() ? 0 : max_queue.peek();
    }

    public int getMin() {
        cleanUp(min_queue);
        return min_queue.isEmpty() ? 0 : min_queue.peek();
    }
    
    public int[] solution(String[] operations) {
        
        for(String operation : operations){
            
            String[] parts = operation.split(" ");
            String command = parts[0];
            int value = Integer.parseInt(parts[1]);
            
            if(command.equals("I")){
                insert(value);
            }else if(command.equals("D")){
                if(value == 1){
                    deleteMax();
                }else{
                    deleteMin();
                }
            }
        }
        
        int max = getMax();
        int min = getMin();
        
        cleanUp(min_queue);
        
        if(min_queue.isEmpty()){
            return new int[]{0, 0};
        }
        
        return new int[]{max, min};
    }
}