import java.util.*;

class Solution {
    
    private int minFatigue = Integer.MAX_VALUE;
    
    private int mine(int pick, String mineral){
        
        // 다이아몬드 곡갱이
        if(pick == 0){
            return 1;
        }
        
        // 철 곡갱이
        if(pick == 1){
            if(mineral.equals("diamond")){
                return 5;
            }
            
            return 1;
        }
        
        if(pick == 2){
            if(mineral.equals("diamond")){
                return 25;
            }
            
            if(mineral.equals("iron")){
                return 5;
            }
            
            return 1;
        }
        
        return 0;
    }
    
    private void dfs(int mineralIdx, int currentFatigue, int[] picks, String[] minerals) {
        
        // 광산을 다 팠거나, 사용할 수 있는 곡괭이가 없을 때
        if (mineralIdx >= minerals.length || (picks[0] == 0 && picks[1] == 0 && picks[2] == 0)) {
            // 지금까지 쌓인 피로도 중 최솟값 갱신
            minFatigue = Math.min(minFatigue, currentFatigue);
            return;
        }
        
        // 현재까지 구한 최솟값보다 피로도가 이미 커졌다면 더 이상 탐색할 필요 없음
        if (currentFatigue >= minFatigue) {
            return;
        }
        
        // 0: 다이아, 1: 철, 2: 돌 곡괭이를 선택
        for (int p = 0; p < 3; p++) {
            if (picks[p] > 0) { 
                
                picks[p]--;
                int tempFatigue = 0;
                int nextIdx = mineralIdx;
                
                // 광물 5개 캐기
                for (int i = 0; i < 5; i++) {
                    if (nextIdx >= minerals.length) break;
                    
                    // 피로도 누적
                    tempFatigue += mine(p, minerals[nextIdx]);
                    nextIdx++;
                }
                
                // 다음 곡괭이를 선택
                dfs(nextIdx, currentFatigue + tempFatigue, picks, minerals);
                
                // 백트래킹
                picks[p]++;
            }
        }
    }
    
    public int solution(int[] picks, String[] minerals) {
        
        // 광물 인덱스 0, 현재 피로도 0, 곡괭이 배열, 광물 배열
        dfs(0, 0, picks, minerals);
        
        return minFatigue;
    }
}