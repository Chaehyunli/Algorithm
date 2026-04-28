import java.util.*;

public class Solution {
    public String solution(String s, String skip, int index) {
        StringBuilder answer = new StringBuilder();
        
        // 1. 유효한 알파벳 리스트 생성
        List<Character> validChars = new ArrayList<>();
        for (char c = 'a'; c <= 'z'; c++) {
            if (skip.indexOf(c) == -1) { // skip에 없는 문자만 추가
                validChars.add(c);
            }
        }
        
        // 2. 변환 로직
        for (char c : s.toCharArray()) {
            int currentIdx = validChars.indexOf(c);
            int newIdx = (currentIdx + index) % validChars.size();
            answer.append(validChars.get(newIdx));
        }
        
        return answer.toString();
    }
}