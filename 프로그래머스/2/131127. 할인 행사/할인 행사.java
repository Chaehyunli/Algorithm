import java.util.*;

class Solution {
    
    private boolean isEqual(Map<String, Integer> wantMap, Map<String, Integer> discountMap){
        
        for(Map.Entry<String, Integer> wM : wantMap.entrySet()){
            String k = wM.getKey();
            
            if(!discountMap.containsKey(k) || discountMap.get(k) < wantMap.get(k)){
                return false;
            }
        }
        
        return true;
    }
    
    public int solution(String[] want, int[] number, String[] discount) {
        int answer = 0;
        
        Map<String, Integer> wantMap = new HashMap<>();
        
        // banana : 3
        for(int i = 0; i < want.length; i++){
            wantMap.put(want[i], number[i]);
        }
        
        
        Map<String, Integer> discountMap = new HashMap<>();
        
        for(int i = 0; i < 10; i++){
            discountMap.put(discount[i], discountMap.getOrDefault(discount[i], 0) + 1);    
        }
        
        // 0번째 날 가입했을 때 조건을 만족하는지
        if(isEqual(wantMap, discountMap)){
            answer++;
        }
        
        for(int i = 10; i < discount.length; i++){
            String removeItem = discount[i - 10];
            
            // 맨 앞 제거
            if(discountMap.get(removeItem) == 1){
                discountMap.remove(removeItem);
            }else{
                discountMap.put(removeItem, discountMap.getOrDefault(removeItem, 0) - 1);   
            }
            
            // 새로운 것 추가
            String addItem = discount[i];
            discountMap.put(addItem, discountMap.getOrDefault(addItem, 0) + 1);
            
            if(isEqual(wantMap, discountMap)){
                answer++;
            }
        }
        
        return answer;
    }
}