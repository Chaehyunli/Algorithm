import java.util.*;

class Solution {
    public int[] solution(String s) {
        
        // 양 끝의 대괄호 쌍을 고려하여 숫자와 쉼표만 남기도록 정제
        // "{{4,2,3},{3}}" -> "4,2,3,3"
        String cleaned = s.substring(2, s.length() - 2).replace("},{", ",");
        String[] numbers = cleaned.split(",");
        
        // HashMap으로 각 숫자의 등장 횟수를 카운트
        Map<Integer, Integer> counts = new HashMap<>();
        
        for (String numStr : numbers) {
            int num = Integer.parseInt(numStr.trim());
            counts.put(num, counts.getOrDefault(num, 0) + 1);
        }
        
        // HashMap의 key를 value 기준으로 내림차순 정렬
        List<Integer> keyList = new ArrayList<>(counts.keySet());
        
        // 등장 횟수(value)가 큰 순서대로 정렬
        keyList.sort((a, b) -> counts.get(b) - counts.get(a));
        
        // List를 int[] 배열로 변환
        int[] answer = new int[keyList.size()];
        for (int i = 0; i < keyList.size(); i++) {
            answer[i] = keyList.get(i);
        }
        
        return answer;
    }
}