class Solution {
    public int solution(int[] numbers) {
        
        int total = 0;
        
        for(int i = 0; i < numbers.length; i++){
            total += numbers[i];
        }
        
        return 45 - total;
    }
}