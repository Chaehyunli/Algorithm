public class Solution {
    public int solution(int n) {
        int ans = 0;
        
        while (n > 0) {
            if (n % 2 == 0) {
                // 짝수면 순간이동 (비용 추가 없음)
                n /= 2;
            } else {
                // 홀수면 한 칸 점프 (건전지 +1)
                n -= 1;
                ans++;
            }
        }
        
        return ans;
    }
}