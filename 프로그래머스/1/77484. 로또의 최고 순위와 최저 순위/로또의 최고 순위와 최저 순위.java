import java.util.*;

class Solution {
    public int[] solution(int[] lottos, int[] win_nums) {
        
        int matchCount = 0;
        int zeroCount = 0;

        Set<Integer> winSet = new HashSet<>();
        for (int num : win_nums) {
            winSet.add(num);
        }

        for (int num : lottos) {
            if (num == 0) {
                zeroCount++;
            } else if (winSet.contains(num)) {
                matchCount++;
            }
        }
        
        int[] rank = {6, 6, 5, 4, 3, 2, 1};
        
        int[] answer = new int[]{rank[matchCount + zeroCount], rank[matchCount]};
        
        return answer;
    }
}