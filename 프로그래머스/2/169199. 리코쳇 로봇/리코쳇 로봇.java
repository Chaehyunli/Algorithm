import java.util.*;

class Solution {
    public int solution(String[] board) {
        int R = board.length;
        int C = board[0].length();
        
        int startX = 0, startY = 0;
        
        // 1. 시작 위치(R) 찾기
        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                if (board[r].charAt(c) == 'R') {
                    startX = r;
                    startY = c;
                    break;
                }
            }
        }
        
        // 상, 하, 좌, 우 방향 벡터
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[R][C];
        
        // 시작점 배열 형태로 큐에 삽입 (x, y, count) 및 방문 처리
        queue.offer(new int[]{startX, startY, 0});
        visited[startX][startY] = true;
        
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];
            int count = current[2];
            
            // 목표 지점(G)에 도달하면 현재까지의 이동 횟수 반환
            if (board[x].charAt(y) == 'G') {
                return count;
            }
            
            // 4방향 미끄러지기 탐색
            for (int i = 0; i < 4; i++) {
                int nx = x;
                int ny = y;
                
                while (true) {
                    int mx = nx + dx[i];
                    int my = ny + dy[i];
                    
                    // 범위를 벗어나거나 장애물(D)을 만나면 직진을 멈춤
                    if (mx < 0 || mx >= R || my < 0 || my >= C || board[mx].charAt(my) == 'D') {
                        break;
                    }
                    
                    nx = mx;
                    ny = my;
                }
                
                // 최종적으로 멈춘 위치가 방문하지 않은 곳이라면
                if (!visited[nx][ny]) {
                    visited[nx][ny] = true; // 방문 처리
                    // 큐에 새 배열 형태로 삽입
                    queue.offer(new int[]{nx, ny, count + 1});
                }
            }
        }
        
        // 목표 지점에 도달할 수 없는 경우 -1 반환
        return -1;
    }
}