import java.util.*;

class Solution {
    public int[] solution(int[] progresses, int[] speeds) {
        
        int size = progresses.length;
        
        int[] remainDays = new int[size]; // [7, 3, 9]
        
        for(int i = 0; i < size; i++){
            int remainDay = (99 - progresses[i] + speeds[i]) / speeds[i];
            
            remainDays[i] = remainDay;
        }
        
        List<Integer> listAnswer = new ArrayList<>();
        
        int count = 1;
        int baseline = remainDays[0];
        
        for(int i = 1; i < remainDays.length; i++){
            // 기준 baseline보다 이후 작업의 시간이 더 오래 걸리면
            if(baseline < remainDays[i]){
                baseline = remainDays[i];
                listAnswer.add(count);
                count = 1;
            }else{
                count++;
            }
            
            // 마지막 인덱스에서는 그떄의 count를 넣어줌
            if(i == remainDays.length - 1){
                listAnswer.add(count);
            }
        }
        
        // ArrayList -> int[] 변환
        int[] answer = new int[listAnswer.size()];
        
        for(int i = 0; i < answer.length; i++){
            answer[i] = listAnswer.get(i);
        }
        
        return answer;
    }
}