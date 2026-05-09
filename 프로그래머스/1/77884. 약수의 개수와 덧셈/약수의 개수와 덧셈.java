class Solution {
    
    private boolean isPlus(int n){
        
        if(n < 0){
            return false;
        }
        
        int i = (int) Math.sqrt(n);
        
        if(i*i == n){
            return false;
        }else{
            return true;
        }
    }
    
    public int solution(int left, int right) {
        int answer = 0;
        
        for(int i = left; i <= right; i++){
            if(isPlus(i)){
                answer += i;
            }else{
                answer -= i;
            }
        }
        
        return answer;
    }
}