class Solution {
    public int[] solution(int brown, int yellow) {
        int weight = 0;
        int height = 0;
        
        int total = brown + yellow;
        // yellow = (weight - 2) * (height - 2);
        
        for(int i = 2; i <= total; i++){
            if(total % i == 0){
                int w = i;
                int h = total / i;
                
                if(yellow == (w - 2) * (h - 2)){
                    weight = Math.max(w, h);
                    height = Math.min(w, h);
                    break;
                }
            }
        }
        
        return new int[]{weight, height};
    }
}