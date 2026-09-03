import java.util.*;

class Solution {
    
    static class Task implements Comparable<Task>{
        int id;
        int start;
        int duration;
        
        public Task(int id, int start, int duration){
            this.id = id;
            this.start = start;
            this.duration = duration;
        }
        
        @Override
        public int compareTo(Task o){
            if(this.duration == o.duration){
                if(this.start == o.start){
                    return Integer.compare(this.id, o.id);
                }
                
                return Integer.compare(this.start, o.start);
            }
            
            return Integer.compare(this.duration, o.duration);
        }
        
    }
    
    public int solution(int[][] jobs) {
        
        // 요청 시간 기준으로 정렬
        Arrays.sort(jobs, (a,b) -> Integer.compare(a[0], b[0]));
        
        PriorityQueue<Task> queue = new PriorityQueue<>();
        
        int totalTurnaroundTime = 0; // 요청부터 종료까지 걸린 총 시간
        int currentTime = 0;        // 현재 시각
        int jobsIdx = 0;            // jobs 배열 인덱스
        int count = 0;              // 처리 완료된 작업 수
        
        while(count < jobs.length){
            
            while(jobsIdx < jobs.length && jobs[jobsIdx][0] <= currentTime){
                queue.offer(new Task(jobsIdx, jobs[jobsIdx][0], jobs[jobsIdx][1]));
                jobsIdx++;
            }
            
            if(!queue.isEmpty()){
                Task current = queue.poll();
                
                currentTime += current.duration;
                
                totalTurnaroundTime += currentTime - current.start;
                count++;
            }else{
                currentTime = jobs[jobsIdx][0];
            }
        }
        
        return totalTurnaroundTime / jobs.length;
    }
}