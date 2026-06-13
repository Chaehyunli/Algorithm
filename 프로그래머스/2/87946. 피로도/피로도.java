class Solution {

    private int maxDungeons = 0;
    private boolean[] visited;

    public int solution(int k, int[][] dungeons) {

        visited = new boolean[dungeons.length];
        
        // DFS 시작 (현재 피로도 k, 탐험한 던전 수 0)
        dfs(k, 0, dungeons);
        
        return maxDungeons;
    }

    private void dfs(int currentK, int count, int[][] dungeons) {
        // 매 단계마다 갱신된 탐험 수가 이전 최댓값보다 크면 업데이트
        maxDungeons = Math.max(maxDungeons, count);

        for (int i = 0; i < dungeons.length; i++) {
            
            if (!visited[i] && currentK >= dungeons[i][0]) {
                
                visited[i] = true; // 방문 처리
                
                // 피로도를 소모하고 던전 수를 1 증가시켜 재귀 호출
                dfs(currentK - dungeons[i][1], count + 1, dungeons);
                
                visited[i] = false; // 다른 경로 탐색을 위해 해제
            }
        }
    }
}