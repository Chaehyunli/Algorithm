import java.util.*;

class Solution {
    
    static class Word{
        String word;
        String[] neighbor; // 이웃한 words 리스트
        int distance;
        
        // words에 begin이 포함되어 있어야함
        public Word(String word, String[] words, int distance){
            this.word = word;
            this.neighbor = convertNeighbor(words);
            this.distance = distance;
        }
        
        // ["hot", "dot", "dog", "lot", "log", "cog"] 를 해당 word와 이웃한 words만 있는 배열로 변환하는 함수
        private String[] convertNeighbor(String[] words){
            
            ArrayList<String> temp = new ArrayList<>();
            
            for(String word : words){
                // 두 단어의 차이가 1글자면
                if(isEdge(this.word, word)){
                    temp.add(word);
                }
            }
            
            String[] tempList = new String[temp.size()];
            
            // ArrayList<String> -> String[] 변환
            for(int i = 0; i < temp.size(); i++){
                tempList[i] = temp.get(i);
            }
            
            return tempList;
        }
        
        // word1, word2의 글자수 차이가 1글자인지 확인하는 함수(글자수 차이가 1개여야 이웃한 것임)
        private boolean isEdge(String word1, String word2){
            int length = word1.length();
            int count = 0; // 일치하는 글자 수
            
            for(int i = 0; i < length; i++){
                if(word1.charAt(i) == word2.charAt(i)){
                    count++;
                }
            }
            
            return length == count + 1;
        }
    }
    
    public int solution(String begin, String target, String[] words) {
        
        Queue<Word> queue = new LinkedList<>();
        Set<String> isVisited = new HashSet<>(); // 이 문자열을 사용한 적이 있나?
        
        // 기존 words에 begin 추가해서 newWords 생성
        ArrayList<String> temp = new ArrayList<>(Arrays.asList(words));
        temp.add(begin);
        String[] newWords = new String[temp.size()];
        
        for(int i = 0; i < temp.size(); i++){
            newWords[i] = temp.get(i);
        }
        
        // begin Word를 큐에 넣기
        queue.offer(new Word(begin, newWords, 0));
        isVisited.add(begin);
        
        while(!queue.isEmpty()){
            Word current = queue.poll();
            
            if(current.word.equals(target)){
                return current.distance;
            }
            
            for(String n : current.neighbor){
                if(!isVisited.contains(n)){
                    isVisited.add(n);
                    queue.offer(new Word(n, newWords, current.distance + 1));
                }
            }
        }
        
        return 0;
              
    }
}