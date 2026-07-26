class Solution {
    public int solution(int n) {
        
        int[] dp = new int[60001]; // 가로의 길이 n인 직사각형을 채우는 방법의 수
        
        dp[1] = 1;
        dp[2] = 2;
        
        for(int i = 3; i <= n; i++){
            dp[i] = (dp[i-1] + dp[i-2]) % 1000000007;
        }
            
        return dp[n];
    }
}