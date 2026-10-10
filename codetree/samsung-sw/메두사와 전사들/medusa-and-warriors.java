import java.util.*;

// 최단경로를 계산할 때는 맨해튼 거리를 기준
// 메두사가 공원에 최단거리로 도착할 때까지 다음의 4단계를 반복

// [1단계] 메두사의 이동
//      - 메두사와 공원의 위치는 static으로 전역에서 사용
//      - 집부터 공원까지 최단경로로 상, 하, 좌, 우로 이동
//      - 메두사는 도로로만 갈 수 있음(도로는 0, 도로가 아닌 곳은 1)
//      - 메두사가 이동한 칸에 전사가 있을 경우 전사는 메두사에게 공격을 받고 사라짐
//      - 집으로부터 공원까지 도달하는 도로 경로가 없으면 -1 출력 후 종료
//      - 공원까지의 거리 disatnceMap을 만들어서 distanceMap[공][원] < 0 이면 -1 출력

// [2단계] 메두사의 시선
//      - 상, 하, 좌, 우 중 전사를 가장 많이 볼 수 있는 방향을 바라 봄. 그 개수가 동일하다면 상, 하, 좌, 우 순으로 선택
//      - 메두사를 기준으로 map1, map2, map3, map4에 각각 상, 하, 좌, 우를 바라 볼 때 석화되는 땅표시(메두사가 바라보는 방향 - 전사에 의해 가려지는 공간)
//      - map1, map2, map3, map4와 전사의 위치를 비교해 가면서 전사의 수를 셈 -> 바라볼 방향 결정
//      - ArrayList<Warrier>를 돌면서 바라볼 방향에 있는 전사들의 석화 여부를 true변경
//      - 메두사에 의해 석화된 전사의 수 계산

// [3단계] 전사들의 이동
//      - 최대 2번 아래를 수행
//      - ArrayList<Warrier>의 석화 여부가 false인 전사들을 대상으로 상, 하, 좌, 우로 이동(전사들은 비도로도 이동 가능)
//      - BFS + 멘헤튼 거리 계산으로 줄어드는 방향일 때 전사 1칸 이동, 그것이 없으면 break
//      - 이동하면 ArrayList<Warrier>에서 전사의 위치 업데이트
//      - 전사들이 이동한 거리의 합 계산

// [4단계] 전사의 공격
//      - ArrayList<Warrier> 중에서 메두사의 위치와 같은 전사가 있으면 ArrayList<Warrier>에서 제거
//      - 제거해야하는 ArrayList<Warrier> 인덱스 기준 내림차순으로 역순으로 remove -> 정순으로 하면 인덱스가 밀려서 잘못 제거됨
//      - 메두사를 공격한 전사의 수 계산

// 출력

// class Warrier - <전사의 위치 r, 전사의 위치 c, 석화 여부>
// 전사는 ArrayList<Warrier> - <전사의 위치 r, 전사의 위치 c, 석화 여부>

public class Main {

    static int N; // 마을의 크기
    static int M; // 전사의 수
    static int[] monster; // 메두사의 위치 배열
    static int[] park; // 공원의 위치 배열
    static ArrayList<Warrier> warriers; // 전사들의 배열
    static int[][] map; // 지도 배열 - 도로는 0, 비도로는 1
    
    // 두 번째 이동 우선순위: 상, 하, 좌, 우
    static int[] dx1 = {-1, 1, 0, 0};
    static int[] dy1 = {0, 0, -1, 1};

    // 두 번째 이동 우선순위: 좌, 우, 상, 하
    static int[] dx2 = {0, 0, -1, 1};
    static int[] dy2 = {-1, 1, 0, 0};

    static class Warrier{
        int row;
        int col;
        boolean isRocked = false; // 석화 여부

        public Warrier(int row, int col){
            this.row = row;
            this.col = col;
        }
    }

