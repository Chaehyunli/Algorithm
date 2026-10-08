import java.util.*;

// 1. 미생물 투입
//      - 만약 영역에 다른 미생물이 있으면 새로 투입된 미생물이 잡아먹음
//      - 기존에 있던 미생물 A가 새로운 미생물 B에 의해서 둘 이상으로 나누어지면 미생물 A 모두 삭제

// 2. 배양 용기 이동
//      - 가장 넓은 영역을 차지한 / 가장 먼저 투입된 미생물 무리부터 새 배양 용기로 이동
//      - 이동한 미생물의 형태는 유지
//      - x좌표가 작은 위치 / x좌표가 같은 경우 y 좌표가 작은 위치에 미생물 위치
//      - 옮길 수 없는 미생물 무리는 삭제

// 3. 실험 결과 기록
//      - 각 미생물 무리끼리 비교하면서 맞닿은 면이 있는지 탐색
//      - 맞달았다고 판정한 미생물 무리의 영역의 넓이를 서로 곱해 성과에 더해 출력

public class Main {

    static int N; // 배양 용기의 칸수 : (1, 1) -> (N, N)
    static int Q; // 실험의 횟수(들어오는 미생물의 횟수)
    static int[][] map; // 처음 배양 용기

    public static void main(String[] args) {
        // Please write your code here.
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        Q = sc.nextInt();

        map = new int[N][N];

        int count = 1; // 미생물의 식별 번호

        while(Q > 0){

            // [1단계] 
            int r1 = sc.nextInt();
            int c1 = sc.nextInt();
            int r2 = sc.nextInt();
            int c2 = sc.nextInt();

            // 식별 번호 count의 미생물을 넣음
            for(int i = r1; i < r2; i++){
                for(int j = c1; j < c2; j++){
                    map[i][j] = count;
                }
            }
            
            // 이번에 들어온 미생물(count) 제외 나머지 미생물들 대상으로 분할 검사
            for(int i = 1; i < count; i++){
                // 식별 변호 i인 미생물의 분할여부 판단
                if(isDivided(i)){
                    for(int r = 0; r < N; r++){
                        for(int c = 0; c < N; c++){
                            // 분할 된 미생물의 위치 제거(0 표기)
                            if(map[r][c] == i){
                                map[r][c] = 0;
                            }
                        }
                    }
                }
            }

            // 남은 미생물들의 상대적인 위치 정보 저장
            Map<Integer, ArrayList<int[]>> locations = new HashMap<>();
            for(int r = 0; r < N; r++){
                for(int c = 0; c < N; c++){
                    if(map[r][c] == 0){
                        continue;
                    }

                    ArrayList<int[]> list = locations.get(map[r][c]);

                    if(list == null){
                        list = new ArrayList<>();
                        locations.put(map[r][c], list);
                    }

                    list.add(new int[]{r, c}); // 실제 미생물의 절대 위치
                }
            }

            // print(locations);

            // [2단계]
            map = moveMicrobes(locations);

            // [3단계]
            long score = calculateScore(count);
            System.out.println(score);

            count++;
            Q--;
        }
        
    }

