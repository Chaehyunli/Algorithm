import java.util.*;

class Solution {
    public int solution(int N, int number) {
        if (N == number) return 1;

        // dp[i] : N을 i개 사용해서 만들 수 있는 수들
        Set<Integer>[] dp = new HashSet[9];
        for (int i = 1; i <= 8; i++) {
            dp[i] = new HashSet<>();
        }

        int continuousN = 0;
        
        for (int i = 1; i <= 8; i++) {
            // N, NN, NNN... 형태 추가 (예: 5, 55, 555)
            continuousN = continuousN * 10 + N; // 5 -> 55 -> 555 ....
            dp[i].add(continuousN);

            // dp[j]와 dp[i - j]의 조합으로 dp[i] 채우기
            for (int j = 1; j < i; j++) {
                for (int op1 : dp[j]) {
                    for (int op2 : dp[i - j]) {
                        dp[i].add(op1 + op2); // 더하기
                        dp[i].add(op1 - op2); // 빼고
                        dp[i].add(op1 * op2); // 곱하기
                        
                        if (op2 != 0){
                            dp[i].add(op1 / op2); // 나누기
                        }
                    }
                }
            }

            // 목표 숫자가 완성되었으면 최소 사용 횟수인 i 반환
            if (dp[i].contains(number)) {
                return i;
            }
        }

        return -1; // 8번 이하로 만들 수 없는 경우
    }
}