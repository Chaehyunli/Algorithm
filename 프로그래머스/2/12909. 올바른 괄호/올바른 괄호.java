import java.util.*;

class Solution {
    boolean solution(String s) {
        
        Stack<Character> stack = new Stack<>();
        
        boolean answer = true;
        
        for(int i = 0; i < s.length(); i++){
            char current = s.charAt(i); // 현재의 괄호
            
            if(current == '('){
                stack.push(current);
            }else{
                if(stack.isEmpty()){
                    return false;
                }
                
                stack.pop();
            }
        }
        

        // 남은 '(' 괄호가 없어야 true
        return stack.isEmpty();
    }
}