    public static void main(String[] args) {
        // Please write your code here.
        Scanner scanner = new Scanner(System.in);

        N = scanner.nextInt();
        M = scanner.nextInt();
        int sr = scanner.nextInt(); // 메두사의 위치 r
        int sc = scanner.nextInt(); // 메두사의 위치 c
        int er = scanner.nextInt(); // 공원의 위치 r
        int ec = scanner.nextInt(); // 공원의 위치 c

        monster = new int[]{sr, sc};
        park = new int[]{er, ec};
        warriers = new ArrayList<>();
        map = new int[N][N]; // map[0][0] ~ map[N - 1][N - 1]

        // 전사 입력
        while(M > 0){
            int wr = scanner.nextInt();
            int wc = scanner.nextInt();

            Warrier warrier = new Warrier(wr, wc);

            warriers.add(warrier);

            M--;
        }

        // 지도 입력
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                map[i][j] = scanner.nextInt();
            }
        }

        // 메두사가 공원의 위치에 도착할 때까지 반복
        while(monster[0] != park[0] || monster[1] != park[1]){

            int totalWarrierDistance = 0; // 모든 전사가 이동한 거리의 합
            int numRockerWarrier = 0; // 돌이 된 전사의 수
            int attackWarrier = 0; // 메두사를 공격한 전사의 수

            // [1단계] 메두사의 이동
            // 공원에서 시작하는 distanceMap
            int[][] distanceMap = getDistanceMap();

            // 집으로부터 공원까지 도달하는 도로 경로가 없으면 -1 출력 후 종료
            // 공원까지의 거리 disatnceMap을 만들어서 distanceMap[monster[0]][monster[1]] < 0 이면 -1 출력
            if(distanceMap[monster[0]][monster[1]] < 0){
                System.out.println(-1);
                return;
            }

            // 집부터 공원까지 최단경로로 상, 하, 좌, 우로 이동
            int cx = monster[0];
            int cy = monster[1];

            for(int i = 0; i < 4; i++){
                int nx = cx + dx1[i];
                int ny = cy + dy1[i];

                if(nx < 0 || nx >= N || ny < 0 || ny >= N){
                    continue;
                }

                // 거리가 1 줄어드는 칸이면
                if(distanceMap[nx][ny] == distanceMap[cx][cy] -1 && map[nx][ny] == 0){
                    monster[0] = nx;
                    monster[1] = ny;
                    break; // 상 → 하 → 좌 → 우 중 첫 번째 후보 선택
                }
            }

            // 메두사가 공원에 도착하면 바로 종료
            if (monster[0] == park[0] && monster[1] == park[1]) {
                System.out.println(0);
                return;
            }        

            // 메두사가 이동한 칸에 전사가 있을 경우 전사는 메두사에게 공격을 받고 사라짐
            // 인덱스 문제로 인해 역순으로 제거
            for(int i = warriers.size() - 1; i >= 0; i--){
                Warrier w = warriers.get(i);
                if(monster[0] == w.row && monster[1] == w.col){
                    warriers.remove(i);
                }
            }

            // 다음 턴 시작 전 석화 상태 해제
            for (Warrier w : warriers) {
                w.isRocked = false;
            }

            // print(distanceMap);

            // --------------------
            // [2단계] 메두사의 시선
            // 상, 하, 좌, 우 중 전사를 가장 많이 볼 수 있는 방향을 바라 봄. 그 개수가 동일하다면 상, 하, 좌, 우 순으로 선택
            // 1이 위험, 0 이 안전
            int[][] map1 = dangerArea1(); // 상
            int[][] map2 = dangerArea2(); // 하
            int[][] map3 = dangerArea3(); // 좌
            int[][] map4 = dangerArea4(); // 우
            // 메두사를 기준으로 map1, map2, map3, map4에 각각 상, 하, 좌, 우를 바라 볼 때 석화되는 땅표시(메두사가 바라보는 방향 - 전사에 의해 가려지는 공간)
            // map1, map2, map3, map4와 전사의 위치를 비교해 가면서 전사의 수를 셈 -> 바라볼 방향 결정
            
            int[] count = new int[4];

            for(int i = 0; i < warriers.size(); i++){
                int wr = warriers.get(i).row;
                int wc = warriers.get(i).col;

                if(map1[wr][wc] == 1){
                    count[0]++;
                }
                
                if(map2[wr][wc] == 1){
                    count[1]++;
                }

                if(map3[wr][wc] == 1){
                    count[2]++;
                }

                if(map4[wr][wc] == 1){
                    count[3]++;
                }
            }

            int max = -1;
            for(int c : count){
                max = Math.max(max, c);
            }

            int targetMap = -1; // 바라보는 방향 / 0 : 상, 1 : 하, 2 : 좌, 3 : 우
            for(int i = 0; i < 4; i++){
                if(count[i] == max){
                    targetMap = i;
                    break;
                }
            }

            // 메두사에 의해 석화된 전사의 수 계산
            numRockerWarrier = max;

            // ArrayList<Warrier>를 돌면서 바라볼 방향에 있는 전사들의 석화 여부를 true변경
            for(int i = 0; i < warriers.size(); i++){
                int wr = warriers.get(i).row;
                int wc = warriers.get(i).col;

                if(targetMap == 0){
                    if(map1[wr][wc] == 1){
                        warriers.get(i).isRocked = true;
                    }
                }
                
                if(targetMap == 1){
                    if(map2[wr][wc] == 1){
                        warriers.get(i).isRocked = true;
                    }
                }

                if(targetMap == 2){
                    if(map3[wr][wc] == 1){
                        warriers.get(i).isRocked = true;
                    }
                }

                if(targetMap == 3){
                    if(map4[wr][wc] == 1){
                        warriers.get(i).isRocked = true;
                    }
                }
            }

            // 선택한 시선 지도를 저장 -> 3단계에서 전사가 못가는 지역 검증용
            int[][] selectedDangerMap = map1;

            if (targetMap == 1){
                selectedDangerMap = map2;
            }else if(targetMap == 2){
                selectedDangerMap = map3;
            }else if(targetMap == 3){
                selectedDangerMap = map4;
            }

            // [3단계]
            for(Warrier w : warriers){
                int moveCount = 0;
                int wx = w.row;
                int wy = w.col;
                boolean isRocked = w.isRocked;

                int mx = monster[0];
                int my = monster[1];

                if(isRocked){
                    continue;
                }

                // 최대 2번 아래를 수행

                while(moveCount < 2){
                    // ArrayList<Warrier>의 석화 여부가 false인 전사들을 대상으로 상, 하, 좌, 우로 이동(전사들은 비도로도 이동 가능)
                    boolean moved = false;

                    int[] currentDx;
                    int[] currentDy;

                    if(moveCount == 0){
                        currentDx = dx1;
                        currentDy = dy1;
                    }else{
                        currentDx = dx2;
                        currentDy = dy2;
                    }

                    for(int i = 0; i < 4; i++){
                        int nx = wx + currentDx[i];
                        int ny = wy + currentDy[i];

                        if(nx < 0 || nx >= N || ny < 0 || ny >= N){
                            continue;
                        }

                        if(selectedDangerMap[nx][ny] == 1){
                            continue;
                        }
                    
                        int before = Math.abs(mx - wx) + Math.abs(my - wy);
                        int after = Math.abs(mx - nx) + Math.abs(my - ny);

                        // BFS + 멘헤튼 거리 계산으로 줄어드는 방향일 때 전사 1칸 이동, 그것이 없으면 break
                        if(after < before){
                            // 이동하면 ArrayList<Warrier>에서 전사의 위치 업데이트
                            w.row = nx;
                            w.col = ny;

                            // 2번째 이동은 첫번째 이동 이후 위치에서 해야함
                            wx = nx;
                            wy = ny;

                            moveCount++;
                            // 전사들이 이동한 거리의 합 계산
                            totalWarrierDistance++;
                            moved = true;
                            break;
                        }
                    }

                    if(!moved){
                        break;
                    }
                }
            }

            // [4단계] 전사의 공격
            // ArrayList<Warrier> 중에서 메두사의 위치와 같은 전사가 있으면 ArrayList<Warrier>에서 제거
            // 제거해야하는 ArrayList<Warrier> 인덱스 기준 내림차순으로 역순으로 remove -> 정순으로 하면 인덱스가 밀려서 잘못 제거됨
            for (int i = warriers.size() - 1; i >= 0; i--) {
                Warrier w = warriers.get(i);

                if (monster[0] == w.row && monster[1] == w.col) {
                    warriers.remove(i);
                    // 메두사를 공격한 전사의 수 계산
                    attackWarrier++;
                }
            }

            // 출력
            System.out.println(totalWarrierDistance + " " + numRockerWarrier + " " + attackWarrier);
        }      

        // 메두사가 공원에 도착하면 0을 출력하고 프로그램 종료
        System.out.println(0);
    
    }

    // 메두사가 위 시선 볼 때
    static int[][] dangerArea1() {
        int[][] dangerMap = new int[N][N];

        int mr = monster[0];
        int mc = monster[1];

        // 기본 시야 삼각형
        int layer = 1;

        for (int r = mr - 1; r >= 0; r--) {
            int start = Math.max(mc - layer, 0);
            int end = Math.min(mc + layer, N - 1);

            for (int c = start; c <= end; c++) {
                dangerMap[r][c] = 1;
            }

            layer++;
        }

        // 메두사에 가까운 전사부터: 행이 큰 전사부터
        ArrayList<Warrier> ordered = new ArrayList<>(warriers);
        ordered.sort((a, b) -> Integer.compare(b.row, a.row));

        for (Warrier w : ordered) {
            int wr = w.row;
            int wc = w.col;

            // 위쪽 시야 삼각형 밖이라면 무시
            if (wr >= mr || Math.abs(wc - mc) > mr - wr) {
                continue;
            }

            // 이미 앞 전사 때문에 가려졌다면 무시
            if (dangerMap[wr][wc] == 0) {
                continue;
            }

            // 중앙선
            if (wc == mc) {
                for (int r = wr - 1; r >= 0; r--) {
                    dangerMap[r][wc] = 0;
                }
            }

            // 왼쪽
            else if (wc < mc) {
                int w_layer = 1;

                for (int r = wr - 1; r >= 0; r--) {
                    int start = Math.max(wc - w_layer, 0);
                    int end = wc;

                    for (int c = start; c <= end; c++) {
                        dangerMap[r][c] = 0;
                    }

                    w_layer++;
                }
            }

            // 오른쪽
            else {
                int w_layer = 1;

                for (int r = wr - 1; r >= 0; r--) {
                    int start = wc;
                    int end = Math.min(wc + w_layer, N - 1);

                    for (int c = start; c <= end; c++) {
                        dangerMap[r][c] = 0;
                    }

                    w_layer++;
                }
            }
        }

        return dangerMap;
    }

    // 메두사가 아래 시선 볼 때
    static int[][] dangerArea2(){
        int[][] dangerMap = new int[N][N];

        int mr = monster[0];
        int mc = monster[1];

        int layer = 1;
        // 메두사의 초기 시선 계산
        for(int r = mr + 1; r < N; r++){
            int start = Math.max(mc - layer, 0);
            int end = Math.min(mc + layer, N - 1);
            layer += 1;

            for(int c = start; c <= end; c++){
                dangerMap[r][c] = 1;
            }
        }

        // 가까운 전사부터 가림 처리를 해야 함
        ArrayList<Warrier> ordered = new ArrayList<>(warriers);
        ordered.sort(Comparator.comparingInt(w -> w.row));

        // 전사의 가림막은 0으로 다시 덮음
        for(Warrier w : ordered){
            int wr = w.row;
            int wc = w.col;

            // 아래쪽 시야 삼각형 바깥의 전사는 가림막 역할을 하지 않음
            if (wr <= mr || Math.abs(wc - mc) > wr - mr) {
                continue;
            }

            // 가까운 전사에 의해 이미 가려진 전사는 보이지 않으므로 무시
            if (dangerMap[wr][wc] == 0) {
                continue;
            }

            if(wc == mc){
                for(int r = wr + 1; r < N; r++){
                    dangerMap[r][wc] = 0;
                }
            }else if(wc < mc){
                int w_layer = 1;
                for(int r = wr + 1; r < N; r++){
                    int start = Math.max(wc - w_layer, 0);
                    int end = wc;

                    w_layer++;
                    for(int c = start; c <= end; c++){
                        dangerMap[r][c] = 0;
                    }
                }
            }else if(wc > mc){
                int w_layer = 1;
                for(int r = wr + 1; r < N; r++){
                    int start = wc;
                    int end = Math.min(wc + w_layer, N - 1);

                    w_layer++;
                    for(int c = start; c <= end; c++){
                        dangerMap[r][c] = 0;
                    }
                }
            }
        }

        return dangerMap;

    }

    // 메두사가 좌 시선 볼 때
    static int[][] dangerArea3() {
        int[][] dangerMap = new int[N][N];

        int mr = monster[0];
        int mc = monster[1];

        // 기본 시야 삼각형
        int layer = 1;

        for (int c = mc - 1; c >= 0; c--) {
            int start = Math.max(mr - layer, 0);
            int end = Math.min(mr + layer, N - 1);

            for (int r = start; r <= end; r++) {
                dangerMap[r][c] = 1;
            }

            layer++;
        }

        // 메두사에 가까운 전사부터: 열이 큰 전사부터
        ArrayList<Warrier> ordered = new ArrayList<>(warriers);
        ordered.sort((a, b) -> Integer.compare(b.col, a.col));

        for (Warrier w : ordered) {
            int wr = w.row;
            int wc = w.col;

            // 왼쪽 시야 삼각형 밖이라면 무시
            if (wc >= mc || Math.abs(wr - mr) > mc - wc) {
                continue;
            }

            // 이미 앞 전사 때문에 가려졌다면 무시
            if (dangerMap[wr][wc] == 0) {
                continue;
            }

            // 중앙선
            if (wr == mr) {
                for (int c = wc - 1; c >= 0; c--) {
                    dangerMap[wr][c] = 0;
                }
            }

            // 중앙선보다 위
            else if (wr < mr) {
                int w_layer = 1;

                for (int c = wc - 1; c >= 0; c--) {
                    int start = Math.max(wr - w_layer, 0);
                    int end = wr;

                    for (int r = start; r <= end; r++) {
                        dangerMap[r][c] = 0;
                    }

                    w_layer++;
                }
            }

            // 중앙선보다 아래
            else {
                int w_layer = 1;

                for (int c = wc - 1; c >= 0; c--) {
                    int start = wr;
                    int end = Math.min(wr + w_layer, N - 1);

                    for (int r = start; r <= end; r++) {
                        dangerMap[r][c] = 0;
                    }

                    w_layer++;
                }
            }
        }

        return dangerMap;
    }

    // 메두사가 우 시선 볼 때
    static int[][] dangerArea4() {
        int[][] dangerMap = new int[N][N];

        int mr = monster[0];
        int mc = monster[1];

        // 기본 시야 삼각형
        int layer = 1;

        for (int c = mc + 1; c < N; c++) {
            int start = Math.max(mr - layer, 0);
            int end = Math.min(mr + layer, N - 1);

            for (int r = start; r <= end; r++) {
                dangerMap[r][c] = 1;
            }

            layer++;
        }

        // 메두사에 가까운 전사부터: 열이 작은 전사부터
        ArrayList<Warrier> ordered = new ArrayList<>(warriers);
        ordered.sort(Comparator.comparingInt(w -> w.col));

        for (Warrier w : ordered) {
            int wr = w.row;
            int wc = w.col;

            // 오른쪽 시야 삼각형 밖이라면 무시
            if (wc <= mc || Math.abs(wr - mr) > wc - mc) {
                continue;
            }

            // 이미 앞 전사 때문에 가려졌다면 무시
            if (dangerMap[wr][wc] == 0) {
                continue;
            }

            // 중앙선
            if (wr == mr) {
                for (int c = wc + 1; c < N; c++) {
                    dangerMap[wr][c] = 0;
                }
            }

            // 중앙선보다 위
            else if (wr < mr) {
                int w_layer = 1;

                for (int c = wc + 1; c < N; c++) {
                    int start = Math.max(wr - w_layer, 0);
                    int end = wr;

                    for (int r = start; r <= end; r++) {
                        dangerMap[r][c] = 0;
                    }

                    w_layer++;
                }
            }

            // 중앙선보다 아래
            else {
                int w_layer = 1;

                for (int c = wc + 1; c < N; c++) {
                    int start = wr;
                    int end = Math.min(wr + w_layer, N - 1);

                    for (int r = start; r <= end; r++) {
                        dangerMap[r][c] = 0;
                    }

                    w_layer++;
                }
            }
        }

        return dangerMap;
    }

    static int[][] getDistanceMap(){
        int[][] distanceMap = new int[N][N];
        // int sr = monster[0];
        // int sc = monster[1];
        int er = park[0];
        int ec = park[1];

        // distanceMap 전체를 -1로 채움
        for(int i = 0; i < N; i++){
            Arrays.fill(distanceMap[i], -1);
        }

        Queue<int[]> queue = new LinkedList<>();
        distanceMap[er][ec] = 0; // 시작점의 거리는 0
        queue.offer(new int[]{er, ec, 0});

        while(!queue.isEmpty()){
            int[] current = queue.poll();
            int cx = current[0];
            int cy = current[1];
            int cd = current[2];

            // 집부터 공원까지 최단경로로 상, 하, 좌, 우로 이동
            // 메두사는 도로로만 갈 수 있음(도로는 0, 도로가 아닌 곳은 1)
            for(int i = 0; i < 4; i++){
                int nx = cx + dx1[i];
                int ny = cy + dy1[i];

                if(nx < 0 || nx >= N || ny < 0 || ny >= N){
                    continue;
                }
            
                // 아직 미방문 칸이고 도로(0)이면
                if(distanceMap[nx][ny] == -1 && map[nx][ny] == 0){
                    distanceMap[nx][ny] = cd + 1;
                    queue.offer(new int[]{nx, ny, distanceMap[nx][ny]});
                }
            }
        }

        return distanceMap;
    }

    static void print(int[][] map){
        System.out.println("==========디버겅==========");

        int row = map.length;
        int col = map[0].length;

        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                System.out.print(map[i][j] + " ");
            }

            System.out.println();
        }
    }
}