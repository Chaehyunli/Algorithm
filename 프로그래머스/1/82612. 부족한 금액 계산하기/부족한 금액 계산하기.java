class Solution {
    public long solution(int price, int money, int count) {
        
        long sum = 0;
    
        for(long i = 1; i <= count; i++){
            sum += i;
        }
    
        long total = price * sum;
        
        if(money - total < 0){
            return total - money;
        }else{
            return 0;
        }
    }
}