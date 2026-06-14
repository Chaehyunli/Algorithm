import java.util.*;

class Solution {
    public int solution(int[] order) {
        int answer = 0;
        
        Stack<Integer> subBelt = new Stack<>();
        
        int indx = 0;
        
        for(int i = 1; i <= order.length; i++){
            subBelt.push(i);
            
            while(!subBelt.isEmpty() && subBelt.peek() == order[indx]){
                indx++;
                subBelt.pop();
                answer++;
            }
        }
        
        return answer;
    }
}