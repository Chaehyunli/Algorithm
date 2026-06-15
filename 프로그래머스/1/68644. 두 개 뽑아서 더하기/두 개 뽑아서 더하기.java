import java.util.*;

class Solution {
    public int[] solution(int[] numbers) {
        
        Set<Integer> set = new HashSet<>();
        
        for(int i = 0; i < numbers.length; i++){
            for(int j = i + 1; j < numbers.length; j++){
                int value = numbers[i] + numbers[j];
                set.add(value);
            }
        }
        
        List<Integer> list = new ArrayList<>(set);
        
        Collections.sort(list);
        
        int size = list.size();
        
        int[] answer = new int[size];
        
        for(int i = 0; i < list.size(); i++){
            answer[i] = list.get(i);
        }
        
        return answer;
    }
}