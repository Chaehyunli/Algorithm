import java.util.*;

public class Main {

    static Map<Integer, Perfume> map;

    static class Perfume{
        int id; // 향수의 번호
        int smell; // 향수의 향

        public Perfume(int id, int smell){
            this.id = id;
            this.smell = smell;
        }
    }

    public static void main(String[] args) {
        // Please write your code here.
        Scanner sc = new Scanner(System.in);

        StringBuilder sb = new StringBuilder();

        int Q = sc.nextInt(); // 작업의 수
        map = new HashMap<>();
        int idx = 0;

        for(int q = 0; q < Q; q++){
            int command = sc.nextInt();

            switch(command){
                case 1: // 향료 준비
                    int N = sc.nextInt(); // 향수의 수
                    idx = N + 1;

                    for(int i = 1; i <= N; i++){
                        int value = sc.nextInt();
                        map.put(i, new Perfume(i, value));
                    }

                    break;
            
                case 2: // 향료 추가
                    int value = sc.nextInt();
                        map.put(idx, new Perfume(idx, value));
                        idx++;

                    break;

                case 3: // 항료 폐기
                    int targetIdx = sc.nextInt();

                    if(map.containsKey(targetIdx)){
                        // System.out.println(map.get(targetIdx).smell);
                        sb.append(map.get(targetIdx).smell).append("\n");
                        map.remove(targetIdx);
                    }else{
                        sb.append(-1).append("\n");
                        // System.out.println(-1);
                    }

                    break;

                case 4: // 블렌딩
                    int K4 = sc.nextInt(); // 향도의 합

                    // System.out.println(getDp(K4));
                    // sb.append(getDp1(K4)).append("\n");
                    sb.append(getDp2(K4)).append("\n");

                    break;

                case 5: // 향수 구성
                    // 합
                    int K5 = sc.nextInt();
                    // System.out.println("case5");

                    int size = map.size();
                    int[] smells = new int[size];
                    long count = 0;
                    int index = 0;

                    for (Perfume perfume : map.values()) {
                        smells[index++] = perfume.smell;
                    }

                    Arrays.sort(smells);

                    long invalid = 0;

                    for(int i = 0; i < size; i++){
                        long required = (long) K5 - smells[i];

                        // middle + base < required 인 경우의 수
                        int right = size - 1;

                        for(int left = 0; left < size; left++){
                            while(right >= 0 && (long) smells[left] + smells[right] >= required){
                                right--;
                            }

                            // right은 현재 조건을 만족하지 못하는 가장 큰 인덱스임
                            invalid += right + 1; 
                        }
                    }

                    count = (long) size * size * size - invalid;

                    // for(Perfume top : map.values()){
                    //     for(Perfume middle : map.values()){
                    //         int sum = top.smell + middle.smell;
                    //         for(Perfume bottom : map.values()){
                    //             if(top.smell + middle.smell + bottom.smell >= K5){
                    //                 count++;
                    //             }
                    //         }
                    //     }
                    // }

                    sb.append(count).append("\n");
                    // System.out.println(count);

                    break;
            }
        }

        System.out.print(sb.toString());
        
    }

    static int getDp1(int K){
        int[] dp = new int[K + 1]; // dp[N] : 향도의 합이 정확히 N이 되는데, 필요한 향로의 최소 개수

        Arrays.fill(dp, 1000000000);
        dp[0] = 0;

        for(Perfume perfume : map.values()){
            int p = perfume.smell;

            // 하나의 향수를 무한으로 사용 가능하기 때문에 정순으로
            for (int i = p; i <= K; i++) {
                // 초기에 채워놓은 1000000000이 아닐 때만 진행하기 때문에 정확히 K일 때만 업데이트
                if (dp[i - p] != 1000000000) {
                    dp[i] = Math.min(dp[i], dp[i - p] + 1);
                }
            }
        }

        if(dp[K] != 1000000000){
            return dp[K];
        }

        return -1;
    }

    static int getDp2(int K) {
        List<Perfume> perfumes = new ArrayList<>(map.values());
        
        int size = map.size();
        int INF = 1000000000;

        // dp[i][sum]
        // = 앞에서 i개의 향료만 사용해서
        //   향도의 합을 정확히 sum으로 만드는 최소 향료 개수
        int[][] dp = new int[size + 1][K + 1];

        // "정확히 sum 만들기"이므로 처음에는 모두 불가능(INF)
        for (int i = 0; i <= size; i++) {
            Arrays.fill(dp[i], INF);
        }

        // 향료를 하나도 사용하지 않고 합 0을 만드는 방법은 0개
        dp[0][0] = 0;

        for (int i = 0; i < size; i++) {
            int smell = perfumes.get(i).smell;

            for (int sum = 0; sum <= K; sum++) {
                // i + 1번째 향료를 사용하지 않는 경우
                dp[i + 1][sum] = dp[i][sum];

                // i + 1번째 향료를 하나 더 사용하는 경우
                if (sum >= smell && dp[i + 1][sum - smell] != INF) {
                    dp[i + 1][sum] = Math.min(
                        dp[i][sum],        // i번째 향료를 안 씀
                        dp[i + 1][sum - smell] + 1 // i번째 향료를 또 사용
                    );
                }
            }
        }

        return dp[size][K] == INF ? -1 : dp[size][K];
    }
}