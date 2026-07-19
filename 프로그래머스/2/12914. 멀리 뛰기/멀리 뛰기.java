class Solution {
    public long solution(int n) {

        if (n == 1) return 1;
        if (n == 2) return 2;
        
        // DP -> n번째 인덱스까지 사용하기 위해 n+1 크기
        long[] dp = new long[n + 1];
        
        dp[1] = 1;
        dp[2] = 2;
        
        for (int i = 3; i <= n; i++) {
            // 오버플로우 방지
            dp[i] = (dp[i - 1] + dp[i - 2]) % 1234567;
        }
        
        return dp[n];
    }
}