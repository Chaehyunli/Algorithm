import java.util.*;

class Solution {
    
    static class Process {
        int location;
        int priority;
        
        Process(int location, int priority){
            this.location = location;
            this.priority = priority;
        }
    }
    
    public int solution(int[] priorities, int location) {
        
        Queue<Process> queue = new LinkedList<>();
        
        for(int i = 0; i < priorities.length; i++){
            queue.offer(new Process(i, priorities[i]));
        }
        
        int[] sortedPriorities = priorities.clone();
        Arrays.sort(sortedPriorities); // 오름차순 정렬

        // 배열 뒤집기 (내림차순 만들기)
        int n = sortedPriorities.length;
        
        for (int i = 0; i < n / 2; i++) {
            int temp = sortedPriorities[i];
            sortedPriorities[i] = sortedPriorities[n - 1 - i];
            sortedPriorities[n - 1 - i] = temp;
        }
        
        int targetIdx = 0; // 현재 정렬 배열에서 찾아야 하는 최대 우선순위 위치
        int answer = 0; // 몇 번째로 실행되었는지 세는 카운터
        
        while(!queue.isEmpty()){
            Process current = queue.poll();
            
            if(current.priority == sortedPriorities[targetIdx]){
                answer++;
                targetIdx++;
                
                if(current.location == location){
                    return answer;
                }
            } else{
                queue.offer(current);
            }
        }
       
        return answer;
    }
}