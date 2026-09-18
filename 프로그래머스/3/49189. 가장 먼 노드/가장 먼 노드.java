import java.util.*;

class Solution {
    
    class Node {
        int id;
        int value; // 1번 노드르부터의 거리
        // 연결된 노드들이 저장되어 있는 동적 리스트
        ArrayList<Integer> next;
        
        public Node(int id, int value){
            this.id = id;
            this.value = value;
            this.next = new ArrayList<>();
        }
    }
    
    public int solution(int n, int[][] edges) {
        
        Node[] nodes = new Node[n];
        
        for(int i = 0; i < n; i++){
            // nodes[0] -> 1번 노드를 의미
            nodes[i] = new Node(i + 1, 0); // 거리는 초기값 0
        }
        
        for(int[] edge : edges){
            int start = edge[0];
            int end = edge[1];
            
            nodes[start - 1].next.add(end);
            nodes[end - 1].next.add(start);
        }
        
        // 1번 노드의 방문 유무 -> isVisited[1]
        boolean[] isVisited = new boolean[n + 1];
        
        Queue<Node> queue = new LinkedList<>();
        
        // 1번 노드를 큐에 넣음
        queue.offer(nodes[0]);
        
        // 1 -> 방문함, 0 -> 방문 x
        isVisited[1] = true;
        
        int maxV = -1;
        
        while(!queue.isEmpty()){
            Node current = queue.poll();
            int distance = current.value;
            
            for(int c : current.next){
                if(!isVisited[c]){
                    isVisited[c] = true;
                    nodes[c - 1].value = distance + 1;
                    queue.offer(nodes[c - 1]);
                    maxV = Math.max(maxV, distance + 1);
                }
            }
            
        }
        
        int count = 0;
        
        for(Node node : nodes){
            if(node.value == maxV){
                count++;
            }
        }    
        
        return count;
    }
}