public class Solution {
    public int solution(int[] diffs, int[] times, long limit) {
        int low = 1;
        int high = 1;
        
        for (int d : diffs) {
            if (d > high) {
                high = d;
            }
        }
        
        int answer = 0;

        while (low <= high) {
            int mid = (low + high) / 2;
            
            // mid 레벨로 걸리는 시간 계산
            if (calculateTime(diffs, times, mid) <= limit) {
                answer = mid;   // 가능하면 저장하고 더 낮은 숙련도 탐색
                high = mid - 1;
            } else {
                low = mid + 1;  // 불가능하면 숙련도를 높임
            }
        }

        return answer;
    }

    private long calculateTime(int[] diffs, int[] times, int level) {
        long totalTime = 0;
        
        for (int i = 0; i < diffs.length; i++) {
            int diff = diffs[i];
            int timeCur = times[i];
            
            if (diff <= level) {
                // 숙련도가 충분한 경우
                totalTime += timeCur;
            } else {
                // 숙련도가 부족한 경우
                int timePrev = (i > 0) ? times[i - 1] : 0;
                int mistakes = diff - level;
                
                // 틀린 횟수만큼 (현재+이전) 시간 소요 + 마지막에 성공한 시간
                totalTime += mistakes * (timeCur + timePrev) + timeCur;
            }
        }
        
        return totalTime;
    }
}