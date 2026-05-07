import java.util.*;

class Solution {
    public int solution(int[][] board, int[] moves) {
        int n = board.length;
        
        // 각 열(Column)별 인형을 담을 Stack 리스트 생성
        List<Stack<Integer>> rBoard = new ArrayList<>();
        
        for (int i = 0; i < n; i++) {
            rBoard.add(new Stack<>());
        }

        // 바닥부터 위로 올라오며 인형을 Stack에 쌓기
        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != 0) {
                    rBoard.get(j).push(board[i][j]);
                }
            }
        }

        // 인형 뽑기
        Stack<Integer> basket = new Stack<>();
        int answer = 0;

        for (int move : moves) {
            int colIdx = move - 1;
            Stack<Integer> column = rBoard.get(colIdx);

            // 해당 열에 인형이 있다면 꺼내기
            if (!column.isEmpty()) {
                int doll = column.pop();

                // 바구니가 비어있지 않고, 맨 위 인형과 방금 뽑은 인형이 같다면
                if (!basket.isEmpty() && basket.peek() == doll) {
                    basket.pop();
                    answer += 2; // 인형 2개 제거
                } else {
                    basket.push(doll);
                }
            }
        }

        return answer;
    }
}