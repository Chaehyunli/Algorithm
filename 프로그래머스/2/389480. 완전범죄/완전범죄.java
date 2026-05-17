import java.util.*;

class Solution {
    public int solution(int[][] info, int n, int m) {
        // 오버플로우 방지를 위한 충분히 큰 값 설정
        final int INF = 100_000;
        
        // dp[j] = B의 누적 흔적이 j일 때, A의 누적 흔적의 최솟값
        // B의 흔적은 m 미만이어야 하므로 크기를 m으로 설정합니다.
        int[] dp = new int[m];
        Arrays.fill(dp, INF);
        dp[0] = 0; // 초기 상태
        
        for (int[] item : info) {
            int aTrace = item[0];
            int bTrace = item[1];
            
            // 이번 물건을 반영할 새로운 DP 테이블
            int[] nextDp = new int[m];
            Arrays.fill(nextDp, INF);
            
            for (int j = 0; j < m; j++) {
                if (dp[j] == INF) continue;
                
                // 1. A가 훔치는 경우 (B의 흔적 j 유지, A의 흔적 추가)
                if (nextDp[j] > dp[j] + aTrace) {
                    nextDp[j] = dp[j] + aTrace;
                }
                
                // 2. B가 훔치는 경우 (B의 흔적이 j + bTrace로 증가, m 미만일 때만)
                if (j + bTrace < m) {
                    if (nextDp[j + bTrace] > dp[j]) {
                        nextDp[j + bTrace] = dp[j];
                    }
                }
            }
            // 테이블 갱신
            dp = nextDp;
        }
        
        // 모든 물건을 훔친 후, B의 흔적이 m 미만인 경우 중 A의 흔적 최솟값 찾기
        int ans = INF;
        for (int j = 0; j < m; j++) {
            if (dp[j] < ans) {
                ans = dp[j];
            }
        }
        
        // A의 흔적도 n 미만이어야 둘 다 경찰에 잡히지 않습니다.
        if (ans < n) {
            return ans;
        } else {
            return -1;
        }
    }
}