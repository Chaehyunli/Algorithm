import java.util.*;

class Solution {
    public String solution(String number, int k) {
        
        Stack<Character> stack = new Stack<>();
        int count = 0;
        
        for(int i = 0; i < number.length(); i++){
            char c = number.charAt(i);
            
            while(count < k && !stack.isEmpty() && stack.peek() < c){
                stack.pop();
                count++;
            }
            
            stack.push(c);
        }
        
        StringBuilder sb = new StringBuilder();
        int targetLength = number.length() - k;
        
        for (int i = 0; i < targetLength; i++) {
            sb.append(stack.get(i));
        }

        return sb.toString();
    }
}