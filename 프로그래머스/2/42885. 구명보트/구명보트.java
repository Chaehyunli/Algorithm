import java.util.*;

class Solution {
    public int solution(int[] people, int limit) {
        
        Arrays.sort(people); // [50, 50, 70, 80]
        int count = 0;
        int left = 0; // 가장 가벼운 사람
        int right = people.length - 1; // 가장 무거운 사람 
        
        while(left <= right){
            // 가장 무거운 사람과 가벼운 사람을 보트에 태움
            if(people[left] + people[right] <= limit){
                left++;
                right--;
                count++;
            }else{
                right--;
                count++;
            }
            
        }
        
        return count;
    }
}