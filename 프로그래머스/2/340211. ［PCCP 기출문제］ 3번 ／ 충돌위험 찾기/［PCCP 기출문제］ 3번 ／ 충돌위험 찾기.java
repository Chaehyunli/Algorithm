import java.util.*;

class Solution {
    public int solution(int[][] points, int[][] routes) {
        int answer = 0;
        
        // 인덱스: 시간(초), Map의 Key: "R,C" 형태의 문자열, Value: 방문한 로봇 수
        Map<String, Integer>[] timeMap = new HashMap[100001];
        for(int i = 0; i <= 100000; i++) {
            timeMap[i] = new HashMap<>();
        }
        
        // 전체 시간의 최댓값을 추적하기 위한 변수
        int maxTime = 0;
        
        for(int[] route : routes){
            
            int time = 0;
            
            int start = route[0]; // 출발 운송 포인트의 번호 
            int end = route[1]; // 도착 운송 포인트의 번호
            
            int[] start_xy = points[start - 1];
            
            int cur_r = start_xy[0];
            int cur_c = start_xy[1];
            
            // 0초일 때의 시작 위치 기록
            String pos = cur_r + "," + cur_c;
            timeMap[time].put(pos, timeMap[time].getOrDefault(pos, 0) + 1);
            
            // 경유지들을 순서대로 이동 (route[0] -> route[1] -> route[2] ...)
            for (int i = 1; i < route.length; i++) {
                int[] next_xy = points[route[i] - 1];
                int next_r = next_xy[0];
                int next_c = next_xy[1];
                
                // r 좌표부터 일치할 때까지 한 칸씩 이동
                while (cur_r != next_r) {
                    if (cur_r < next_r) cur_r++;
                    else cur_r--;
                    time++;
                    
                    pos = cur_r + "," + cur_c;
                    timeMap[time].put(pos, timeMap[time].getOrDefault(pos, 0) + 1);
                }
                
                // 그 다음 c 좌표가 일치할 때까지 한 칸씩 이동
                while (cur_c != next_c) {
                    if (cur_c < next_c) cur_c++;
                    else cur_c--;
                    time++;
                    
                    pos = cur_r + "," + cur_c;
                    timeMap[time].put(pos, timeMap[time].getOrDefault(pos, 0) + 1);
                }
            }
            // 가장 오래 걸린 로봇의 시간을 기록
            maxTime = Math.max(maxTime, time);
        }
        
        // 2. 기록된 timeMap을 돌면서 같은 시간대에 같은 좌표의 밸류가 2 이상인 경우 충돌 카운트
        for (int t = 0; t <= maxTime; t++) {
            for (int count : timeMap[t].values()) {
                if (count >= 2) {
                    answer++;
                }
            }
        }
        
        return answer;
    }
}