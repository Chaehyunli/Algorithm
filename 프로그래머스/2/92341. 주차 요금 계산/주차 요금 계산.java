import java.util.*;

class Solution {
    
    private int defaultTime;
    private int defaultFee;
    private int addTime;
    private int addFee;
    
    private int parseTime(String time){
        String[] t = time.split(":");
        
        int hours = Integer.parseInt(t[0]);
        int minutes = Integer.parseInt(t[1]);
        
        return hours * 60 + minutes;
    }
    
    private int calculateFees(int time){
        int fee = 0;
        
        if(time >= defaultTime){
            fee = defaultFee + (time - defaultTime + addTime - 1) / addTime * addFee;
        }else{
            fee = defaultFee;
        }
        
        return fee;
    }
    
    public int[] solution(int[] fees, String[] records) {
        
        Map<Integer,Integer> buf = new HashMap<>();
        Map<Integer,Integer> record = new TreeMap<>(); // 각 차량별로 주차한 누적 시간(오름차순)
            
        defaultTime = fees[0];
        defaultFee = fees[1];
        addTime = fees[2];
        addFee = fees[3];
        
        for(String r : records){
            String[] buffer = r.split(" ");
            int time = parseTime(buffer[0]);
            int carID = Integer.parseInt(buffer[1]);
            String action = buffer[2];
            
            if(action.equals("IN")){
                buf.put(carID, time);    
            }else if(buf.containsKey(carID)){
                int diff = time - buf.get(carID);
                record.put(carID, record.getOrDefault(carID, 0) + diff);
                buf.remove(carID);
            }
        }
        
        int endTime = parseTime("23:59"); // 1439분
        
        // 출차 안한 차량은 23:59 출차로 처리
        for(Integer carID : buf.keySet()) {
            int diff = endTime - buf.get(carID);
            record.put(carID, record.getOrDefault(carID, 0) + diff);
        }
        
        int size = record.size();
        int[] answer = new int[size];
        
        int indx = 0;
        for(Map.Entry<Integer, Integer> entry : record.entrySet()){
            answer[indx] = calculateFees(entry.getValue());
            indx++;
        }
            
        return answer;
    }
}