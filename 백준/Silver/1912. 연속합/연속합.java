import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int N = sc.nextInt();

        int[] list = new int[N + 1];

        for(int i = 0; i < N; i++){
            int n = sc.nextInt();
            list[i] = n;
        }

        int[] dp = new int[N + 1]; // dp[i] = i 번째 수(list[i])를 마지막 원소로 포함하는 연속합 중 최대값
        
        dp[0] = list[0];
        int maxSum = dp[0];

        for(int i = 1; i < N; i++){
            dp[i] = Math.max(dp[i - 1] + list[i], list[i]); // list[i]를 이전 연속합에 추가한 것과, list[i]부터 새로 시작하는 것을 비교해서 더 큰 것을 선택함
            maxSum = Math.max(maxSum, dp[i]);
        }

        System.out.println(maxSum);
    }
    
}