    // [3단계]
    static long calculateScore(int maxId) {
        // 각 미생물의 넓이 배열
        int[] area = new int[maxId + 1];
        boolean[][] adjacent = new boolean[maxId + 1][maxId + 1];

        // 1. 각 미생물의 넓이 계산
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                int id = map[r][c];

                if (id != 0) {
                    area[id]++;
                }
            }
        }

        // 2. 오른쪽, 아래만 확인해서 인접 쌍 기록
        int[] dr = {0, 1};
        int[] dc = {1, 0};

        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                int a = map[r][c];

                if (a == 0) {
                    continue;
                }

                for (int d = 0; d < 2; d++) {
                    int nr = r + dr[d];
                    int nc = c + dc[d];

                    if (nr >= N || nc >= N) {
                        continue;
                    }

                    int b = map[nr][nc];

                    if (b == 0 || a == b) {
                        continue;
                    }

                    // (a, b), (b, a)를 하나의 쌍으로 통일
                    int small = Math.min(a, b);
                    int large = Math.max(a, b);

                    adjacent[small][large] = true;
                }
            }
        }

        // 3. 서로 인접한 미생물 쌍마다 넓이 곱을 한 번만 더함
        long score = 0;

        for (int a = 1; a <= maxId; a++) {
            for (int b = a + 1; b <= maxId; b++) {
                if (adjacent[a][b]) {
                    score += (long) area[a] * area[b];
                }
            }
        }

        return score;
    }

    // [2단계]
    static int[][] moveMicrobes(Map<Integer, ArrayList<int[]>> locations) {
        int[][] nextMap = new int[N][N];

        // 1. 넓이 내림차순, ID 오름차순 정렬
        ArrayList<Integer> ids = new ArrayList<>(locations.keySet());

        ids.sort((a, b) -> {
            int areaA = locations.get(a).size();
            int areaB = locations.get(b).size();

            if (areaA != areaB) {
                return Integer.compare(areaB, areaA); // 넓이 큰 것 먼저
            }

            return Integer.compare(a, b); // ID 작은 것 먼저
        });

        // 2. 우선순위 순으로 하나씩 새 배양 용기에 배치
        for (int id : ids) {
            ArrayList<int[]> cells = locations.get(id);

            // 해당 미생물의 좌상단 기준점 찾기
            int minR = N;
            int minC = N;

            for (int[] cell : cells) {
                minR = Math.min(minR, cell[0]);
                minC = Math.min(minC, cell[1]);
            }

            // 절대좌표 -> 상대좌표 변환
            ArrayList<int[]> shape = new ArrayList<>();

            for (int[] cell : cells) {
                int relativeR = cell[0] - minR;
                int relativeC = cell[1] - minC;

                shape.add(new int[]{relativeR, relativeC});
            }

            boolean placed = false;

            // x(c)가 작은 위치 우선
            for (int baseR = 0; baseR < N; baseR++) {
                // x가 같다면 y(r)가 작은 위치 우선
                for (int baseC = 0; baseC < N; baseC++) {
                    boolean canPlace = true;

                    // 이 기준점에 모양 전체를 놓을 수 있는지 검사
                    for (int[] pos : shape) {
                        int nr = baseR + pos[0];
                        int nc = baseC + pos[1];

                        if (nr < 0 || nr >= N || nc < 0 || nc >= N) {
                            canPlace = false;
                            break;
                        }

                        if (nextMap[nr][nc] != 0) {
                            canPlace = false;
                            break;
                        }
                    }

                    if (!canPlace) {
                        continue;
                    }

                    // 가능한 첫 위치이므로 실제 배치
                    for (int[] pos : shape) {
                        int nr = baseR + pos[0];
                        int nc = baseC + pos[1];

                        nextMap[nr][nc] = id;
                    }

                    placed = true;
                    break;
                }

                if (placed) {
                    break;
                }
            }

            // placed == false면 놓을 수 없으므로 아무것도 하지 않음 = 소멸
        }

        return nextMap;
    }

    static boolean isDivided(int id){
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] isVisited = new boolean[N][N];

        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, -1, 0, 1};

        int groupCount = 0;

        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){

                // 다른 id 번호의 미생물이거나 방문한적 있으면
                if(map[i][j] != id || isVisited[i][j]){
                    continue;
                }

                groupCount++;

                if(groupCount >= 2){
                    return true;
                }
                
                queue.offer(new int[]{i, j});
                isVisited[i][j] = true;

                while(!queue.isEmpty()){
                    int[] current = queue.poll();
                    int cx = current[0];
                    int cy = current[1];

                    for(int d = 0; d < 4; d++){
                        int nx = cx + dx[d];
                        int ny = cy + dy[d];

                        if(nx >= 0 && nx < N && ny >= 0 && ny < N){
                            if(!isVisited[nx][ny] && map[nx][ny] == id){
                                isVisited[nx][ny] = true;
                                queue.offer(new int[]{nx, ny});
                            }
                        }
                    }   
                }            
            }
        }

        return false;
    }

    static void print(Map<Integer, ArrayList<int[]>> locations){

        for(int key : locations.keySet()){
            ArrayList<int[]> list = locations.get(key);
            int size = list.size();
            System.out.println(key);
            for(int i = 0; i < size; i++){
                System.out.print("[" + list.get(i)[0] + "," + list.get(i)[1] + "] ");
            }

            System.out.println();
        }
    }
}