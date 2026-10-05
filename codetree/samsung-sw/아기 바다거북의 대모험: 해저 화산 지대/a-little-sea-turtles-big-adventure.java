import java.util.*;

public class Main {

    static int[][] map; // 0 : 갈수 있는 곳, 1 : 산호, 2 : 거북이
    static int[][] turtles; // turtles[i][0] : row, turtles[i][1] : column, turtles[i][2] : 거북이가 안식처에 도착했는지 여부(0 : 도착x, 1: 도착o)
    static int[][] volcanos; // volcanos[i][0] : row, volcanos[i][1] : column, volcanos[i][2] : 임계값, volcanos[i][3] : 압력, volcanos[i][4] : 분출 여부(0이면 분출x, 1이면 분출)
    static int[][] heat;
    
    // 우, 하, 좌, 상
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};

    public static void main(String[] args) {
        // Please write your code here.
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt(); // 격자의 크기
        int M = sc.nextInt(); // 바다거북 수
        int K = sc.nextInt(); // 해저 화산수

        map = new int[N][N]; // 0 ~ N - 1
        // isVisited = new boolean[N][N]; // 방문 여부
        turtles = new int[M][3]; // 거북이
        volcanos = new int[K][5]; // 화산
        heat = new int[N][N]; // 열기

        // 산호 그리기
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                map[i][j] = sc.nextInt();

                // if(map[i][j] == 1){
                //     isVisited[i][j] = true;
                // }
            }
        }

        // 거북이의 위치
        for(int i = 0; i < M; i++){
            int r = sc.nextInt();
            int c = sc.nextInt();
            // isVisited[r][c] = true; // 거북이의 위치 방문 완료 처리 

            turtles[i][0] = r;
            turtles[i][1] = c; 
            turtles[i][2] = -1; // 기본값 0, 거북이는 안식처에 도착하지 못함

            map[r][c] = 2; // 거북이의 위치는 지도상에서 2(못 가는 지역)으로 표시
        }

        // 화산의 위치 - 화산읜 거북이가 지나갈 수 있음
        for(int i = 0; i < K; i++){
            int r = sc.nextInt();
            int c = sc.nextInt();
            int limit = sc.nextInt();
            
            volcanos[i][0] = r;
            volcanos[i][1] = c;
            volcanos[i][2] = limit;
            volcanos[i][3] = 0;
            volcanos[i][4] = 0;
        }

        // print(map);
        // print(turtles);
        // print(volcanos);

        int remainTurtles = turtles.length;
        int turn = 1;

        while(remainTurtles > 0 && turn <= 100){
            // [1단계] 모든 거북이에 대해서 순차적으로 BFS
            for(int t = 0; t < turtles.length; t++){
                int sx = turtles[t][0];
                int sy = turtles[t][1];
                int isFinished = turtles[t][2]; // 거북이가 안식처에 도착했는지

                // 거북이가 안식처에 도착했거나, 화석이 되었다면(-2)
                if(isFinished != -1 || isFinished == -2){
                    continue;
                }

                int[][] distanceFromShelter = getDistanceFromShelter(N); // 안식처에서 부터 bfs

                int minDistance = Integer.MAX_VALUE;

                for(int i = 0; i < 4; i++){
                    int nx = sx + dx[i];
                    int ny = sy + dy[i];

                    if(nx >= 0 && nx <= N - 1 && ny >= 0 && ny <= N - 1){
                        // 지도상 갈 수 있는 장소(0)라면
                        if(map[nx][ny] == 0 && distanceFromShelter[nx][ny] != -1){
                            minDistance = Math.min(minDistance, distanceFromShelter[nx][ny]);
                        }
                    }
                }

                if(minDistance == Integer.MAX_VALUE){
                    continue;
                }

                for(int i = 0; i < 4; i++){
                    int nx = sx + dx[i];
                    int ny = sy + dy[i];

                    if(nx >= 0 && nx <= N - 1 && ny >= 0 && ny <= N - 1){
                        // 지도상 갈 수 있는 장소(0)라면
                        if(map[nx][ny] == 0 && distanceFromShelter[nx][ny] == minDistance){
                            map[nx][ny] = 2; // 거북이가 이동한 칸 처리
                            map[sx][sy] = 0; // 거북이가 이전에 있던 칸 처리
                            turtles[t][0] = nx;
                            turtles[t][1] = ny;

                            // 거북이가 은신처에 도착했다면 위치 갱신
                            if(nx == N - 1 && ny == N - 1){
                                map[nx][ny] = 0; // 안식처의 경우, 거북이가 도착하면 비워줘야함.
                                turtles[t][2] = turn;
                                remainTurtles--;
                            }
                            break;
                        }
                    }
                }

            }

            // [2단계] 화산 압력 10씩 증가
            for(int[] volcano : volcanos){
                volcano[3] += 10;
            }

            // [3단계] 화산 분출 및 연쇄 반응
            boolean changed = true;

            while(changed){
                changed = false;
                for(int[] volcano : volcanos){
                    if(volcano[4] == 1) continue; // 이미 분출한 화산

                    // 현재 압력 + 이 칸에 쌓인 외부 열기가 임계치 이상이면 분출
                    if(volcano[3] + heat[volcano[0]][volcano[1]] >= volcano[2]){
                        volcano[4] = 1;
                        spreadHeat(volcano[0], volcano[1], volcano[2], N);
                        changed = true;
                    }
                }           
            }

            // 화석화: 살아있는 거북이만 확인
            for(int t = 0; t < M; t++){
                if(turtles[t][2] != -1) continue; // 도착했거나 이미 화석
                if(heat[turtles[t][0]][turtles[t][1]] >= 20){
                    turtles[t][2] = -2;
                    remainTurtles--;   // 화석이 되면 남아 있는 거북이에서 제외
                }
            }

            // 4단계
            for(int i = 0; i < N; i++){
                for(int j = 0; j < N; j++){
                    heat[i][j] = 0;
                }
            }

            for(int[] volcano : volcanos){
                // 화산이 분출했다면
                if(volcano[4] == 1){
                    volcano[4] = 0;
                    volcano[3] = 0;
                }
            }

            turn++;
        }

        for(int[] turtle : turtles){
            if(turtle[2] != -2){
                System.out.println(turtle[2]);
            }else{
                System.out.println(-1);
            }
        }
        
    }

    // 은식처에서 시작해서 bfs 탐색
    static public int[][] getDistanceFromShelter(int N){

        int[][] grid = new int[N][N]; // 안식처에서의 거리가 적히 grid
        Queue<int[]> queue = new LinkedList<>();

        // 초기 grid를 -1로 채움
        for(int i = 0; i < N; i++){
            Arrays.fill(grid[i], -1);
        }

        grid[N - 1][N - 1] = 0;
        queue.offer(new int[]{N - 1, N - 1, 0}); // 안식처의 위치를 큐에 넣음

        while(!queue.isEmpty()){

            int[] current = queue.poll();
            int cx = current[0];
            int cy = current[1];
            int distance = current[2];

            for(int i = 0; i < 4; i++){
                int nx = cx + dx[i];
                int ny = cy + dy[i];

                if(nx >= 0 && nx <= N - 1 && ny >= 0 && ny <= N - 1){
                    // 아직 방문하지 않았고, 지도상 갈 수 있는 장소(0)라면
                    if(grid[nx][ny] == -1 && map[nx][ny] == 0){
                        queue.offer(new int[]{nx, ny, distance + 1});
                        grid[nx][ny] = distance + 1;
                    }
                }
            }
        }

        return grid;
    }

    // 화산 분출
    static void spreadHeat(int r, int c, int power, int N){
        heat[r][c] += power; // 화산 칸 자체에 P만큼

        for(int i = 0; i < 4; i++){
            int nx = r + dx[i];
            int ny = c + dy[i];
            int h = power / 2;

            // 격자 밖, 산호(1), 열기 0이면 이 방향 중단
            while(h > 0 && nx >= 0 && nx < N && ny >= 0 && ny < N && map[nx][ny] != 1){
                heat[nx][ny] += h;
                h /= 2;
                nx += dx[i];
                ny += dy[i];
            }
        }
    }

    // 디버깅 용
    static public void print(int[][] map){

        System.out.println("==========디버깅용 출력==========");

        int r = map.length;
        int c = map[0].length;

        for(int i = 0; i < r; i++){
            for(int j = 0; j < c; j++){
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
}