import java.util.*;

public class Main {

    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int K = scanner.nextInt();
        int[][] grid = new int[N + 1][N + 1];

        // dp[k][r][c] : 끝나는 칸의 좌표가 (r, c)이고, 길이가 k인 등산로의 인접한 높이차의 최댓값의 최솟값
        int[][][] dp = new int[K + 1][N + 1][N + 1];

        for (int i = 1; i <= N; i++){
            for (int j = 1; j <= N; j++){
                grid[i][j] = scanner.nextInt();
                for(int k = 2; k <= K; k++){
                    dp[k][i][j] = Integer.MAX_VALUE;
                }
            }
        }

        // Please write your code here.

        // 길이가 k인 등산로 중에서 인전한 높이차의 최대값의 최솟값 -> dp[k][][] 의 최솟값
        // Math.min에서 고른 1칸 이전의 칸이 grid[r][c] 보다 작아야함.
        // dp[k][r][c] = Math.min(dp[k - 1][r - 1][c], grid[r][c] - grid[r - 1][c])

        for(int r = 1; r <= N; r++){
            for(int c = 1; c <= N; c++){
                dp[1][r][c] = 0; // 초기값
            }
        }

        for(int i = 2; i <= K; i++){
            for(int r = 1; r <= N; r++){
                for(int c = 1; c <= N; c++){   
                    for(int d = 0; d < 4; d++){
                        if(r + dx[d] > N || r + dx[d] <=0 || c + dy[d] > N || c + dy[d] <=0){
                            continue;
                        }
                        int nr = r + dx[d];
                        int nc = c + dy[d];

                        if(grid[r][c] > grid[nr][nc]){
                            // 이전 경로의 최대 높이차와 이번 이동의 높이차 중 최댓값
                            int currentMax = Math.max(dp[i - 1][nr][nc], grid[r][c] - grid[nr][nc]);
                            
                            // 사방에서 오는 경로들 중 최솟값을 골라 현재 칸의 dp[i][r][c]에 갱신
                            dp[i][r][c] = Math.min(dp[i][r][c], currentMax);
                        }
                    }
                }
            }
        }

        int answer = Integer.MAX_VALUE;

        for (int r = 1; r <= N; r++) {
            for (int c = 1; c <= N; c++) {
                answer = Math.min(answer, dp[K][r][c]);
            }
        }

        System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);

    }
}