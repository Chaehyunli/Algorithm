import java.util.*;

class Solution {
    // 각 지역을 표현할 Node 클래스 정의
    static class Node {
        int id;                  // 지역 번호
        List<Node> nextNodes;    // 인접한 노드들의 목록 리스트

        Node(int id) {
            this.id = id;
            this.nextNodes = new ArrayList<>();
        }
    }

    public int[] solution(int n, int[][] roads, int[] sources, int destination) {
        // Node 객체들 생성
        Node[] nodes = new Node[n + 1];
        
        for (int i = 1; i <= n; i++) {
            nodes[i] = new Node(i);
        }

        // 도로 정보를 바탕으로 Node 간 연결 관계 설정
        for (int[] road : roads) {
            Node u = nodes[road[0]];
            Node v = nodes[road[1]];
            
            // 양방향 추가
            u.nextNodes.add(v);
            v.nextNodes.add(u);
        }

        int[] dist = new int[n + 1];
        Arrays.fill(dist, -1);

        Queue<Node> queue = new LinkedList<>();

        // destination 노드를 Queue에 넣고 시작
        Node startNode = nodes[destination];
        queue.add(startNode);
        dist[startNode.id] = 0;

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            // 인덱스가 아닌 Node 객체 참조를 직접 순회
            for (Node next : current.nextNodes) {
                if (dist[next.id] == -1) {
                    dist[next.id] = dist[current.id] + 1;
                    queue.add(next);
                }
            }
        }

        int[] answer = new int[sources.length];
        for (int i = 0; i < sources.length; i++) {
            answer[i] = dist[sources[i]];
        }

        return answer;
    }
}