class Solution {
    // 0과 1의 개수를 저장할 배열 (answer[0]: 0의 개수, answer[1]: 1의 개수)
    private int[] answer = new int[2];

    public int[] solution(int[][] arr) {
        int n = arr.length;
        compress(arr, 0, 0, n);
        return answer;
    }

    private void compress(int[][] arr, int x, int y, int size) {
        // 현재 영역이 모두 같은 값인지 확인
        if (isUniform(arr, x, y, size)) {
            answer[arr[x][y]]++; // answer[0] : 0의 개수, answer[1] : 1의 개수
            return;
        }

        // 같지 않다면 4개의 영역으로 분할
        int newSize = size / 2;

        compress(arr, x, y, newSize);                     // 제1사분면
        compress(arr, x, y + newSize, newSize);           // 제2사분면
        compress(arr, x + newSize, y, newSize);           // 제3사분면
        compress(arr, x + newSize, y + newSize, newSize); // 제4사분면
    }

    // 주어진 영역 내부가 모두 같은 값인지 - (x,y)에서 시작해서 size만큼 검사
    private boolean isUniform(int[][] arr, int x, int y, int size) {
        int firstValue = arr[x][y];

        for (int i = x; i < x + size; i++) {
            for (int j = y; j < y + size; j++) {
                if (arr[i][j] != firstValue) {
                    return false;
                }
            }
        }
        return true;
    }
}