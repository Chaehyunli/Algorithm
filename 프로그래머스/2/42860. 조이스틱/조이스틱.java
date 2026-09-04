class Solution {
    public int solution(String name) {
        int answer = 0;
        int length = name.length();
        int move = length - 1;
        
        for(int i = 0; i < length; i++){
            char current = name.charAt(i);
            
            // char 간 - 연산 시, 아스키코드 차이가 int로 나옴
            answer += Math.min(current - 'A', 'Z' - current + 1);
            
            // 연속된 'A' 처리
            int next = i + 1;
            while (next < length && name.charAt(next) == 'A') {
                next++; // 연속된 A의 끝 위치 찾기
            }
            
            // 오른쪽으로 가는 방법
            move = Math.min(move, i * 2 + (length - next));
            // 왼쪽으로 가는 방법
            move = Math.min(move, (length - next) * 2 + i);
        }
        
        return answer + move;
    }
}