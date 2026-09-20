import java.util.*;

class Solution {
    public int solution(int[][] routes) {
    
        // 나오는 지점을 기준으로 정렬
        Arrays.sort(routes, (a, b) -> a[1] - b[1]);
        
        int answer = 0;
        int checkpoint = -30001;
        
        for(int[] route : routes){
            if(route[0] > checkpoint){
                answer++;
                checkpoint = route[1];
            }
        }
        
        return answer;
    }
}