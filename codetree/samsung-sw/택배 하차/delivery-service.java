import java.util.*;

// 1. 택배 투입
//      - 중력

// 2. 택배 1개 하차 (좌측) - 1 ~ N번까지 택배를 돌면서 왼쪽으로 빠질 수 있는지 점검 
//      - 중력
// 3. 택배 1개 하차 (우측)
//      - 중력

// 2, 3 반복

public class Main {

    static int N; // 격자의 크기
    static int M; // 택배의 개수
    static int[][] map; // 1 ~ N까지만 사용

    static class Box {
        int id, h, w;
        int top, left; // top: 가장 위 행, left: 가장 왼쪽 열
        boolean alive = true;

        Box(int id, int h, int w, int top, int left) {
            this.id = id;
            this.h = h;
            this.w = w;
            this.top = top;
            this.left = left;
        }
    }

    static ArrayList<Box> boxes;

    public static void main(String[] args) {
        // Please write your code here.
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        M = sc.nextInt();
        map = new int[N + 1][N + 1];
        boxes = new ArrayList<>();

        // [1단계] 택배 투입
        while(M > 0){
            int id = sc.nextInt(); // 택배의 id
            int h = sc.nextInt(); // 세로
            int w = sc.nextInt(); // 가로
            int inputLocation = sc.nextInt(); // 시작하는 가로 위치

            drop(id, h, w, inputLocation);

            M--;
        }

        // print(map);

        while(true){
            // [2단계] 택배 1개 하차 (좌측)
            Box leftBox = unloadLeft();
            if (leftBox != null) {
                System.out.println(leftBox.id);
                applyGravity();
            }

            // [3단계]
            Box rightBox = unloadRight();
            if (rightBox != null) {
                System.out.println(rightBox.id);
                applyGravity();
            }

            // 남은 택배가 있는지 확인
            boolean exists = false;
            for (Box b : boxes) {
                if (b.alive) {
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                break;
            }
        }       
    }

    // [3단계] 택배 1개 하차 (우측)
    static Box unloadRight() {
        Box selected = null;

        // 우측으로 빠질 수 있는 택배 중 번호가 가장 작은 것 선택
        for (Box b : boxes) {
            if (!b.alive) continue;

            int right = b.left + b.w - 1;
            boolean canExit = true;

            // 택배가 차지하는 모든 행에서,
            // 택배 오른쪽 ~ N열 사이에 다른 택배가 있으면 못 나감
            for (int row = b.top; row < b.top + b.h; row++) {
                for (int col = right + 1; col <= N; col++) {
                    if (map[row][col] != 0) {
                        canExit = false;
                        break;
                    }
                }

                if (!canExit) break;
            }

            if (canExit && (selected == null || b.id < selected.id)) {
                selected = b;
            }
        }

        if (selected == null) return null;

        // 선택된 택배를 map에서 제거
        for (int row = selected.top; row < selected.top + selected.h; row++) {
            for (int col = selected.left; col < selected.left + selected.w; col++) {
                map[row][col] = 0;
            }
        }

        selected.alive = false;
        return selected;
    }

    static boolean canExitRight(Box b) {
        int right = b.left + b.w - 1;

        for (int row = b.top; row < b.top + b.h; row++) {
            for (int col = right + 1; col <= N; col++) {
                if (map[row][col] != 0) {
                    return false;
                }
            }
        }

        return true;
    }

    static void applyGravity() {
        boolean moved;

        do {
            moved = false;

            for (Box b : boxes) {
                if (b.alive && fallToBottom(b)) {
                    moved = true;
                }
            }
        } while (moved);
    }

    static boolean fallToBottom(Box b) {
        int currentBottom = b.top + b.h - 1;
        int landingBottom = N; // 장애물이 없으면 바닥까지

        // 택배의 각 열에서, 현재 택배 바로 아래의 첫 장애물을 찾는다.
        for (int col = b.left; col < b.left + b.w; col++) {
            for (int row = currentBottom + 1; row <= N; row++) {
                if (map[row][col] != 0) {
                    landingBottom = Math.min(landingBottom, row - 1);
                    break;
                }
            }
        }

        // 이미 최종 위치에 있음
        if (landingBottom == currentBottom) {
            return false;
        }

        // 기존 위치 제거
        for (int row = b.top; row <= currentBottom; row++) {
            for (int col = b.left; col < b.left + b.w; col++) {
                map[row][col] = 0;
            }
        }

        // 한 번에 최종 위치로 이동
        b.top = landingBottom - b.h + 1;

        for (int row = b.top; row <= landingBottom; row++) {
            for (int col = b.left; col < b.left + b.w; col++) {
                map[row][col] = b.id;
            }
        }

        return true;
    }

    

    static Box unloadLeft() {
        Box selected = null;

        for (Box b : boxes) {
            if (!b.alive) continue;

            if (canExitLeft(b)) {
                if (selected == null || b.id < selected.id) {
                    selected = b;
                }
            }
        }

        if (selected == null) return null;

        // 맵에서 제거
        for (int row = selected.top; row < selected.top + selected.h; row++) {
            for (int col = selected.left; col < selected.left + selected.w; col++) {
                map[row][col] = 0;
            }
        }

        selected.alive = false;
        return selected;
    }

    static boolean canExitLeft(Box b) {
        for (int row = b.top; row < b.top + b.h; row++) {
            for (int col = 1; col < b.left; col++) {
                if (map[row][col] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    static void drop(int id, int h, int w, int inputLocation){
        // 중력 이후 각 택배의 왼쪽 아래의 좌표 찾기
        int left = inputLocation;
        int bottom = N;

        for(int col = left; col < left + w; col++){
            for(int row = 1; row <= N; row++){
                if(map[row][col] != 0){
                        
                    // 숫자가 작은 것이 더 높은 위치에 있는 것
                    bottom = Math.min(bottom, row - 1);
                    break;
                }
            }
        }

        for(int row = bottom - h + 1; row <= bottom; row++){
            for(int col = left; col < left + w; col++){
                map[row][col] = id;
            }
        }

        boxes.add(new Box(id, h, w, bottom - h + 1, left));
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