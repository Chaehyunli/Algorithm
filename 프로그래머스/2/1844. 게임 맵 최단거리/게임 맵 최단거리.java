import java.util.*;

class Solution {
    
    static int[][] minDistanceGrid;
    static boolean[][] visited;
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};
    static int N;
    static int M;
    
    static int minDistance = Integer.MAX_VALUE;
    
    private int bfs(int[] start, int[][] maps){
        
        Queue<int[]> queue = new LinkedList<>();
        
        queue.add(new int[]{start[0], start[1], 1});
        visited[start[0]][start[1]] = true;
        
        while(!queue.isEmpty()){
            int[] current = queue.poll();
            int cx = current[0];
            int cy = current[1];
            int dist = current[2];
            
            if(cx == N - 1 && cy == M - 1) {
                return dist;
            }
        
            for(int i = 0; i < 4; i++){
                int nx = cx + dx[i];
                int ny = cy + dy[i];
                
                if(nx >= 0 && nx < N && ny >= 0 && ny < M){
                    if(!visited[nx][ny] && maps[nx][ny] == 1){
                        visited[nx][ny] = true;
                        queue.add(new int[]{nx, ny, dist + 1});
                    }
                }
            }
        }
        
        return -1;
    }
    
    private int dfs1(int[] start, int[][] maps){
        
        minDistance = Integer.MAX_VALUE;
        minDistanceGrid = new int[N][M];
        
        for(int i = 0; i < N; i++) {
            Arrays.fill(minDistanceGrid[i], Integer.MAX_VALUE);
        }
        
        Stack<int[]> stack = new Stack<>();
        
        stack.push(new int[]{start[0], start[1], 1});
        minDistanceGrid[start[0]][start[1]] = 1;
        
        while(!stack.isEmpty()){
            int[] current = stack.pop();
            int cx = current[0];
            int cy = current[1];
            int dist = current[2];
            
            if(cx == N - 1 && cy == M - 1) {
                minDistance = Math.min(minDistance, dist);
                continue;
            }
        
            if (dist >= minDistance) continue;
            
            for(int i = 0; i < 4; i++){
                int nx = cx + dx[i];
                int ny = cy + dy[i];
                
                if(nx >= 0 && nx < N && ny >= 0 && ny < M){
                    if(maps[nx][ny] == 1){
                        
                        if(dist + 1 < minDistanceGrid[nx][ny]) {
                            minDistanceGrid[nx][ny] = dist + 1; // 갱신
                            stack.push(new int[]{nx, ny, dist + 1});
                        }
                    }
                }
            }
        }
        
        return minDistance == Integer.MAX_VALUE ? -1 : minDistance;
    }
    
    private int dfs2(int[] start, int[][] maps) {
        
        minDistance = Integer.MAX_VALUE;
        
        visited[start[0]][start[1]] = true;
        dfsRecursion(start[0], start[1], 1, maps);
        
        return minDistance == Integer.MAX_VALUE ? -1 : minDistance;
    }

    // 재귀
    private void dfsRecursion(int cx, int cy, int dist, int[][] maps) {
        
        if (cx == N - 1 && cy == M - 1) {
            minDistance = Math.min(minDistance, dist);
            return;
        }

        for (int i = 0; i < 4; i++) {
            int nx = cx + dx[i];
            int ny = cy + dy[i];

            if (nx >= 0 && nx < N && ny >= 0 && ny < M) {
                if (!visited[nx][ny] && maps[nx][ny] == 1) {
                    
                    visited[nx][ny] = true;
                    dfsRecursion(nx, ny, dist + 1, maps);
                    visited[nx][ny] = false; // 백트레킹
                }
            }
        }
    }
    
    public int solution(int[][] maps) {
        
        N = maps.length;
        M = maps[0].length;
        
        visited = new boolean[N][M];
        
        int answer = bfs(new int[]{0,0}, maps);
        // int answer = dfs1(new int[]{0,0}, maps); // 효율성 테스트 통과 x
        // int answer = dfs2(new int[]{0,0}, maps); // 백트레킹으로 효율성 테스트 통과 x
    
        return answer;
    }
}