import java.util.*;

class Solution {
    public int solution(int[] nums) {
        // 가져갈 수 있는 최대 마리 수 (N / 2)
        int maxPick = nums.length / 2;
        
        // HashSet을 이용해 폰켓몬 종류의 중복을 제거
        HashSet<Integer> pokemonTypes = new HashSet<>();
        for (int num : nums) {
            pokemonTypes.add(num);
        }
        
        // 종류 수와 가져갈 수 있는 마리 수 중 더 작은 값을 반환
        return Math.min(maxPick, pokemonTypes.size());
    }
}