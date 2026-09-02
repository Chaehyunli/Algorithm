import java.util.*;

class Solution {
    
    // Music class
    static class Music implements Comparable<Music>{
        int id;
        int play;
        
        public Music(int id, int play){
            this.id = id;
            this.play = play;
        }
            
        @Override
        public int compareTo(Music o){
            // 재생 횟수가 같으면 id 기준 오름차순
            if(this.play == o.play){
                return Integer.compare(this.id, o.id); // id 비교
            }
                
            // 재생 횟수가 다르면 play 기준 내림차순
            return Integer.compare(o.play, this.play); // play 비교
        }
    }
    
    public int[] solution(String[] genres, int[] plays) {
        
        // 장르별 총 재생 수
        Map<String, Integer> genrePlayMap = new HashMap<>();
        
        // 장르별 노래 목록 저장
        Map<String, List<Music>> genreMusicMap = new HashMap<>();
        
        for(int i = 0; i < genres.length; i++){
            String genre = genres[i];
            int play = plays[i];
            
            genrePlayMap.put(genre, genrePlayMap.getOrDefault(genre, 0) + play);
            
            if (!genreMusicMap.containsKey(genre)) {
                genreMusicMap.put(genre, new ArrayList<>());
            }
            
            genreMusicMap.get(genre).add(new Music(i, play));
        }
        
        List<String> sortedGenres = new ArrayList<>(genrePlayMap.keySet());
        sortedGenres.sort((g1, g2) -> Integer.compare(genrePlayMap.get(g2), genrePlayMap.get(g1)));
        
        List<Integer> result = new ArrayList<>();
        
        for (String genre : sortedGenres) {
            List<Music> musics = genreMusicMap.get(genre);
            Collections.sort(musics);

            // 장르당 최대 2개만 선택
            result.add(musics.get(0).id);
            
            if (musics.size() > 1) {
                result.add(musics.get(1).id);
            }
        }
        
        return result.stream().mapToInt(i -> i).toArray();
    }
}