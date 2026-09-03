import java.util.*;

class Solution {
    public int[] solution(int[] array, int[][] commands) {
        int[] answer = new int[commands.length];

        int index = 0;
        
        for(int[] command : commands){
            int start = command[0];
            int end = command[1];
            int idx = command[2];
            
            int[] subArray = new int[end - start + 1];
            
            int j = 0;
            for(int i = start - 1; i <= end - 1; i++){
                subArray[j] = array[i];
                j++;
            }
            
            Arrays.sort(subArray);
            
            answer[index++] = subArray[idx - 1];
        }
        
        return answer;
    }
}