import java.util.*;

class Solution {
    public int[] solution(String s) {
        int[] answer = new int[s.length()];
        
        HashMap<Character, Integer> lastSeen = new HashMap<>();
        
        for(int i = 0; i < s.length(); i++){
            char current = s.charAt(i);
            
            if(lastSeen.containsKey(current)){
                answer[i] = i - lastSeen.get(current);
            }else{
                answer[i] = -1;
            }
            
            lastSeen.put(current,i);
        }
            
        return answer;
    }
}