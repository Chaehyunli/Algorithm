import java.util.*;

class Solution {
    
    static class Word{
        String value;
        int idx;
        
        Word(String value, int idx){
            this.value = value;
            this.idx = idx;
        }
    }
    
    // word1, word2가 1글자 차이인지 확인
    static boolean isEdge(String word1, String word2){
        if(word1.length() != word2.length()){
            return false;
        }
        
        int size = word1.length();
        // 같은 글자수의 수
        int count = 0;
        
        for(int i = 0; i < size; i++){
            if(word1.charAt(i) == word2.charAt(i)){
                count++;
            }
        }
        
        // 한글자 차이이면
        if(count == size - 1){
            return true;
        }
        
        return false;
    }
    
    public int solution(String begin, String target, String[] words) {
        
        Queue<Word> queue = new LinkedList<>();
        boolean[] isVisited = new boolean[words.length]; // i번째 인덱스의 방문 여부
        
        queue.offer(new Word(begin, 0));
        
        while(!queue.isEmpty()){
            Word current = queue.poll();
            String cValue = current.value;
            int cIdx = current.idx;
            
            // 현재의 단어가 타깃이면 반환
            if(cValue.equals(target)){
                return cIdx;
            }
            
            for(int i = 0; i < words.length; i++){
                // i번째 단어가 cValue의 이웃이 아니면 건너뜀
                if(!isEdge(cValue, words[i])){
                    continue;
                }
                
                // i번째 단어가 cValue의 이웃이고 아직 사용을 안했으면
                if(!isVisited[i]){
                    isVisited[i] = true;
                    queue.offer(new Word(words[i], cIdx + 1));
                }
            }
        }
        
        // target을 만들 수 없으면
        return 0;
    }
}