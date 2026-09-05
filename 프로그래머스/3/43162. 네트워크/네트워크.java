import java.util.*;

class Solution {
    
    static class Node{
        
        public int id;
        public int[] neighbor;
        
        public Node(int id, int[] neighbor){
            this.id = id;
            this.neighbor = convertNeighbor(neighbor);
        }
        
        // [1, 1, 0] -> [1] 로 변경(0번 노드의 경우) - 이웃한 노드 번호의 리스트만
        private int[] convertNeighbor(int[] neighbor){
            
            ArrayList<Integer> temp = new ArrayList<>();
            
            for(int i = 0; i < neighbor.length; i++){
                if(neighbor[i] == 1 && i != id){
                    temp.add(i);
                }
            }
            
            int[] list_temp = new int[temp.size()];
            
            for(int i = 0; i < temp.size(); i++){
                list_temp[i] = temp.get(i);
            }
            
            return list_temp;
        }
    }
    
    public int solution(int n, int[][] computers) {
        
        boolean[] visited = new boolean[n];
        int count = 0;
        
        for(int i = 0; i < computers.length; i++){
            if(!visited[i]){
                count++;
                
                // 하나의 그룹이 끝나면 queue를 비워야함
                Queue<Node> queue = new LinkedList<>();
                
                // i번 노드를 queue에 넣음
                queue.offer(new Node(i, computers[i]));
                visited[i] = true;
                
                while(!queue.isEmpty()){
                    Node current = queue.poll();
            
                    for(int c : current.neighbor){
                        if((c != current.id) && (visited[c] == false)){
                            visited[c] = true;
                            queue.offer(new Node(c, computers[c]));
                        }
                    }
                }
            }
                
        }
        
        return count;
    }
}