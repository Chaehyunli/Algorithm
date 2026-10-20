import java.util.*;

public class Main {

    static ArrayList<Node> list; // i번 인덱스가 i+1번 노드
    static int answer = Integer.MAX_VALUE;
    static int[][] distanceMap; // distanceMap[i][n] : startPoints[i] 번 노드의 사람이 n번 노드까지의 거리
    static int[] startPoints;

    // 사람이 있는 노드에서 제공된 목적지 노드까지의 거리를 계산하는 bfs, 길이 없으면 -1
    // 모든 노드에 대해서 순회하면서 모든 사람들간의 거리의 max가 최소가 되는 지점을 찾아서 answer(단 모든 사람의 bfs 결과가 -1이 아니면 반영)
    // 만약 answer = -1이면, -1 출력

    static class Node{
        int id; // 각 노드의 ID
        ArrayList<Node> neighbors;

        public Node(int id){
            this.id = id;
            this.neighbors = new ArrayList<>();
        }

        public void addEdge(int[] edge){
            int start = edge[0];
            int end = edge[1];

            if(id != start){
                return;
            }

            neighbors.add(list.get(end - 1));
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); // 노드의 수
        int m = sc.nextInt(); // 간선의 수
        int k = sc.nextInt(); // 사람의 수
        int[][] edges = new int[m][2];
        

        for (int i = 0; i < m; i++) {
            edges[i][0] = sc.nextInt();
            edges[i][1] = sc.nextInt();
        }

        startPoints = new int[k];
        for (int i = 0; i < k; i++) {
            startPoints[i] = sc.nextInt();
        }

        list = new ArrayList<>();
        distanceMap = new int[k][n + 1];

        for(int i = 1; i <= n; i++){
            list.add(new Node(i)); // i번 노드는 list의 i-1 인덱스
        }

        for(int[] edge : edges){
            int start = edge[0];
            int end = edge[1];
            list.get(start - 1).addEdge(edge);
        }

        // distanceMap -1로 초기화
        for(int[] r : distanceMap){
            Arrays.fill(r, -1);
        }

        for(int startPoint : startPoints){
            bfs(startPoint);
        }

        // Please write your code here.
        for(Node node : list){
            int maxDistance = -1;
            boolean isAvailable = true;
            for(int i = 0; i < startPoints.length; i++){
                int distance = distanceMap[i][node.id];
                // int distance = bfs(startPoint, node);

                if(distance == -1){
                    // 하나의 사람이라도 거리 -1(도착 못하면) 넘김
                    isAvailable = false;
                    break;
                }

                maxDistance = Math.max(distance, maxDistance);
                
            }

            // 하나의 노드의 maxDistance와 지금까지의 누적 max를 비교해서 업데이트
            if(isAvailable){
                answer = Math.min(answer, maxDistance);
            }
        }

        if(answer != Integer.MAX_VALUE){
            System.out.println(answer);
        }else{
            System.out.println(-1);
        }
        
    }

    // 각 사람마다 1번의 bfs를 해서 모든 노드까지의 거리를 미리 distanceMap에 저장해 놓음
    static void bfs(int startPoint){

        Queue<int[]> queue = new LinkedList<>();
        Set<Integer> isVisited = new HashSet<>();

        int idx = 0;

        for(int i = 0; i < startPoints.length; i++){
            if(startPoint == startPoints[i]){
                idx = i;
            }
        }

        // 시작 노드와 끝 노드가 같으면 거리가 0
        distanceMap[idx][startPoint] = 0;

        // 시작 거리는 0
        queue.offer(new int[]{startPoint, 0});
        isVisited.add(startPoint);

        while(!queue.isEmpty()){
            int[] current = queue.poll();
            int nodeNumber = current[0];
            int distance = current[1];

            Node currentNode = list.get(nodeNumber - 1);

            for(Node neighbor : currentNode.neighbors){

                // 인접 노드가 이미 방문했으면 넘어감
                if(isVisited.contains(neighbor.id)){
                    continue;
                }

                isVisited.add(neighbor.id);
                queue.offer(new int[]{neighbor.id, distance + 1});

                int temp_idx = 0;

                for(int i = 0; i < startPoints.length; i++){
                    if(startPoint == startPoints[i]){
                        temp_idx = i;
                    }
                }

                // distanceMap에 저장
                distanceMap[temp_idx][neighbor.id] = distance + 1;
                
            }
        }

    }
}