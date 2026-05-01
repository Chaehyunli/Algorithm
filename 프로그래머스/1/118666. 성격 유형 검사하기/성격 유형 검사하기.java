import java.util.Arrays;
import java.util.List;

class Solution {
    public String solution(String[] survey, int[] choices) {
        String[] grade1 = {"R", "C", "J", "A"};
        String[] grade2 = {"T", "F", "M", "N"};
        
        int[] score = new int[4];
        
        int[] dic = {0, -3, -2, -1, 0, 1, 2, 3}; // 0번 인덱스는 버림

        // 설문 데이터 처리
        for (int i = 0; i < survey.length; i++) {
            String s = survey[i];
            int choice = choices[i];
            
            // 현재 질문이 몇 번째 지표(R, C, J, A)인지 찾기
            int idx = -1;
            for (int j = 0; j < 4; j++) {
                if (s.contains(grade1[j])) {
                    idx = j;
                    break;
                }
            }

            // 질문의 순서가 뒤집혀 있는지 확인 (예: "TR")
            int currentScore = dic[choice];
            if (s.startsWith(grade2[idx])) { 
                // 첫 글자가 grade2(T, F, M, N)라면 점수 부호를 반대로
                currentScore *= -1;
            }
            
            score[idx] += currentScore;
        }

        StringBuilder answer = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            // 점수가 0보다 작거나 같으면 grade1 (R, C, J, A)
            // 0보다 크면 grade2 (T, F, M, N)
            if (score[i] <= 0) {
                answer.append(grade1[i]);
            } else {
                answer.append(grade2[i]);
            }
        }

        return answer.toString();
    }
}