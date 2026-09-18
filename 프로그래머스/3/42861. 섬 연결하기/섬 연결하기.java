import java.util.*;


// Prim 알고리즘
class Solution {
    
    class Node {
        int id;
        // [도착 노드, 거리] 들의 리스트 저장
        ArrayList<int[]> next;

        public Node(int id) {
            this.id = id;
            this.next = new ArrayList<>();
        }
    }

    public int solution(int n, int[][] costs) {
        // nodes[0] ~ nodes[n - 1]
        Node[] nodes = new Node[n];

        // nodes[0] -> 0번 노드
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(i);
        }

        for (int[] cost : costs) {
            int start = cost[0];
            int end = cost[1];
            int value = cost[2];

            // 양방향 노드 연결
            nodes[start].next.add(new int[]{end, value});
            nodes[end].next.add(new int[]{start, value});
        }

        // 프림 알고리즘 구현
        boolean[] visited = new boolean[n];
        // [도착 노드, 비용] 기준 오름차순 우선순위 큐
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));

        pq.add(new int[]{0, 0}); // 0번 노드부터 시작 (비용 0)

        int answer = 0;
        int connectedCount = 0;

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int to = current[0];
            int cost = current[1];

            if (visited[to]) continue;

            visited[to] = true;
            answer += cost;
            connectedCount++;

            if (connectedCount == n) break;

            for (int[] nextNode : nodes[to].next) {
                if (!visited[nextNode[0]]) {
                    pq.add(nextNode);
                }
            }
        }

        return answer;
    }
}