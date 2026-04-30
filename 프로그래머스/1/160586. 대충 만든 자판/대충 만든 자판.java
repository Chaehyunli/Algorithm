import java.util.*;

class Solution {
    public int[] solution(String[] keymap, String[] targets) {
        
        Map<Character,Integer> minPress = new HashMap<>();
        
        // minPress HashMap 만들기
        for(String key : keymap){
            for(int i = 0; i < key.length(); i++){
                char c = key.charAt(i);
                int count = i + 1;
                
                if(!minPress.containsKey(c) || count < minPress.get(c)){ // minPress에 key c가 없거나, 현재의 count가 더 최소일 경우
                    minPress.put(c, count); 
                }
            }
        }
        
        int[] answer = new int[targets.length];
        
        for (int i = 0; i < targets.length; i++){
            String target = targets[i];
            int total_sum = 0;
            boolean isPossible = true;
            
            for(int j = 0; j < target.length(); j++){
                
                char c = target.charAt(j);
                
                if(minPress.containsKey(c)){
                    total_sum += minPress.get(c);
                }else{
                    isPossible = false;
                    break;
                }
            }
            
            if(isPossible){
                answer[i] = total_sum;
            }else{
                answer[i] = -1;
            }
            
        }
        
        return answer;
    }
}