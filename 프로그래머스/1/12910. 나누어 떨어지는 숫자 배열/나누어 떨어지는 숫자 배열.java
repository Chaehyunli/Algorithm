import java.util.*;

class Solution {
    public int[] solution(int[] arr, int divisor) {
        List<Integer> list = new ArrayList<>();
        
        for(int i = 0; i < arr.length; i++){
            if(arr[i] % divisor == 0){
                list.add(arr[i]);
            }
        }
        
        Collections.sort(list);
        
        int n = list.size();
        
        if(n == 0){
            return new int[]{-1};
        }
        
        int[] answer = new int[n];
        
        for(int i = 0; i < n; i++){
            answer[i] = list.get(i);
        }
        
        return answer;
    }
}