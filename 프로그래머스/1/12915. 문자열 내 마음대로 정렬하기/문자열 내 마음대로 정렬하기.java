import java.util.Arrays;

class Solution {
    public String[] solution(String[] strings, int n) {
        String[] answer = new String[strings.length];
        
        // n번째 문자를 각 단어 앞에 붙임 (예: "car", n=1 -> "acar")
        for (int i = 0; i < strings.length; i++) {
            answer[i] = strings[i].charAt(n) + strings[i];
        }
        
        // 정렬하면 0번째 문자(n번째 글자) 기준 정렬, 동일할 시 원래 문자열 사전순 자동 정렬
        Arrays.sort(answer);
        
        // 앞에 붙였던 문자 제거
        for (int i = 0; i < answer.length; i++) {
            answer[i] = answer[i].substring(1);
        }
        
        return answer;
    }
}