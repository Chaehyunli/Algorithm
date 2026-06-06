import java.util.*;

class Solution {
    public int solution(int[] elements) {
        int n = elements.length;
        Set<Integer> sumSet = new HashSet<>();
        
        // 원형 수열을 표현하기 위해 배열을 2배 크기로 확장
        int[] extendedElements = new int[n * 2];
        for (int i = 0; i < n * 2; i++) {
            extendedElements[i] = elements[i % n];
        }
        
        for (int len = 1; len <= n; len++) {
            // 첫 번째 윈도우의 합 계산
            int currentSum = 0;
            for (int i = 0; i < len; i++) {
                currentSum += extendedElements[i];
            }
            sumSet.add(currentSum);

            for (int start = 1; start < n; start++) {
                // 이전 윈도우의 맨 앞 요소를 빼고, 새로운 맨 뒤 요소를 더함
                currentSum = currentSum - extendedElements[start - 1] + extendedElements[start + len - 1];
                sumSet.add(currentSum);
            }
        }
        
        return sumSet.size();
    }
}