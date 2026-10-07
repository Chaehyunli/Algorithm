import java.util.*;

public class Main {

    // 보석 정보를 저장할 클래스
    static class Jewelry {
        int id;
        int weight;
        int value;
        boolean sold; // 판매 여부 플래그

        public Jewelry(int id, int weight, int value) {
            this.id = id;
            this.weight = weight;
            this.value = value;
            this.sold = false; // 기본값: 판매되지 않음
        }
    }

    // ==========================================
    // [디버깅용 함수] ArrayList 상태를 가시화하여 출력
    // ==========================================
    public static <T> void printArrayList(List<T> list, String title) {
        System.out.println("=== [DEBUG] " + title + " ===");

        if (list == null || list.isEmpty()) {
            System.out.println("(empty)");
        } else {
            for (int i = 0; i < list.size(); i++) {
                System.out.println("[" + i + "] " + list.get(i));
            }
        }

        System.out.println("==========================");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        if (!sc.hasNextInt()) return;
        int Q = sc.nextInt(); // 작업의 수

        // 보석 번호(ID)로 즉시 접근하기 위한 Map (O(1) 조회)
        Map<Integer, Jewelry> jewelryMap = new HashMap<>();
        int indexCount = 1; // 부여할 보석 번호 (1부터 시작)

        // case 5용 캐시: 보석 목록이 바뀌지 않았으면 정렬 결과를 재사용
        List<Integer> weights = new ArrayList<>();
        boolean dirty = true;

        for (int q = 0; q < Q; q++) {
            int task = sc.nextInt();

            switch (task) {
                case 1: { // (1) 보석 준비
                    int N = sc.nextInt();

                    for (int j = 0; j < N; j++) {
                        int w = sc.nextInt();
                        int v = sc.nextInt();

                        jewelryMap.put(
                            indexCount,
                            new Jewelry(indexCount, w, v)
                        );

                        indexCount++;
                    }

                    dirty = true;
                    break;
                }

                case 2: { // (2) 보석 입고
                    int w = sc.nextInt();
                    int v = sc.nextInt();

                    jewelryMap.put(
                        indexCount,
                        new Jewelry(indexCount, w, v)
                    );

                    indexCount++;
                    dirty = true;
                    break;
                }

                case 3: { // (3) 보석 판매
                    int sellNum = sc.nextInt();

                    // 보석이 존재하고, 아직 판매되지 않은 경우
                    if (jewelryMap.containsKey(sellNum)
                            && !jewelryMap.get(sellNum).sold) {

                        Jewelry j = jewelryMap.get(sellNum);
                        j.sold = true; // 판매 처리
                        dirty = true;

                        System.out.println(j.value);
                    } else {
                        System.out.println(-1);
                    }

                    break;
                }

                case 4: { // (4) 진열 (Knapsack DP)
                    int limitWeight = sc.nextInt();

                    // 현재 공방에 남아있는 보석만 수집
                    List<Jewelry> activeList = new ArrayList<>();

                    for (Jewelry j : jewelryMap.values()) {
                        if (!j.sold) {
                            activeList.add(j);
                        }
                    }

                    // [디버깅] 현재 남아있는 보석 목록 출력 확인
                    // printArrayList(activeList, "Active Jewelry List for Task 4");

                    // 1차원 DP 사용 시
                    // System.out.println(getDp1(activeList, limitWeight));

                    // 2차원 DP 사용 시
                    System.out.println(getDp2(activeList, limitWeight));

                    break;
                }

                case 5: { // (5) 세트 구성 (정렬 + 투 포인터)
                    int maxWeightGap = sc.nextInt();

                    // 보석 목록이 바뀐 경우에만 다시 수집하고 정렬
                    if (dirty) {
                        weights.clear();

                        for (Jewelry j : jewelryMap.values()) {
                            if (!j.sold) {
                                weights.add(j.weight);
                            }
                        }

                        Collections.sort(weights);
                        dirty = false;
                    }

                    // [디버깅] 정렬된 무게 리스트 확인
                    // printArrayList(weights, "Sorted Weights for Task 5");

                    long count = 0;
                    int left = 0;

                    for (int right = 0; right < weights.size(); right++) {
                        // 두 무게의 차이가 maxWeightGap보다 크면 left 포인터 이동
                        while (weights.get(right) - weights.get(left) > maxWeightGap) {
                            left++;
                        }

                        // right 위치의 보석과 세트를 만들 수 있는 보석의 개수 누적
                        count += (right - left);
                    }

                    System.out.println(count);
                    break;
                }
            }
        }

        sc.close();
    }

    static int getDp1(List<Jewelry> activeList, int limitWeight) {
        // dp[w]: 무게 한도 w일 때 얻을 수 있는 최대 가치
        int[] dp = new int[limitWeight + 1];

        for (Jewelry j : activeList) {
            // 중복 선택을 방지하기 위해 역순(limitWeight -> j.weight)으로 갱신
            for (int w = limitWeight; w >= j.weight; w--) {
                dp[w] = Math.max(
                    dp[w],
                    dp[w - j.weight] + j.value
                );
            }
        }

        return dp[limitWeight];
    }

    static int getDp2(List<Jewelry> activeList, int limitWeight) {
        int size = activeList.size();

        // dp[i][weight]
        // = 앞에서 i개의 보석만 사용해서
        //   무게가 weight 이하일 때 얻을 수 있는 최대 가치
        int[][] dp = new int[size + 1][limitWeight + 1];

        for (int i = 0; i < size; i++) {
            int weight = activeList.get(i).weight;
            int value = activeList.get(i).value;

            for (int w = 0; w <= limitWeight; w++) {
                // i + 1번째 보석을 사용하지 않는 경우
                dp[i + 1][w] = dp[i][w];

                // i + 1번째 보석을 사용하는 경우
                if (w >= weight) {
                    dp[i + 1][w] = Math.max(
                        dp[i][w],                 // i + 1번째 보석을 안 씀
                        dp[i][w - weight] + value // i + 1번째 보석을 한 번 사용
                    );
                }
            }
        }

        return dp[size][limitWeight];
    }
}