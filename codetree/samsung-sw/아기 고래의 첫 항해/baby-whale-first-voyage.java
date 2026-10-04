import java.util.*;

public class Main {

    static int N;
    static int[][] map;
    static boolean[][] isVisited;

    // 0: 상, 1: 우, 2: 하, 3:좌 - (dirOrder[i] + cd) % 4처럼 사용하기 위해서는 인덱스가 1 증가할 때마다 90도 돈다는 가정으로 시계방향으로 있어야함
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};

    // 바라보는 방향 우선 순위 - 1: 상, 2: 좌, 3: 우, 4: 하
    static int[] dirOrder = {0, 3, 1, 2};

    // 문제의 방향 - 1: 상, 2: 하, 3: 좌, 4: 우 -> 이걸 시계방향 인덱스와 연결
    static int[] firstOrder = {0, 0, 2, 3, 1};

    // 2단계 탐색 우선 순위 - 좌(3), 하(2), 우(1), 상(0)
    static int[] secondOrder = {3, 2, 1, 0};

    public static void main(String[] args) {
        // Please write your code here.
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        int r = sc.nextInt();
        int c = sc.nextInt();
        int d = sc.nextInt();

        map = new int[N + 1][N + 1];
        isVisited = new boolean[N + 1][N + 1];

        int unVisitedCount = 0; // 전체 미방문 칸의 개수

        for(int i = 1; i <= N; i++){
            for(int j = 1; j <= N; j++){
                map[i][j] = sc.nextInt();

                if(map[i][j] == 0){
                    unVisitedCount++;
                }else if(map[i][j] == 1){
                    isVisited[i][j] = true; // 암초는 방문 처리
                }
            }
        }

        // 1단계 시작 위치 설정
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{r, c, firstOrder[d]});
        isVisited[r][c] = true;
        unVisitedCount--;
        System.out.println(r + " " + c);

        while(!queue.isEmpty() && unVisitedCount > 0){
            int[] current = queue.poll();
            int cx = current[0];
            int cy = current[1];
            int cd = current[2];

            boolean isMoved = false; // 1단계의 흐름에 들어간적 있는지

            // 1단계
            for(int i = 0; i < 4; i++){
                int nd = (dirOrder[i] + cd) % 4; // 현재 바라보는 방향과 바라보는 방향 우선순위를 반영(인덱스에 1을 더할 때마다 90도 된다고 가정)
                int nx = cx + dx[nd];
                int ny = cy + dy[nd];

                if(nx >= 1 && nx <= N && ny >= 1 && ny <= N){
                    if(!isVisited[nx][ny] && map[nx][ny] == 0){
                        isVisited[nx][ny] = true;
                        queue.offer(new int[]{nx, ny, nd}); // 이동하기 전의 고개의 방향을 그대로 사용

                        unVisitedCount--;
                        isMoved = true;
                        System.out.println(nx + " " + ny);
                        break;
                    }
                }
            }

            // 2단계
            if(!isMoved && unVisitedCount > 0){
                int[][] startDistanceMap = getDistanceMap(cx, cy); // 현재 막힌 위치에서 비방문 칸까지의 거리를 계산하는 map
                
                int minDist = Integer.MAX_VALUE;
                int targetX = -1;
                int targetY = -1;

                // 행이 작은 순으로 미방운 칸 탐색 - targetX, targetY에 다음 칸 위치 추가
                for(int i = 1; i <= N; i++){
                    for(int j = 1; j <= N; j++){
                        if(map[i][j] == 0 && !isVisited[i][j] && startDistanceMap[i][j] != -1){
                            if(startDistanceMap[i][j] < minDist){
                                minDist = Math.min(minDist, startDistanceMap[i][j]);
                                targetX = i;
                                targetY = j;
                            }
                        }
                    }
                }

                // 갈 수 있는 칸이 없으면
                if(targetX == -1 && targetY == -1){
                    break;
                }

                int[][] targetDistanceMap = getDistanceMap(targetX, targetY);

                int currX = cx;
                int currY = cy;
                int finalDir = cd;

                while(currX != targetX || currY != targetY){
                    for(int i = 0; i< 4; i++){
                        int dir = secondOrder[i];
                        int nx = currX + dx[dir];
                        int ny = currY + dy[dir];

                        if(nx >= 1 && nx <= N && ny >= 1 && ny <= N && map[nx][ny] == 0){
                            // target까지의 거리가 1 줄어드는 칸인지
                            if(targetDistanceMap[nx][ny] == targetDistanceMap[currX][currY] - 1){
                                currX = nx;
                                currY = ny;
                                finalDir = dir;

                                if(!isVisited[currX][currY]){
                                    isVisited[currX][currY] = true;
                                    unVisitedCount--;
                                    System.out.println(currX + " " + currY);
                                }

                                break;
                            }
                        }
                    }
                }

                // 도착 위치와 갱신된 방향을 Queue에 넣어 다음 1단계 시작
                queue.offer(new int[]{targetX, targetY, finalDir});
            }
        }
    }

    static public int[][] getDistanceMap(int cx, int cy){

        int[][] grid = new int[N + 1][N + 1];

        for(int i = 1; i <= N; i++){
            Arrays.fill(grid[i], -1);
        }

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{cx, cy, 0});
        grid[cx][cy] = 0;

        while(!queue.isEmpty()){
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];
            int d = current[2];

            for(int i = 0; i < 4; i++){
                int nx = x + dx[i];
                int ny = y + dy[i];

                if(nx >= 1 && nx <= N && ny >= 1 && ny <= N){
                    if(grid[nx][ny] == -1 && map[nx][ny] == 0){
                        grid[nx][ny] = d + 1;
                        queue.offer(new int[]{nx, ny, d + 1});
                    }
                }
            }
        }

        return grid;
    }

    static public void printMap(int[][] map){

        System.out.println("==========디버깅 출력==========");

        int row = map[0].length - 1;
        int col = map.length - 1;

        for(int i = 1; i <= row; i++){
            for(int j = 1; j <= col; j++){
                System.out.print(map[i][j] + " ");
            }

            System.out.println();
        }
    }
}