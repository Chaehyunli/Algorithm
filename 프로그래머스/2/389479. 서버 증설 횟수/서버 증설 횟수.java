class Solution {
    public int solution(int[] players, int m, int k) {
        int answer = 0;
        
        int[] improved_server = new int[24];
        int[] improved_count = new int[24];
        
        for(int i = 0; i < 24; i++){
            if(players[i] / m > improved_server[i]){
                int add_count = (players[i] / m) - improved_server[i];
                
                improved_count[i] += add_count;
                
                for(int j = 0; j < k; j++){
                    if(i+j <= 23){
                        improved_server[i+j] += add_count;
                    }
                }
            }
        }
        
        for(int i = 0; i < 24; i++){
            answer += improved_count[i];
        }
        
        return answer;
    }
}