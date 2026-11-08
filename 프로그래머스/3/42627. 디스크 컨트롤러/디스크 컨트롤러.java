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
        
        // jobs 목록 배열
        Task[] tasks = new Task[jobs.length];

        for (int i = 0; i < jobs.length; i++) {
            tasks[i] = new Task(i, jobs[i][0], jobs[i][1]);
        }

        // 요청 시각 오름차순
        Arrays.sort(tasks, (a, b) -> Integer.compare(a.time, b.time));
        
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
            while(jobIdx < tasks.length && tasks[jobIdx].time <= currentTime){
                // id, time, duration
                queue.offer(tasks[jobIdx]);
                jobIdx++;
            }
            
            // 대기 큐에 작업이 있으면
            if(!queue.isEmpty()){
                Task current = queue.poll();
                currentTime += current.duration;
                finishedTask++;
                totalTurnAroundTime += currentTime - current.time;
            }else{
                // 없으면, 다음 작업의 요청 시각까지 시간을 이동
                currentTime = tasks[jobIdx].time;
            }
            
        }
        
        // 평균 반환 시간의 정수부분
        int answer = totalTurnAroundTime / jobs.length;
        
        return answer;
    }
}