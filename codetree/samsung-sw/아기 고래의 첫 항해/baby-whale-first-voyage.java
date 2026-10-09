import java.util.*;

// 고래 위치(r, c) -> 처음 바라보는 방향 d(1 : 상, 2 : 하, 3 : 좌, 4 : 우)
// [1단계]
//      - 현재 바라보는 방향 기준, 직진. 좌, 우, 뒤 우선순위로 해서 탐색

// [2단계]
//      - 인전한 칸 모두 방문 가능한 바다가 없으면
//      - 가장 가까운 바다를 찾음 - 거리가 같으면 행번호 작은 것, 행번호가 같으면 열이 작은 것
//      - 이미 방문한 바다로 지나갈 수 있음
//      - 선택한 칸까지 최단거리로 이동(좌, 하, 우, 상) -> 도착 후 방향도 중요하기 때문에 이 순서 중요

public class Main {

    static int N; // 격자의 크기
    static int r; // 처음 고래의 행
    static int c; // 처음 고래의 열
    static int d; // 처음 고래의 방향 (1, 2, 3, 4) -> (상, 하, 좌, 우)
    static int[][] map;
    static boolean[][] isVisited;

    // 상(-1, 0), 하(1, 0), 좌(0, -1), 우(0, 1)

    // 직, 좌, 우, 뒤
    // dx1[0] : 바라보는 방향이 1일 때의 우선순위
    static int[][] dx1 = new int[][]{
        {-1, 0, 0, 1}, {1, 0, 0, -1}, {0, 1, -1, 0}, {0, -1, 1, 0}
    };

    static int[][] dy1 = new int[][]{
        {0, -1, 1, 0}, {0, 1, -1, 0}, {-1, 0, 0, 1}, {1, 0, 0, -1}
    };

    // dir[0] : 바라보는 방향이 1일 때의 이동 후의 방향
    static int[][] dir1 = new int[][]{
        {1, 3, 4, 2}, {2, 4, 3, 1}, {3, 2, 1, 4}, {4, 1, 2, 3}
    };

    // 좌, 하, 우, 상
    static int[] dx2 = {0, 1, 0, -1};
    static int[] dy2 = {-1, 0, 1, 0};
    static int[] dir2 = {3, 2, 4, 1};

    public static void main(String[] args) {
        // Please write your code here.
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        r = sc.nextInt();
        c = sc.nextInt();
        d = sc.nextInt();

        map = new int[N + 1][N + 1];
        isVisited = new boolean[N + 1][N + 1];

        int unVisitedCount = 0; // 전체 미방문 칸의 개수

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{r, c, d});
        isVisited[r][c] = true;
        System.out.println(r + " " + c);

        // 초기 정보 map에 채우기
        for(int i = 1; i <= N; i++){
            for(int j = 1; j <= N; j++){
                map[i][j] = sc.nextInt(); // 1이면 암초

                if(map[i][j] == 1){
                    isVisited[i][j] = true;
                }else{
                    unVisitedCount++;
                }
            }
        }

    
        while(!queue.isEmpty() && unVisitedCount > 0){
            // [1단계]
            int[] current = queue.poll();
            int cx = current[0];
            int cy = current[1];
            int cd = current[2]; // 고래의 고개 방향 1, 2, 3, 4

            boolean isMoved = false; // 1단계 흐름에 들어간적 있는지

            for(int i = 0; i < 4; i++){
                int nx = cx + dx1[cd - 1][i];
                int ny = cy + dy1[cd - 1][i];
                int nd = dir1[cd - 1][i];

                if(nx <= 0 || nx > N || ny <= 0 || ny > N){
                    continue;
                }

                if(!isVisited[nx][ny] && map[nx][ny] == 0){
                    isVisited[nx][ny] = true;
                    queue.offer(new int[]{nx, ny, nd});
                    System.out.println(nx + " " + ny);
                    unVisitedCount--;
                    isMoved = true;
                    break; // 우선순위를 만족하는 하나의 칸을 찾으면 더 이상 방향 탐색 x
                }
            }

            // [2단계]
            if(!isMoved && unVisitedCount > 0){
                int[][] startDistanceMap = getDistanceMap(cx, cy); // cx, cy에서부터 바다까지의 거리 지도

                // print(distanceMap);

                int targetX = -1;
                int targetY = -1;
                int minDistance = 1000000000;

                // 전체 map을 돌면서 행 번호가 작고, 열 번호가 작은 가장 가까운 바다 칸 찾기
                for(int i = 1; i <= N; i++){
                    for(int j = 1; j <= N; j ++){
                        
                        if(map[i][j] == 0 && !isVisited[i][j] && startDistanceMap[i][j] != -1){
                            // 새로 찾은 칸까지의 거리가 이전 최단거리와 같으면 이전에 찾은 행이 더 적은 것을 사용
                            if(startDistanceMap[i][j] < minDistance){
                                minDistance = startDistanceMap[i][j];
                                targetX = i;
                                targetY = j;
                            }
                        }
                    }
                }

                if(targetX == -1 && targetY == -1){
                    break;
                }

                // 찾은 다음 비어있는 바다 칸에서부터 거리 계산
                int[][] targetDistanceMap = getDistanceMap(targetX, targetY);

                while(cx != targetX || cy != targetY){
                    for(int i = 0; i < 4; i++){
                        int nx = cx + dx2[i];
                        int ny = cy + dy2[i];
                        int nd = dir2[i]; // 방향 갱신

                        if(nx <= 0 || nx > N || ny <= 0 || ny > N){
                            continue;   
                        }

                        if(map[nx][ny] == 0 && (targetDistanceMap[nx][ny] == targetDistanceMap[cx][cy] - 1)){
                            cx = nx;
                            cy = ny;
                            cd = nd;

                            if(!isVisited[nx][ny]){
                                isVisited[nx][ny] = true;
                                unVisitedCount--;
                                System.out.println(nx + " " + ny);
                            }

                            break;
                        }
                    }
                } // 새로운 땅까지 도착

                queue.offer(new int[]{targetX, targetY, cd});
            }
        }
    }

    static int[][] getDistanceMap(int sx, int sy){
        int[][] grid = new int[N + 1][N + 1];

        for(int i = 0; i <= N; i++){
            Arrays.fill(grid[i], -1);
        }

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{sx, sy, 0}); // 0은 초기 위치의 거리
        grid[sx][sy] = 0;

        while(!queue.isEmpty()){
            int[] current = queue.poll();
            int cx = current[0];
            int cy = current[1];
            int cd = current[2]; // 거리

            for(int i = 0; i < 4; i++){
                int nx = cx + dx2[i];
                int ny = cy + dy2[i];

                if(nx <= 0 || nx > N || ny <= 0 || ny > N){
                    continue;
                }

                if(grid[nx][ny] == -1 && map[nx][ny] == 0){
                    grid[nx][ny] = cd + 1;
                    queue.offer(new int[]{nx, ny, grid[nx][ny]});
                }
            }
        } 

        return grid;
    }

    // 디버그용
    static void print(int[][] map){
        
        System.out.println("==========디버그==========");
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