class Solution {
    public int solution(String word) {
        
        int init = 1;
        int[] value = new int[5];
        
        for(int i = 4; i >= 0; i--){
            if(i == 4){
                value[i] = init;
            }else{
                value[i] = value[i + 1] * 5 + 1;
            }
        }
        
        int answer = 0;
        String vowels = "AEIOU";
        
        for(int i = 0; i < word.length(); i++){
            int c = word.charAt(i);
            int index = vowels.indexOf(c);
            
            answer += index * value[i] + 1;
        }
          
        return answer;
    }
}