import java.util.*;

class Solution {
    public int solution(String[][] book_times) {
        
        Map<Integer,Integer> map = new TreeMap<>(); // 순서 보장
        
        int maxRooms = 0;
        int currentRooms = 0;
        
        for(String[] book_time : book_times){
            int start = parseTime(book_time[0]);
            int end = parseTime(book_time[1]) + 10;
            
            map.put(start, map.getOrDefault(start,0) + 1);
            map.put(end, map.getOrDefault(end,0) - 1);
        }
        
        for(Integer value : map.values()){
            currentRooms += value;
            
            if (currentRooms > maxRooms) {
                maxRooms = currentRooms;
            }
        }
        
        return maxRooms;
    }
    
    private int parseTime(String time){
        String[] split = time.split(":");
        int hour = Integer.parseInt(split[0]);
        int minute = Integer.parseInt(split[1]);
        
        return hour * 60 + minute;
    }
}