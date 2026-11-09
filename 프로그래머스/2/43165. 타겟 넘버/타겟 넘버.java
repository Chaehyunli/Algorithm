import java.util.*;

class Solution {
    
    // 현재 합이 currentSum일 때, index번부터 끝까지 + 또는 -를 선택해서 합을 target으로 만드는 경우의 수
    public int dfs(int[] numbers, int target, int index, int currentSum){
        if(index == numbers.length){
            
            if(currentSum == target){
                return 1;
            }
            
            return 0;
        }
        
        // 현재 숫자에 +를 붙이는 경우
        int add = dfs(numbers, target, index + 1, currentSum + numbers[index]);
        
        // 현재 숫자에 -를 붙이는 경우
        int substract = dfs(numbers, target, index + 1, currentSum - numbers[index]);
        
        return add + substract;
    }
    
    public int solution(int[] numbers, int target) {
        
        int answer = dfs(numbers, target, 0, 0);
        
        return answer;
    }
}