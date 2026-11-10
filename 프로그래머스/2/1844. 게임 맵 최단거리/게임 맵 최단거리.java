import java.util.*;

class Solution {
    
    static int[] dx = new int[]{-1, 1, 0, 0};
    static int[] dy = new int[]{0, 0, -1, 1};
    static boolean[][] isVisited;
    
    public int solution(int[][] maps) {
        
        int N = maps.length;
        int M = maps[0].length;
        
        Queue<int[]> queue = new LinkedList<>();
        isVisited = new boolean[N][M];
        
        queue.offer(new int[]{0, 0, 1}); // 출발점은 0,0 / 도착점은 N-1, M-1
        isVisited[0][0] = true;
        
        while(!queue.isEmpty()){
            int[] current = queue.poll();
            int cx = current[0];
            int cy = current[1];
            int distance = current[2];
            
            if(cx == N - 1 && cy == M - 1){
                return distance;
            }
            
            for(int i = 0; i < 4; i++){
                int nx = cx + dx[i];
                int ny = cy + dy[i];
                
                if(nx < 0 || nx >= N || ny < 0 || ny >= M){
                    continue;
                }
                
                if(!isVisited[nx][ny] && maps[nx][ny] == 1){
                    isVisited[nx][ny] = true;
                    queue.offer(new int[]{nx, ny, distance + 1});
                }
            }
        }

        return -1;
    }
}