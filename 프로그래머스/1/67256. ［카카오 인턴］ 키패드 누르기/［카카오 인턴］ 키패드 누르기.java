import java.util.*;

class Solution {
    
    private static final Map<String, int[]> keypad = new HashMap<>();

    private String calculateDistance(String preLeft, String preRight, int number, String hand) {
        
        // 위치 정보 넣기
        keypad.put("1", new int[]{0, 0}); 
        keypad.put("2", new int[]{0, 1});
        keypad.put("3", new int[]{0, 2});
        keypad.put("4", new int[]{1, 0}); 
        keypad.put("5", new int[]{1, 1}); 
        keypad.put("6", new int[]{1, 2});
        keypad.put("7", new int[]{2, 0}); 
        keypad.put("8", new int[]{2, 1}); 
        keypad.put("9", new int[]{2, 2});
        keypad.put("*", new int[]{3, 0}); 
        keypad.put("0", new int[]{3, 1}); 
        keypad.put("#", new int[]{3, 2});
        
        int[] L_pos = keypad.get(preLeft);
        int[] R_pos = keypad.get(preRight);
        int[] N_pos = keypad.get(Integer.toString(number));

        // 맨해튼 거리 계산: |x1 - x2| + |y1 - y2|
        int distL = Math.abs(L_pos[0] - N_pos[0]) + Math.abs(L_pos[1] - N_pos[1]);
        int distR = Math.abs(R_pos[0] - N_pos[0]) + Math.abs(R_pos[1] - N_pos[1]);

        if (distL < distR) {
            return "L";
        } else if (distR < distL) {
            return "R";
        } else {
            // 거리가 같을 경우 주 손잡이 확인
            return hand.equals("left") ? "L" : "R";
        }
    }
    
    
    public String solution(int[] numbers, String hand) {
        StringBuilder sb = new StringBuilder();
        
        String preLeft = "*";
        String preRight = "#";
        
        for(int number : numbers){
            if(number == 1 || number == 4 || number == 7){
                preLeft = Integer.toString(number);
                sb.append("L");
            }else if(number == 3 || number == 6 || number == 9){
                preRight = Integer.toString(number);
                sb.append("R");
            }else{
                String result = calculateDistance(preLeft, preRight, number, hand);
                if (result.equals("R")) {
                    preRight = Integer.toString(number);
                    sb.append("R");
                } else {
                    preLeft = Integer.toString(number);
                    sb.append("L");
                }
            }
        }
        
        return sb.toString();
    }
}