class Solution {
    boolean solution(String s) {
        
        int count = 0;

        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == 'p' || s.charAt(i) == 'P'){
                count ++;
            }else if(s.charAt(i) == 'y' || s.charAt(i) == 'Y'){
                count --;
            }
        }

        if(count == 0){
            return true;
        }else{
            return false;
        }
    }
}