import java.util.*;

class Solution {
    public int solution(int[][] data, int col, int row_begin, int row_end) {
        
        Arrays.sort(data, (a,b) ->{
            if(a[col - 1] == b[col - 1]){
                return Integer.compare(b[0], a[0]);
                // return b[0] - a[0];
            }
            
            return Integer.compare(a[col - 1], b[col - 1]);
            // return a[col - 1] - b[col - 1];
        });
        
        int answer = 0;
        
        for (int i = row_begin; i <= row_end; i++) {
            int S_i = 0;
            
            for (int val : data[i - 1]) {
                S_i += val % i; // mod 연산 후 더하기
            }
            
            answer ^= S_i; // XOR 연산
        }
        
        return answer;
    }
}