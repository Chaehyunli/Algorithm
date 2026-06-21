import java.util.*;

class Solution {
    public int solution(int x, int y, int n) {
        
        if(x == y){
            return 0;
        }
        
        int[] dp = new int[y + 1]; // d[i]는 숫자 i를 만들기 위한 연산 횟수
        
        Arrays.fill(dp, 1000001);
        
        dp[x] = 0;
        
        for(int i = x; i <= y; i++){
            
            if(i + n <= y){
                dp[i + n] = Math.min(dp[i + n], dp[i] + 1);
            }
            
            if(i * 2 <= y){
                dp[i * 2] = Math.min(dp[i * 2], dp[i] + 1);
            }
            
            if(i * 3 <= y){
                dp[i * 3] = Math.min(dp[i * 3], dp[i] + 1);
            }
        }
        
        if(dp[y] == 1000001){
            return -1;
        }else{
            return dp[y];
        }
    }
}