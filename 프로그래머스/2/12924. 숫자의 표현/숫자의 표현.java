class Solution {
    public int solution(int n) {
        // 자연수 n을 연속된 자연수의 합으로 나타내는 방법의 수 = n의 홀수 약수의 개수
        int answer = 0;
        
        for (int i = 1; i <= n; i += 2) {
            if (n % i == 0) {
                answer++;
            }
        }
        
        return answer;
    }
}