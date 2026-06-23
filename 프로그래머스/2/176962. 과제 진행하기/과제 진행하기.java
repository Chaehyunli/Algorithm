import java.util.*;

class Solution {
    // 과제 클래스
    static class Task {
        String name;
        int start;
        int playtime;

        public Task(String name, int start, int playtime) {
            this.name = name;
            this.start = start;
            this.playtime = playtime;
        }
    }

    public String[] solution(String[][] plans) {
        List<Task> tasks = new ArrayList<>();
        
        // tasks에 분 변환해서 넣기
        for (String[] plan : plans) {
            String name = plan[0];
            String[] time = plan[1].split(":");
            int start = Integer.parseInt(time[0]) * 60 + Integer.parseInt(time[1]);
            int playtime = Integer.parseInt(plan[2]);
            tasks.add(new Task(name, start, playtime));
        }

        // 시작 시간 기준으로 오름차순 정렬
        Collections.sort(tasks, (a, b) -> a.start - b.start);

        List<String> answer = new ArrayList<>();
        Stack<Task> stopTasks = new Stack<>(); // 멈춘 과제를 담을 스택

        for (int i = 0; i < tasks.size() - 1; i++) {
            Task current = tasks.get(i);
            Task next = tasks.get(i + 1);
            
            int availableTime = next.start - current.start;

            // 현재 과제를 다음 과제 시작 전까지 끝낼 수 있는 경우
            if (current.playtime <= availableTime) {
                answer.add(current.name);
                availableTime -= current.playtime; // 남은 시간 갱신

                // 남은 시간 동안 스택에 멈춰둔 과제 해결하기
                while (!stopTasks.isEmpty() && availableTime > 0) {
                    Task stopped = stopTasks.peek();
                    
                    if (stopped.playtime <= availableTime) {
                        answer.add(stopTasks.pop().name);
                        availableTime -= stopped.playtime;
                    } else {
                        stopped.playtime -= availableTime;
                        availableTime = 0;
                    }
                }
            } 
            
            // 현재 과제를 다 못 끝내고 멈춰야 하는 경우
            else {
                current.playtime -= availableTime;
                stopTasks.push(current);
            }
        }

        // 마지막 과제는 무조건 바로 끝남
        answer.add(tasks.get(tasks.size() - 1).name);

        // 스택에 남아 있는 멈춘 과제들을 순서대로 꺼내어 마무리
        while (!stopTasks.isEmpty()) {
            answer.add(stopTasks.pop().name);
        }
        
        return answer.toArray(new String[answer.size()]);
    }
}