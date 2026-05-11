class Solution {
    public int solution(String s) {
        int answer = 0;
        int i = 0;
        
        while (i < s.length()) {
            char x = s.charAt(i); // 첫 글자 설정
            int same = 0;         // x와 같은 글자 수
            int diff = 0;         // x와 다른 글자 수
            
            // 문자열 끝까지 탐색하면서 조건을 확인
            while (i < s.length()) {
                if (s.charAt(i) == x) {
                    same++;
                } else {
                    diff++;
                }
                
                i++; // 다음 글자로 이동
                
                // 두 횟수가 같아지는 순간 루프 탈출 후 분리
                if (same == diff) {
                    break;
                }
            }
            
            // 문자열 한 덩어리가 분리되었으므로 카운트 증가
            answer++;
        }
        
        return answer;
    }
}