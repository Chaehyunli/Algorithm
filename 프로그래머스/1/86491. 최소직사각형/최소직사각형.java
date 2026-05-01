class Solution {
    public int solution(int[][] sizes) {
        
        int max_w = 0;
        int max_h = 0;
        
        for(int[] card : sizes){
            int w = Math.max(card[0], card[1]);
            int h = Math.min(card[0], card[1]);
            
            max_w = Math.max(max_w, w);
            max_h = Math.max(max_h, h);
        }
        
        int answer = 0;
        
        answer = max_w * max_h;
        
        return answer;
    }
}