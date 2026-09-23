class Solution {
    public long solution(int n, int[] times) {
        long left = 1;
        
        // times 배열에서 최댓값 찾기
        long maxTime = 0;
        for (int t : times) {
            maxTime = Math.max(t, maxTime);
        }
        
        // 가장 오래 걸리는 심사관 혼자 n명을 처리할 때
        long right = maxTime * n;
        long answer = right;

        while (left <= right) {
            long mid = (left + right) / 2; // 제한 시간
            
            long totalPeople = 0;
            
            for (int t : times) {
                totalPeople += mid / t; // 제한 시간 내에 각 심사권이 심사할 수 있는 사람들의 수를 터함
            }

            if (totalPeople >= n) {
                answer = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return answer;
    }
}