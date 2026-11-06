import java.util.*;

class Solution {
    public boolean solution(String[] phone_book) {
        
        Set<String> set = new HashSet<>();
        boolean[] hasLength = new boolean[21]; // int[i] -> 길이가 i인 단어가 있나?
        
        for(String phone : phone_book){
            set.add(phone);
            hasLength[phone.length()] = true;
        }
        
        for(String phone : phone_book){
            // phone이랑 같은 단어는 set에서 비교할 필요 x, 무조건 있을테니까
            for(int length = 1; length < phone.length(); length++){
                if(!hasLength[length]){
                    continue;
                }
                
                if(set.contains(phone.substring(0, length))){
                    return false;
                }
            }
        }
        
        
        return true;
    }
}