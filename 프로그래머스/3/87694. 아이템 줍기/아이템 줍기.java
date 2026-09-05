import java.util.*;

class Solution {
    public int solution(int[][] rectangle, int characterX, int characterY, int itemX, int itemY) {
        
        int[][] map = new int[102][102]; // ㄷ자 모양에서 BFS를 돌릴 때, 거리가 1이면 잘못된 경로로 이동할 수 있으므로, 2배수
         
        // 겹친 사각형의 테두리를 1로 채움
        for(int[] r : rectangle){
            // ㄷ자 모양에서 BFS를 돌릴 때, 거리가 1이면 잘못된 경로로 이동할 수 있으므로, 2배수
            int x1 = r[0] * 2;
            int y1 = r[1] * 2;
            int x2 = r[2] * 2;
            int y2 = r[3] * 2;
            
            // 테투리 1, 내부 2
            for(int i = x1; i <= x2; i++){
                for(int j = y1; j <= y2; j++){
                    if(i == x1 || i == x2 || j == y1 || j == y2){
                        if(map[i][j] != 2){ // 다른 사각형의 내부가 아니면
                            map[i][j] = 1; // 테두리는 1로 채움
                        }
                    }else{
                        map[i][j] = 2; // 안쪽 내부는 2으로 채움
                    }
                }
            }
        }
        
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] isVisited = new boolean[102][102]; // 아까 2배수 했기 때문에 51 * 2
        
        int[] dx = new int[]{0, 0, -1, 1};
        int[] dy = new int[]{-1, 1, 0, 0};
        
        queue.offer(new int[]{characterX * 2, characterY * 2, 0});
        isVisited[characterX * 2][characterY * 2] = true;
        
        while(!queue.isEmpty()){
            int[] current = queue.poll();
            int cx = current[0];
            int cy = current[1];
            int distance = current[2];
            
            if(cx == itemX * 2 && cy == itemY * 2){
                return distance / 2; // 아까 2배수 했기 때문에 전체 거리를 다시 2로 나누어서 반환
            }
            
            for(int i = 0; i < 4; i++){
                
                int nx = cx + dx[i];
                int ny = cy + dy[i];
                
                if(nx >= 2 && nx <=100 && ny >= 2 && ny <=100){
                    if(!isVisited[nx][ny] && map[nx][ny] == 1){
                        isVisited[nx][ny] = true;
                        queue.offer(new int[]{nx, ny, distance + 1});
                    }
                }
            }
        }
        
        return 0;
    }
}