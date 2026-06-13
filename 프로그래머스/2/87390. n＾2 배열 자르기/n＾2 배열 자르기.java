class Solution {
    public int[] solution(int n, long left, long right) {
        
        int size = (int) (right - left + 1);
        int[] answer = new int[size];
        
        int idx = 0;
        for (long i = left; i <= right; i++) {
            long row = i / n;
            long col = i % n;
            
            // row와 col 중 더 큰 값에 1을 더한 게 해당 칸의 숫자
            answer[idx] = (int) Math.max(row, col) + 1;
            idx++;
        }
        
        return answer;
    }
}