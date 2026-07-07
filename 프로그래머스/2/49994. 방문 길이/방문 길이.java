import java.util.*;

class Solution {
    public int solution(String dirs) {
        // 방문한 경로를 저장할 Set (중복 제거)
        HashSet<String> visitedPaths = new HashSet<>();
        
        int x = 0;
        int y = 0;
        
        for (int i = 0; i < dirs.length(); i++) {
            char command = dirs.charAt(i);
            int nextX = x;
            int nextY = y;

            if (command == 'U') {
                nextY++;
            } else if (command == 'D') {
                nextY--;
            } else if (command == 'R') {
                nextX++;
            } else if (command == 'L') {
                nextX--;
            }
            
            // 좌표평면의 경계(-5 ~ 5)를 넘어가는 경우 무시
            if (nextX < -5 || nextX > 5 || nextY < -5 || nextY > 5) {
                continue;
            }
            
            // "(0,0) -> (0,1)"과 "(0,1) -> (0,0)" -> 0010, 0100
            String path1 = "" + x + y + nextX + nextY;
            String path2 = "" + nextX + nextY + x + y;

            visitedPaths.add(path1);
            visitedPaths.add(path2);
            
            x = nextX;
            y = nextY;
        }
        
        // 양방향으로 넣었으므로 2로 나누기
        return visitedPaths.size() / 2;
    }
}