import java.util.*;

class Solution {
    
    static class Task{
        int id; // 작업의 번호
        int time; // 작업의 요청 시각
        int duration; // 작업의 소요시간
        
        Task(int id, int time, int duration){
            this.id = id;
            this.time = time;
            this.duration = duration;
        }
    }
    
    public int solution(int[][] jobs) {
        
        // 요청 시간 기준으로 정렬
        Arrays.sort(jobs, (a,b) -> Integer.compare(a[0], b[0]));
        
        // 소요시간이 짧은 것, 요청 시각이 빠른 것, 번호가 작은 것 순
        PriorityQueue<Task> queue = new PriorityQueue<>((a, b) -> {
            
            // 소요시간이 같으면
            if(a.duration == b.duration){
                // 요청 시각이 같으면
                if(a.time == b.time){
                    // 번호 오름차순
                    return Integer.compare(a.id, b.id);
                }
                
                // 요청 시간 오름차순
                return Integer.compare(a.time, b.time);
            }
            
            // 소요시간 오름차순
            return Integer.compare(a.duration, b.duration);
        });
        
        int finishedTask = 0; // 완료된 작업의 수
        int totalTurnAroundTime = 0; // 반환 시간의 총합
        int currentTime = 0;
        int jobIdx = 0;
        
        while(finishedTask < jobs.length){
            // 현재의 시간이랑 들어온 작업의 시간을 비교해서 우선순위 큐에 넣음
            while(jobIdx < jobs.length && jobs[jobIdx][0] <= currentTime){
                // id, time, duration
                queue.offer(new Task(jobIdx, jobs[jobIdx][0], jobs[jobIdx][1]));
                jobIdx++;
            }
            
            // 대기 큐에 작업이 있으면
            if(!queue.isEmpty()){
                Task current = queue.poll();
                currentTime += current.duration;
                finishedTask++;
                totalTurnAroundTime += currentTime - current.time;
            }else{
                // 없으면, 다음 우선순위의 jobIdx 작업 시작 시간까지 currentTime을 이동
                currentTime = jobs[jobIdx][0];
            }
            
        }
        
        // 평균 반환 시간의 정수부분
        int answer = totalTurnAroundTime / jobs.length;
        
        return answer;
    }
}