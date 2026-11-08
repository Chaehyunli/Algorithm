import java.util.*;

public class Solution {
    public int[] solution(int[] arr) {
        
        int prev = -1;
        
        List<Integer> list = new ArrayList<>();
        
        for(int i = 0; i < arr.length; i++){
            
            // 이전의 값이 현재 값과 같으면 무시
            if(arr[i] == prev){
                continue;
            }
            
            // 다르면 이전의 값 갱신 및 넣기
            prev = arr[i];

            list.add(arr[i]);
        }
        
        // list -> int[] 로 변환
        int[] answer = new int[list.size()];
        
        for(int i = 0; i < answer.length; i++){
            answer[i] = list.get(i);
        }

        return answer;
    }
}