import java.util.*;

class Solution {
    public int solution(String[][] clothes) {
        
        Map<String, Integer> map = new HashMap<>();
        
        for(String[] cloth : clothes){
            String type = cloth[1];
            String name = cloth[0];
            
            map.put(type, map.getOrDefault(type, 0) + 1);
        }
        
        int answer = 1;
        
        for(String key : map.keySet()){
            answer *= (map.get(key) + 1);
        }
        return answer - 1;
    }
}