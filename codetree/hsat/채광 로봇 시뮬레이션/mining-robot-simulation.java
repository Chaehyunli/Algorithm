import java.io.*;
import java.util.*;

public class Main {

    static int[][] grid;
    static final int NEG = -1_000_000_000;

    public static void main(String[] args) throws Exception {
        FastScanner sc = new FastScanner(System.in);

        int n = sc.nextInt(); // 격자 모양의 크기
        int t = sc.nextInt(); // 시간 역행을 하는 시간
        grid = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = sc.nextInt();
            }
        }

        // Please write your code here.

        // (0, 0)에서 (i, j)까지 갈 때 얻을 수 있는 최대 누적값
        int[][] distanceMap1 = getDistanceMap1();

        // (i, j)에서 출발해 (n-1, n-1)까지 갈 때 얻을 수 있는 최대 누적값
        int[][] distanceMap2 = getDistanceMap2();

        // (i, j)에서 정확히 t번 아래/오른쪽으로 이동하며 얻는 최대 추가 이득
        // 시작점 (i, j)의 값은 포함하지 않음
        int[][] rewindGainMap = getRewindGainMap(t);

        // print(distanceMap1);
        // print(distanceMap2);
        // print(rewindGainMap);

        // 시간 역행 장치를 사용하지 않는 경우
        int answer = distanceMap1[n - 1][n - 1];

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {

                // (r, c)에서 정확히 t번 이동할 수 없는 경우
                if (rewindGainMap[r][c] == NEG) {
                    continue;
                }

                /*
                 * 첫 번째 경로: 시작 -> ... -> (r, c) -> ... -> t초 후 위치
                 * 시간 역행: t초 전 위치인 (r, c)로 돌아감
                 * 두 번째 경로: (r, c) -> ... -> 도착점
                 *
                 * 시간 역행 후 (r, c)의 광물/장애물도 다시 적용되므로
                 * distanceMap1[r][c]와 distanceMap2[r][c]에 (r, c)가 각각 포함되는 것이 맞음
                 */
                int total = distanceMap1[r][c]
                        + rewindGainMap[r][c]
                        + distanceMap2[r][c];

                answer = Math.max(answer, total);
            }
        }

        System.out.println(answer);
    }

    // (0, 0) -> (i, j)의 최대 누적값
    static int[][] getDistanceMap1() {
        int n = grid.length;
        int[][] distanceMap = new int[n][n];

        distanceMap[0][0] = grid[0][0];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }

                int best = NEG;

                // 위 칸 -> 현재 칸
                if (i - 1 >= 0) {
                    best = Math.max(best, distanceMap[i - 1][j]);
                }

                // 왼쪽 칸 -> 현재 칸
                if (j - 1 >= 0) {
                    best = Math.max(best, distanceMap[i][j - 1]);
                }

                distanceMap[i][j] = best + grid[i][j];
            }
        }

        return distanceMap;
    }

    // (i, j) -> (n - 1, n - 1)의 최대 누적값
    static int[][] getDistanceMap2() {
        int n = grid.length;
        int[][] distanceMap = new int[n][n];

        distanceMap[n - 1][n - 1] = grid[n - 1][n - 1];

        for (int i = n - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (i == n - 1 && j == n - 1) {
                    continue;
                }

                int best = NEG;

                // 아래 칸에서 역전파
                if (i + 1 < n) {
                    best = Math.max(best, distanceMap[i + 1][j]);
                }

                // 오른쪽 칸에서 역전파
                if (j + 1 < n) {
                    best = Math.max(best, distanceMap[i][j + 1]);
                }

                distanceMap[i][j] = grid[i][j] + best;
            }
        }

        return distanceMap;
    }

    /*
     * distanceMap[r][c]:
     * (r, c)에서 정확히 step번 이동할 때 얻는 최대 추가 이득
     *
     * 현재 위치 (r, c)의 값은 이미 첫 번째 경로에서 얻었으므로 포함하지 않는다.
     * 다음 칸으로 이동할 때부터 해당 칸의 값을 더한다.
     */
    static int[][] getRewindGainMap(int t) {
        int n = grid.length;

        // 0번 이동하면 추가 이득은 0
        int[][] prev = new int[n][n];

        for (int step = 1; step <= t; step++) {
            int[][] curr = new int[n][n];

            for (int i = 0; i < n; i++) {
                Arrays.fill(curr[i], NEG);
            }

            for (int r = n - 1; r >= 0; r--) {
                for (int c = n - 1; c >= 0; c--) {

                    // 아래 칸으로 한 번 이동
                    if (r + 1 < n && prev[r + 1][c] != NEG) {
                        curr[r][c] = Math.max(
                                curr[r][c],
                                grid[r + 1][c] + prev[r + 1][c]
                        );
                    }

                    // 오른쪽 칸으로 한 번 이동
                    if (c + 1 < n && prev[r][c + 1] != NEG) {
                        curr[r][c] = Math.max(
                                curr[r][c],
                                grid[r][c + 1] + prev[r][c + 1]
                        );
                    }
                }
            }

            prev = curr;
        }

        return prev;
    }

    static void print(int[][] map) {
        int n = map.length;

        System.out.println("==========디버깅==========");

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(map[i][j] + " ");
            }

            System.out.println();
        }
    }

    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int pointer = 0;
        private int length = 0;

        FastScanner(InputStream in) {
            this.in = in;
        }

        private int read() throws IOException {
            if (pointer >= length) {
                length = in.read(buffer);
                pointer = 0;

                if (length <= 0) {
                    return -1;
                }
            }

            return buffer[pointer++];
        }

        int nextInt() throws IOException {
            int ch;

            do {
                ch = read();
            } while (ch <= ' ');

            int sign = 1;

            if (ch == '-') {
                sign = -1;
                ch = read();
            }

            int value = 0;

            while (ch > ' ') {
                value = value * 10 + (ch - '0');
                ch = read();
            }

            return value * sign;
        }
    }
}