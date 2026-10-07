import java.util.*;

public class Main {

    static class Robot{
        int r;
        int c;

        public Robot(int r, int c){
            this.r = r;
            this.c = c;
        }
    }
    
    static boolean hasOtherRobot(int r, int c, Robot currentRobot) {
        for (Robot robot : robots) {
            if (robot != currentRobot
                    && robot.r == r
                    && robot.c == c) {
                return true;
            }
        }
        return false;
    }

    static int N; // 격자의 크기
    static int K; // 로봇청소기의 개수
    static int L; // 테스트 횟수

    static int[][] map; // 지도
    static Robot[] robots; // 로봇청소기

    // 좌, 상, 오, 하 -> 2단계에서 청소하지 않는 방향의 우선순위
    static int[] dx = {0, -1, 0, 1};
    static int[] dy = {-1, 0, 1, 0};

    public static void main(String[] args) {
        // Please write your code here.
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt(); // 격자의 크기
        K = sc.nextInt(); // 로봇청소기의 개수
        L = sc.nextInt(); // 테스트 횟수

        map = new int[N + 1][N + 1];
        robots = new Robot[K]; // 로봇은 0 ~ k - 1 번까지

        for(int i = 1; i <= N; i++){
            for(int j = 1; j <= N; j++){
                map[i][j] = sc.nextInt();
            }
        }

        // 로봇 배열에 로봇을 넣음
        for(int i = 0; i < K; i++){
            int r = sc.nextInt();
            int c = sc.nextInt();

            robots[i] = new Robot(r, c);
        }

        // print(map);

        while(L > 0){
            
            // [1단계] 청소기 이동
            for(Robot robot: robots){

                Queue<int[]> queue = new LinkedList<>();
                int[][] distanceMap = new int[N + 1][N + 1]; // 로봇의 위치로부터의 거리가 적힌 map
                
                for(int i = 0; i <= N; i++){
                    for(int j = 0; j <= N; j++){
                        distanceMap[i][j] = -1;
                    }
                }

                int r = robot.r;
                int c = robot.c;
                int minDistance = map[r][c] > 0 ? 0 : Integer.MAX_VALUE;
                
                queue.offer(new int[]{r, c, 0}); // 시작 위치는 거리 0
                distanceMap[r][c] = 0;

                while(!queue.isEmpty()){
                    int[] current = queue.poll();
                    int cx = current[0];
                    int cy = current[1];
                    int cd = current[2]; // 거리

                    for(int i = 0; i < 4; i++){
                        int nx = cx + dx[i];
                        int ny = cy + dy[i];

                        if(nx >= 1 && nx <= N && ny >= 1 && ny <= N){
                            // 아직 방문하지 않았고, 지도상에 먼저가 없거나, 있는 칸일 경우
                            if(distanceMap[nx][ny] == -1 && map[nx][ny] >= 0 && !hasOtherRobot(nx, ny, robot)){
                                distanceMap[nx][ny] = cd + 1;
                                queue.offer(new int[]{nx, ny, cd + 1});

                                // 먼지가 있는 위치라면
                                if(map[nx][ny] > 0){
                                    // 가장 가까운 먼지까지의 거리 업데이트
                                    minDistance = Math.min(minDistance, cd + 1);

                                }
                            }
                        }
                    }
                }

                int targetR = N + 1;
                int targetC = N + 1;

                for (int i = 1; i <= N; i++) {
                    for (int j = 1; j <= N; j++) {
                        if (map[i][j] > 0 && distanceMap[i][j] == minDistance) {

                            // 행 우선, 행이 같으면 열 우선
                            if (i < targetR || (i == targetR && j < targetC)) {
                                targetR = i;
                                targetC = j;
                            }
                        }
                    }
                }

                if (targetR != N + 1) { // 도달 가능한 먼지가 있었을 때만
                    robot.r = targetR;
                    robot.c = targetC;
                }

                // print(distanceMap);
                // System.out.println(robot.r + " " + robot.c);
                
            }

            // [2단계] - 청소
            for(Robot robot: robots){

                int r = robot.r;
                int c = robot.c;
                int minValue = 1000000000;

                // 5칸 중에서 좌, 상, 하, 오 쪽으로 보면서 해당 칸의 값이 작으면 가장 청소가 많이 되는 방향임
                for(int i = 0; i < 4; i++){
                    int nx = r + dx[i];
                    int ny = c + dy[i];

                    // 청소할 수 있는 먼지량의 비교
                    if(nx >= 1 && nx <= N && ny >= 1 && ny <= N && map[nx][ny] != -1){
                        minValue = Math.min(minValue, (Math.min(map[nx][ny], 20)));
                    }else{
                        // 격자 밖 또는 물건: 청소 가능한 먼지량은 0
                        minValue = Math.min(minValue, 0);
                    }
                }

                // System.out.println(minValue);

                boolean isFixed = false;

                for(int i = 0; i < 4; i++){
                    int nx = r + dx[i];
                    int ny = c + dy[i];

                    if(nx >= 1 && nx <= N && ny >= 1 && ny <= N){

                        if (map[nx][ny] == -1) continue;

                        // 우선순위에 따라 처음 발견되는 최소값에서 방향 고정(등돌린 칸이 최소이면, 나머지 지역의 먼지가 최대)
                        if((Math.min(map[nx][ny], 20) == minValue && isFixed == false)){
                            isFixed = true;
                            continue;
                        }

                        // 나머지 먼지가 많은 3칸 청소
                        if(map[nx][ny] >= 20){
                            map[nx][ny] -= 20;
                        }else {
                            map[nx][ny] = 0;
                        }
                    }
                }

                // 나머지 먼지가 많은 3칸 청소
                if(map[r][c] >= 20){
                    map[r][c] -= 20;
                }else{
                    map[r][c] = 0;
                }
            }
        
            // print(map);

            // [3단계] - 먼지 축적
            boolean[][] isDirty = new boolean[N + 1][N + 1];

            for(int i = 1; i <= N; i++){
                for(int j = 1; j <= N; j++){
                    if(map[i][j] > 0){
                        map[i][j] += 5;
                        isDirty[i][j] = true;
                    }
                }
            }

            // [4단계] - 먼지 확산

            // 확신된 먼지들을 합함
            for(int i = 1; i <= N; i++){
                for(int j = 1; j <= N; j++){
                    if(map[i][j] > 0 && isDirty[i][j] == true){
                        if(i > 1 && isDirty[i - 1][j] == false && map[i - 1][j] != -1){
                            map[i - 1][j] += map[i][j];
                        }

                        if(i <= N - 1 && isDirty[i + 1][j] == false && map[i + 1][j] != -1){
                            map[i + 1][j] += map[i][j];
                        }

                        if(j > 1 && isDirty[i][j - 1] == false && map[i][j - 1] != -1){
                            map[i][j - 1] += map[i][j];
                        }

                        if(j <= N - 1 && isDirty[i][j + 1] == false && map[i][j + 1] != -1){
                            map[i][j + 1] += map[i][j];
                        }
                    }
                }
            }

            // 각 합해진 먼지들에서 계산
            for(int i = 1; i <= N; i++){
                for(int j = 1; j <= N; j++){
                    if(map[i][j] > 0 && isDirty[i][j] == false){
                        map[i][j] = map[i][j] / 10;
                    }
                }
            }

            // print(map);

            // [5단계]
            int answer = 0;

            for(int i = 1; i <= N; i++){
                for(int j = 1; j <= N; j++){
                    if(map[i][j] > 0){
                        answer += map[i][j];
                    }
                }
            }

            System.out.println(answer);

            if (answer == 0) {
                return;
            }

            L--; // 테스트 횟수 감소
        }
    }

    static void print(int[][] map){

        System.out.println("==========디버깅==========");
        int r = map.length - 1;
        int c = map[0].length - 1;

        for(int i = 1; i <= r; i++){
            for(int j = 1; j <= c; j++){
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
}