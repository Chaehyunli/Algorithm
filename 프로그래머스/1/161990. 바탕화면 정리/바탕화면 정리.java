import java.util.*;

class Solution {
    public int[] solution(String[] wallpaper) {
        int[] answer = {};
        
        ArrayList<Integer> rList = new ArrayList<>();
        ArrayList<Integer> cList = new ArrayList<>();
        
        for(int i = 0; i < wallpaper.length; i++){
            for(int j = 0; j < wallpaper[i].length(); j++){
                if(wallpaper[i].charAt(j) == '#'){
                    rList.add(i);
                    cList.add(j);
                }
            }
        }
        
        Collections.sort(rList);
        Collections.sort(cList);
        
        int minR = rList.get(0); // 가장 작은 값
        int minC = cList.get(0); // 가장 작은 값
        int maxR = rList.get(rList.size() - 1); // 가장 큰 값
        int maxC = cList.get(cList.size() - 1); // 가장 큰 값
        
        return new int[]{minR, minC, maxR + 1, maxC + 1};
    }
}