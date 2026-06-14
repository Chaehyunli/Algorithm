import java.util.*;

class Solution {
    
    private ArrayList<ArrayList<Integer>> graph;
    
    public int solution(int n, int[][] wires) {
        
        int answer = 101;
        
        graph = new ArrayList<>();
        
        for(int i = 0; i <= n; i++){
            graph.add(new ArrayList<>());
        }
        
        for(int[] wire : wires){
            int v1 = wire[0];
            int v2 = wire[1];
            
            graph.get(v1).add(v2);
            graph.get(v2).add(v1);
        }
        
        for(int[] wire : wires){
            int v1 = wire[0];
            int v2 = wire[1];
            
            int countA = bfs(1, v1, v2, n);
            
            int countB = n - countA;
            
            int diff = Math.abs(countA - countB);
            
            answer = Math.min(answer, diff);
        }
        
        return answer;
    }
    
    private int bfs(int start, int v1, int v2, int n){
        boolean[] visited = new boolean[n + 1];
        
        Queue<Integer> queue = new LinkedList<>();
        
        queue.add(start);
        visited[start] = true;
        int count = 1;
        
        while(!queue.isEmpty()){
            int current = queue.poll();
            
            for(int neighbor : graph.get(current)){
                if((current == v1 && neighbor == v2) || (current == v2 && neighbor == v1)){
                    continue;
                }
                
                if(!visited[neighbor]){
                    visited[neighbor] = true;
                    queue.add(neighbor);
                    count++;
                }
            }
        }
        
        return count;
    }
}