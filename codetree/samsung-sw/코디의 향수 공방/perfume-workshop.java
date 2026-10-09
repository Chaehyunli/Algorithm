import java.util.*;

// 향수의 목록을 map에 넣어서 관리 1,2,3 단계 처리

// 4단계
//      - map에서 현재 사용 가능한 향수들을 ArrayList로 변환해서 무한 가방 문제

// 5단계
//      - top하나를 고정하고 미들, 베이스를 두 포인터로 구현

public class Main {

    static int Q;

    public static void main(String[] args) {
        // Please write your code here.
        Scanner sc = new Scanner(System.in);

        Q = sc.nextInt();

        // key : 향수의 번호, value : 향료
        Map<Integer, Integer> map = new HashMap<>();

        int indx = 1; // 향수의 번호

        while(Q > 0){

            int command = sc.nextInt();

            switch(command){
                case 1: {

                    int N = sc.nextInt();

                    for(int i = 0; i < N; i++){
                        int smell = sc.nextInt();
                        map.put(indx, smell);
                        indx++;
                    }
                    break;
                }

                case 2: {
                    int smell = sc.nextInt();
                    map.put(indx, smell);
                    indx++;

                    break;
                }

                case 3: {
                    int targetIdx = sc.nextInt();
                    if(map.containsKey(targetIdx)){
                        int smell = map.get(targetIdx);
                        System.out.println(smell);
                        map.remove(targetIdx);
                    }else{
                        System.out.println(-1);
                    }

                    break;
                }

                case 4: {
                    int maxK = sc.nextInt();
                    int size = map.size();

                    // dp[i][K] : i번 번호의 향수까지 해용해서 햡도 합이 정확히 K가 되는데 필요한 최소 개수(같은 향료 무한 사용 가능)
                    int[][] dp = new int[size + 1][maxK + 1];

                    for(int i = 0; i <= size; i++){
                        for(int j = 0; j <= maxK; j++){
                            dp[i][j] = 1000000000;
                        }
                    }

                    dp[0][0] = 0;

                    ArrayList<int[]> list = new ArrayList<>();

                    // map에서 하나씩 꺼내서 list에 넣음
                    for(int p : map.keySet()){
                        list.add(new int[]{p, map.get(p)});
                    }
                    
                    for(int i = 0; i < size; i++){
                        int smell = list.get(i)[1];

                        for (int sum = 0; sum <= maxK; sum++) {
                            // 현재 향료를 쓰지 않는 경우
                            dp[i + 1][sum] = dp[i][sum];
                        }

                        // 현재 항료를 사용하는 것을 시도(사용한 것이 개수가 더 적으면 반영)
                        for(int j = smell; j <= maxK; j++){

                            dp[i + 1][j] = Math.min(dp[i][j], dp[i + 1][j - smell] + 1);
                        }
                    }

                    if(dp[size][maxK] != 1000000000){
                        System.out.println(dp[size][maxK]);
                    }else{
                        System.out.println(-1);
                    }

                    break;
                }

                case 5: {

                    int minK = sc.nextInt(); // 세 항료의 합이 K 이상
                    int size = map.size();
                    long count = 0;

                    ArrayList<Integer> list = new ArrayList<>();

                    // map에서 하나씩 꺼내서 list에 넣음
                    for(int p : map.keySet()){
                        list.add(map.get(p));
                    }

                    list.sort(Comparator.naturalOrder());

                    // 향로 중복 사용 가능
                    for(int top = 0; top < size; top++){
                        long required = (long) minK - list.get(top);

                        int right = size - 1;

                        for(int left = 0; left < size; left++){

                            // right >= 0 을 먼저 안하면 list.get(right)에서 음수 인덱스에 접근할 수 있기 때문에 순서 중요!!
                            while(right >= 0 && (long) list.get(left) + list.get(right) >= required){
                                right--;
                            }

                            count += size - right - 1;
                        }
                    }

                    System.out.println(count);
                    

                    break;
                }
            }
            Q--;
        }
    }
}