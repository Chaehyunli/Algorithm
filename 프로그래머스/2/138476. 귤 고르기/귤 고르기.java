import java.util.*;

class Solution {
    public int solution(int k, int[] tangerine) {
        
        Map<Integer, Integer> map = new HashMap<>();
        
        for(int i = 0; i < tangerine.length; i++){
            map.put(tangerine[i], map.getOrDefault(tangerine[i], 0) + 1);
        }
        
        List<Map.Entry<Integer, Integer>> entryList = new ArrayList<>(map.entrySet());
        
        entryList.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        
        int answer = 0;
        int sum = 0;
        
        for(Map.Entry<Integer, Integer> entry : entryList){
            int orangeSize = entry.getKey(); 
            int count = entry.getValue();
            
            sum += count;
            answer++;
            
            if(sum >= k){
                break;
            }
        }
        
        return answer;
    }
}