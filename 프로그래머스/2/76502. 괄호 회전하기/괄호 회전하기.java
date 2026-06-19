import java.util.*;

class Solution {
    
    private boolean isValid(String str){
        Stack<Character> stack = new Stack<>();
        
        for(int i = 0; i < str.length(); i++){
            char c = str.charAt(i);
            
            if(c == '(' || c == '[' || c == '{'){
                stack.push(c);
            }else{
                
                if(stack.isEmpty()){
                    return false;
                }
                
                char top = stack.peek();
                
                if((c == ')' && top == '(') || 
                   (c == ']' && top == '[') || 
                   (c == '}' && top == '{')){
                    stack.pop();
                }else{
                    return false;
                }
            }
        }
        
        return stack.isEmpty();
    }
    
    public int solution(String s) {
        int answer = 0;
        int len = s.length();
        
        String extendedS = s + s;
        
        for (int i = 0; i < len; i++) {
            // i부터 원래 길이만큼 잘라내어 회전된 문자열 생성
            String rotated = extendedS.substring(i, i + len);
            
            if (isValid(rotated)) {
                answer++;
            }
        }
        
        return answer;
    }
}