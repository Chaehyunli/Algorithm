import java.util.*;

class Solution {
    public int solution(int n, int k, int[] enemy) {
        // 무적권 수가 총 라운드 수보다 많으면 모든 라운드를 다 막을 수 있음
        if (k >= enemy.length) {
            return enemy.length;
        }

        // 가장 작은 값이 먼저 나오는 우선순위 큐 (최소 힙)
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        for (int i = 0; i < enemy.length; i++) {
            pq.add(enemy[i]); // 현재 라운드의 적 추가

            // 큐의 크기가 무적권 개수 k를 초과하면
            // 가장 적의 수가 적은 라운드는 무적권을 못 쓰므로 내 병사(n)로 막아야 함
            if (pq.size() > k) {
                n -= pq.poll(); // 가장 작은 적의 수만큼 내 병사 차감
            }

            // 내 병사가 음수가 되면 이 라운드는 막지 못한 것임
            if (n < 0) {
                return i; // 인덱스가 곧 지금까지 버틴 라운드 수가 됨
            }
        }

        // 끝까지 다 막아냈다면 총 라운드 수 반환
        return enemy.length;
    }
}