class Solution {
    
    private long work(long n){
        if(n % 2 == 0){
            return n / 2;
        }else{
            return n * 3 + 1;
        }
    }
    
    public int solution(int num) {
        if(num == 1){
            return 0;
        }
        
        long n = num;
        int count = 0;
        
        while(n != 1 && count < 500){
            count++;
            n = work(n);
        }
        
        if(count >= 500){
            return -1;
        }
        
        return count;
    }
}