import java.util.*;

public class Solution {
    public int[] solution(int []arr) {
        ArrayList<Integer> tempList = new ArrayList<>();
        
        int preNum = -1;
        
        for(int num : arr){
            if(preNum != num){
                tempList.add(num);
                preNum = num;
            }
        }
        
        int[] answer = new int[tempList.size()];
        
        for(int i = 0; i < tempList.size(); i++){
            answer[i] = tempList.get(i);
        }

        return answer;
    }
}