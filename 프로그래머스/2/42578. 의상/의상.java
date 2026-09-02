import java.util.*;

class Solution {
    public int solution(String[][] clothes) {
        
        Map<String, Integer> hashmap = new HashMap<>();
        
        for(String[] cloth : clothes){
            String key = cloth[1];
            String value = cloth[0];
            
            hashmap.put(key, hashmap.getOrDefault(key, 0) + 1);
            
        }
        
        // System.out.print(hashmap.size());
        
        int[] list = new int[hashmap.size()];
        
        int count = 0;
        for(Map.Entry<String, Integer> entry: hashmap.entrySet()){
            list[count] = entry.getValue() + 1;
            count++;
        }
        
        int answer = 1;
        
        for(int l : list){
            answer *= l;
        }
        
        return answer - 1;
    }
}