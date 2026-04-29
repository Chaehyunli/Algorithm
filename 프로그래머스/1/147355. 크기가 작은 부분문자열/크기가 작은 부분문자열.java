import java.util.*;

class Solution {
    public int solution(String t, String p) {
        
        int t_size = t.length(); // t의 길이
        int p_size = p.length(); // p의 길이
        long p_value = Long.parseLong(p); // 비교할 숫자
            
        List<Long> part_number = new ArrayList<>();
        
        for(int i = 0; i < t_size - p_size + 1; i++){
            part_number.add(Long.parseLong(t.substring(i,i + p_size)));
        }
        
        int answer = 0;
        
        for(long num : part_number){
            if(num <= p_value){
                answer++;
            }
        }
        
        return answer;
    }
}