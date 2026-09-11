import java.util.*;

class Solution {
    public int solution(int m, int n, int[][] puddles) {
        
        int[][] grid = new int[n + 1][m + 1];
        boolean[][] isPuddle = new boolean[n + 1][m + 1];
        
        // 웅덩이 위치 저장
        if (puddles != null) {
            for (int[] puddle : puddles) {
                // length가 2 미만인 예외 데이터([[]] 등) 방지
                if (puddle == null || puddle.length < 2) continue;
                
                int px = puddle[0];
                int py = puddle[1];
                
                if (py < 1 || py > n || px < 1 || px > m) continue;
                
                isPuddle[py][px] = true;
            }
        }
        
        if (isPuddle[1][1]) return 0;
        
        // 시작점 설정 (집 위치)
        grid[1][1] = 1;
        
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                // 시작점은 이미 1로 설정되었으므로 건너뜀
                if (i == 1 && j == 1) continue;
                
                // 웅덩이인 경우 경로 수 0
                if (isPuddle[i][j]) {
                    grid[i][j] = 0;
                    continue;
                }
                
                // 위쪽 칸과 왼쪽 칸의 경로 수 합산 (웅덩이는 0 처리되었으므로 단순 가산 가능)
                int top = grid[i - 1][j];
                int left = grid[i][j - 1];
                
                // 1,000,007 모듈러 연산 적용
                grid[i][j] = (top + left) % 1000000007;
            }
        }
        
        // System.out.println(Arrays.deepToString(grid));
        
        return grid[n][m];
    }
}