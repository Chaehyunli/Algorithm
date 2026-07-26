class Solution {
    public String solution(String s) {
        
        String[] numbers = s.split(" ");
        
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        
        for (String numStr : numbers) {
            int num = Integer.parseInt(numStr);
            
            if (num < min) min = num;
            if (num > max) max = num;
        }

        return min + " " + max;
    }
}