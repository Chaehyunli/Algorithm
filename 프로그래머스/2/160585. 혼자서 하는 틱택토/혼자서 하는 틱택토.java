class Solution {
    public int solution(String[] board) {
        int countO = 0;
        int countX = 0;
        
        // 1. O와 X의 개수 세기
        for (String row : board) {
            for (int i = 0; i < row.length(); i++) {
                char c = row.charAt(i);
                if (c == 'O') countO++;
                if (c == 'X') countX++;
            }
        }
        
        // 기본 규칙: O는 X보다 같거나 1개 많아야 함
        if (countX > countO || countO > countX + 1) {
            return 0;
        }
        
        boolean oWins = checkWin(board, 'O');
        boolean xWins = checkWin(board, 'X');
        
        // O가 이겼는데 X가 돌을 더 놨거나 (개수가 같아진 경우 에러)
        if (oWins && countO != countX + 1) {
            return 0;
        }
        
        // X가 이겼는데 O가 돌을 더 놨다면 에러
        if (xWins && countO != countX) {
            return 0;
        }
        
        // 둘 다 이기는 상태는 불가능
        if (oWins && xWins) {
            return 0;
        }
        
        return 1;
    }
    
    private boolean checkWin(String[] board, char player) {
        // 가로, 세로 확인
        for (int i = 0; i < 3; i++) {
            if (board[i].charAt(0) == player && board[i].charAt(1) == player && board[i].charAt(2) == player) return true;
            if (board[0].charAt(i) == player && board[1].charAt(i) == player && board[2].charAt(i) == player) return true;
        }
        
        // 대각선 확인
        if (board[0].charAt(0) == player && board[1].charAt(1) == player && board[2].charAt(2) == player) return true;
        if (board[0].charAt(2) == player && board[1].charAt(1) == player && board[2].charAt(0) == player) return true;
        
        return false;
    }
}