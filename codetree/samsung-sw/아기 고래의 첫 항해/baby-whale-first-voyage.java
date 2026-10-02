import java.util.*;

public class Main {

    static int N;
    static int[][] map;
    static boolean[][] isVisited;

    // 시계방향 [0:상, 1:우, 2:하, 3:좌]
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};
    static int[] dirOrder = {0, 3, 1, 2}; // 앞, 왼, 오, 뒤 (상대 방향)

    // 문제의 방향 (1:상, 2:하, 3:좌, 4:우) -> 시계방향 인덱스 (0, 2, 3, 1) 변환
    static int[] toClockwise = {0, 0, 2, 3, 1};
    
    // 2단계 이동 우선순위: 좌(3) -> 하(2) -> 우(1) -> 상(0) (시계방향 인덱스 기준)
    static int[] step2ClockwiseOrder = {3, 2, 1, 0};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        if (!sc.hasNextInt()) return;
        N = sc.nextInt();
        int r = sc.nextInt();
        int c = sc.nextInt();
        int d = sc.nextInt(); // 1:상, 2:하, 3:좌, 4:우

        map = new int[N + 1][N + 1];
        isVisited = new boolean[N + 1][N + 1];

        int unvisitedCount = 0;

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                map[i][j] = sc.nextInt();
                if (map[i][j] == 0) {
                    unvisitedCount++;
                } else if (map[i][j] == 1) {
                    isVisited[i][j] = true; // 암초는 미리 방문 처리
                }
            }
        }

        Queue<int[]> queue = new LinkedList<>();
        int startDir = toClockwise[d];

        queue.offer(new int[]{r, c, startDir});
        isVisited[r][c] = true;
        unvisitedCount--;
        
        // [출력] 시작 위치
        System.out.println(r + " " + c);

        while (unvisitedCount > 0 && !queue.isEmpty()) {
            int[] current = queue.poll();
            int cx = current[0];
            int cy = current[1];
            int cd = current[2];

            boolean moved = false;

            // ------------------ 1단계: 인접 탐험 ------------------
            for (int step : dirOrder) {
                int nd = (cd + step) % 4; // 상대 방향 회전

                int nx = cx + dx[nd];
                int ny = cy + dy[nd];

                if (nx >= 1 && nx <= N && ny >= 1 && ny <= N) {
                    if (!isVisited[nx][ny] && map[nx][ny] == 0) {
                        isVisited[nx][ny] = true;
                        unvisitedCount--;
                        
                        // [출력] 이동 칸
                        System.out.println(nx + " " + ny);

                        queue.offer(new int[]{nx, ny, nd});
                        moved = true;
                        break; // 우선순위 높은 방향 1개 찾으면 즉시 이동
                    }
                }
            }

            // ------------------ 2단계: 가장 가까운 미방문 바다로 이동 ------------------
            if (!moved && unvisitedCount > 0) {
                // 1. 현재 위치(cx, cy)에서 BFS로 각 칸까지의 최단거리 계산
                int[][] distFromCurrent = getDistancesFrom(cx, cy);

                int minDist = Integer.MAX_VALUE;
                int targetX = -1, targetY = -1;

                // 행 작은 순 -> 열 작은 순으로 미방문 바다 탐색
                for (int i = 1; i <= N; i++) {
                    for (int j = 1; j <= N; j++) {
                        if (map[i][j] == 0 && !isVisited[i][j] && distFromCurrent[i][j] != -1) {
                            if (distFromCurrent[i][j] < minDist) {
                                minDist = distFromCurrent[i][j];
                                targetX = i;
                                targetY = j;
                            }
                        }
                    }
                }

                if (targetX == -1) break; // 갈 수 있는 미방문 바다가 없음

                // 2. Target 기준 역방향 BFS 1회 수행 (경로 탐색용)
                int[][] distFromTarget = getDistancesFrom(targetX, targetY);

                int currX = cx, currY = cy;
                int finalDir = cd;

                // Target 위치에 도달할 때까지 한 칸씩 이동
                while (currX != targetX || currY != targetY) {
                    for (int dir : step2ClockwiseOrder) {
                        int nx = currX + dx[dir];
                        int ny = currY + dy[dir];

                        if (nx >= 1 && nx <= N && ny >= 1 && ny <= N && map[nx][ny] == 0) {
                            // Target까지의 거리가 1 줄어드는 인접 칸 선택
                            if (distFromTarget[nx][ny] == distFromTarget[currX][currY] - 1) {
                                currX = nx;
                                currY = ny;
                                finalDir = dir;

                                // ★ 미방문 칸에 새로 도달했을 때만 출력 및 방문 처리
                                if (!isVisited[currX][currY]) {
                                    isVisited[currX][currY] = true;
                                    unvisitedCount--;
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

    // 지정된 지점(sx, sy)에서 전체 칸까지의 최단거리를 구하는 BFS
    static int[][] getDistancesFrom(int sx, int sy) {
        int[][] dist = new int[N + 1][N + 1];
        for (int i = 1; i <= N; i++) {
            Arrays.fill(dist[i], -1);
        }

        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{sx, sy});
        dist[sx][sy] = 0;

        while (!q.isEmpty()) {
            int[] curr = q.poll();
            int x = curr[0];
            int y = curr[1];

            for (int d = 0; d < 4; d++) {
                int nx = x + dx[d];
                int ny = y + dy[d];

                if (nx >= 1 && nx <= N && ny >= 1 && ny <= N) {
                    if (map[nx][ny] == 0 && dist[nx][ny] == -1) {
                        dist[nx][ny] = dist[x][y] + 1;
                        q.offer(new int[]{nx, ny});
                    }
                }
            }
        }
        return dist;
    }
}