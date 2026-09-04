import java.util.*;

class Solution {
    public int solution(int[][] triangle) {
        
        int floor = triangle.length;

        int[][] dp = new int[floor][floor]; // dp[0][0] = 7 임
        
        int answer = 0;
        dp[0][0] = triangle[0][0];
        
        for(int i = 1; i < floor; i++){
            for(int j = 0; j <= i; j++){
                // [0][0], [1][0], [1][1] ...
                if (j == 0) {
                    // 맨 왼쪽 칸: 바로 위(오른쪽 대각선)에서만 내려옴
                    dp[i][j] = dp[i - 1][j] + triangle[i][j];
                } else if (j == i) {
                    // 맨 오른쪽 칸: 바로 왼쪽 위 대각선에서만 내려옴
                    dp[i][j] = dp[i - 1][j - 1] + triangle[i][j];
                } else {
                    // 중간 칸: 왼쪽 위와 오른쪽 위 중 더 큰 값 선택
                    dp[i][j] = Math.max(dp[i - 1][j - 1], dp[i - 1][j]) + triangle[i][j];
                }
            }
        }
        
        for(int i = 0; i < floor; i++){
            answer = Math.max(answer, dp[floor - 1][i]);
        }
        
        return answer;
    }
}