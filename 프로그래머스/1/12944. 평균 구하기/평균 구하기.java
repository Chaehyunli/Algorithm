class Solution {
    public double solution(int[] arr) {
        double answer = 0;
        
        double size = arr.length;
        
        for(int current : arr){
            answer += current;
        }
        
        return answer / size;
    }
}