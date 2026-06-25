class Solution {
    
    private int[][] board;
    
    private int rotate(int[] query, int[][] board){
        
        int startX = query[0];
        int startY = query[1];
        int endX = query[2];
        int endY = query[3];
        
        int buffer = board[startX][startY];
        int min = buffer;
        
        // 왼쪽 테두리
        for (int i = startX; i < endX; i++) {
            board[i][startY] = board[i + 1][startY];
            min = Math.min(min, board[i][startY]);
        }
        
        // 아래쪽 테두리
        for (int i = startY; i < endY; i++) {
            board[endX][i] = board[endX][i + 1];
            min = Math.min(min, board[endX][i]);
        }
        
        // 오른쪽 테두리
        for (int i = endX; i > startX; i--) {
            board[i][endY] = board[i - 1][endY];
            min = Math.min(min, board[i][endY]);
        }
        
        // 위쪽 테두리
        for (int i = endY; i > startY; i--) {
            board[startX][i] = board[startX][i - 1];
            min = Math.min(min, board[startX][i]);
        }
        
        // buffer 값을 원래 시작점 바로 오른쪽 칸에 대입
        board[startX][startY + 1] = buffer;
        
        return min;
    }
    
    public int[] solution(int rows, int columns, int[][] queries) {
        
        int[] answer = new int[queries.length];
        
        board = new int[rows + 1][columns + 1];
        
        for(int i = 1; i <= rows; i++){
            for(int j = 1; j <= columns; j++){
                board[i][j] = (i - 1) * columns + j;
            }
        }
        
        int indx = 0;
        for(int[] query : queries){
            
            int min = rotate(query, board);
            
            answer[indx++] = min;
        }
        
        return answer;
    }
}