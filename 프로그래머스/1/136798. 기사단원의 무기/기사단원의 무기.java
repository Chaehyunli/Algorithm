class Solution {
    public int solution(int number, int limit, int power) {
        int answer = 0;
        
        for (int i = 1; i <= number; i++) {
            int count = countDivisors(i);
            
            if (count > limit) {
                answer += power;
            } else {
                answer += count;
            }
        }

        return answer;
    }
    
    private int countDivisors(int n){
        int count = 0;
        
        for(int i = 1; (long)i * i <= n; i++){
            if(n % i == 0){
                if(i * i == n){
                    count++;
                }else{
                    count += 2;
                }
            }
        }
        
        return count;
    }
}