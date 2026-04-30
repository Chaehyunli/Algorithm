class Solution {
    public int solution(int n, int m, int[] section) {
        int answer = 0;
        int last_painted = 0; // 칠해진 구역의 번호 (1 ~ n)
        
        for(int s : section){
            if(last_painted < s){
                last_painted = s + m - 1; // 룰러로 페인트를 1번 칠함
                answer++;
            }
        }
        
        return answer;
    }
}