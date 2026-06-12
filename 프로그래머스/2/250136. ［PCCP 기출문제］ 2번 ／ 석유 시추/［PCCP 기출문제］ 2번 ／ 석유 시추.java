import java.util.*;

class Solution {
    
    private boolean[][] visited;
    private int[][] groupMap; // 각 칸이 몇번째 덩어리인지
    
    // Key: 덩어리 번호, Value: 덩어리 크기
    private Map<Integer, Integer> groupSizeMap = new HashMap<>();
    
    private static final int[] dx = {-1, 1, 0, 0};
    private static final int[] dy = {0, 0, -1, 1};
    
    private int height;
    private int width;
    
    // startX, startY에서 bfs로 groupId 번호를 매김
    private void bfs(int startX, int startY,int groupId, int[][] land){
        Queue<int[]> queue = new LinkedList<>();
        
        queue.add(new int[]{startX, startY});
        visited[startX][startY] = true;
        
        int areaSize = 0;
        
        while(!queue.isEmpty()){
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];
            
            groupMap[x][y] = groupId;
            areaSize++;
            
            for(int i = 0; i < 4; i++){
                int nx = x + dx[i];
                int ny = y + dy[i];
                
                if(nx >= 0 && nx < height && ny >= 0 && ny < width){
                    int nextId = nx * width + ny;
                    
                    if(land[nx][ny] == 1 && !visited[nx][ny]){
                        queue.offer(new int[]{nx, ny});
                        visited[nx][ny] = true;
                    }
                }
            }
        }
        
        // 덩어리의 크기를 기록하고, 맵에 덩어리 번호를 마킹
        groupSizeMap.put(groupId, areaSize);
    }
    
    public int solution(int[][] land) {
        
        height = land.length;       // 행의 개수 (세로)
        width = land[0].length;     // 열의 개수 (가로)
        
        visited = new boolean[height][width];
        groupMap = new int[height][width];
        
        int groupId = 1;
        
        // 모든 석유 덩어리를 찾아서 번호를 매기고 크기를 구함
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                if(land[i][j] == 1 && !visited[i][j]){
                    bfs(i, j, groupId, land);
                    groupId++; // 다음 덩어리는 새로운 번호 부여
                }
            }
        }
        
        int maxOil = 0;
        
        for(int j = 0; j < width; j++) {
            Set<Integer> hitGroups = new HashSet<>(); // 이번 열에서 만난 덩어리 번호들
            int currentOil = 0;
            
            for(int i = 0; i < height; i++) {
                if(groupMap[i][j] > 0) { // 덩어리 번호가 마킹되어 있으면
                    hitGroups.add(groupMap[i][j]);
                }
            }
            
            // 만난 모든 덩어리의 크기를 합산
            for(int id : hitGroups) {
                currentOil += groupSizeMap.get(id);
            }
            
            // 최댓값 갱신
            maxOil = Math.max(maxOil, currentOil);
        }
        
        return maxOil;
    }
}