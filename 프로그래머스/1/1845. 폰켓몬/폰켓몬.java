import java.util.*;

class Solution {
    public int solution(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        
        for(int num : nums){
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        
        int maxKind = map.size();
        int selectCount = nums.length / 2;
        
        return Math.min(maxKind, selectCount);
    }
}