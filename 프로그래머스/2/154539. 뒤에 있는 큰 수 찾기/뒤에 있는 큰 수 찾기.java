import java.util.*;

class Solution {
    public int[] solution(int[] numbers) {
        int n = numbers.length;
        int[] answer = new int[n];
    
        Arrays.fill(answer, -1);
        
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < n; i++) {
            
            while (!stack.isEmpty() && numbers[stack.peek()] < numbers[i]) {
                int index = stack.pop();
                // 그 인덱스 자리에 현재 숫자를 '뒷 큰수'로 저장합니다.
                answer[index] = numbers[i];
            }
            
            stack.push(i);
        }
        
        return answer;
    }
}