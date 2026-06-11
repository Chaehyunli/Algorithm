import java.util.*;

class Solution {
    
    private int[] collatz(int n){
        
        ArrayList<Integer> list = new ArrayList<>();
        
        while(n != 1){
            
            list.add(n);
            
            if(n % 2 == 0){
                n = n / 2;
            }else{
                n = n * 3 + 1;
            }
        }
        
        list.add(n);
        
        
        int[] intArray = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            intArray[i] = list.get(i);
        }
        
        return intArray;
    }
    
    public double[] solution(int k, int[][] ranges) {
        
        int[] k_list = collatz(k);
        int size = k_list.length;
        int totalX = size - 1;
        
        double[] areas = new double[totalX];
        
        for (int i = 0; i < totalX; i++) {
            areas[i] = (k_list[i] + k_list[i + 1]) / 2.0;
        }
        
        double[] answer = new double[ranges.length];
        
        for (int i = 0; i < ranges.length; i++) {
            int start = ranges[i][0];
            int end = totalX + ranges[i][1];
            
            if (start > end) {
                answer[i] = -1.0;
            } else {
                double sum = 0.0;
                
                for (int j = start; j < end; j++) {
                    sum += areas[j];
                }
                answer[i] = sum;
            }
        }
        
        return answer;
    }
}