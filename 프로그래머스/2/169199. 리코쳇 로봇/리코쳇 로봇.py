from collections import deque

def solution(board):
    answer = 0
    
    R = len(board) # 행의 개수
    C = len(board[0]) # 열의 개수
    
    start_x, start_y = 0, 0
    for r in range(R):
        for c in range(C):
            if board[r][c] == 'R':
                start_x, start_y = r, c
                break
                
    dx = [-1, 1, 0, 0]
    dy = [0, 0, -1, 1]
    
    queue = deque([(start_x, start_y, 0)]) # 큐에 초기 위치를 넣음
    visited = [[False] * C for _ in range(R)]
    visited[start_x][start_y] = True
    
    while queue:
        current = queue.popleft()
        
        x = current[0]
        y = current[1]
        count = current[2]
        
        if board[x][y] == 'G':
            return count
        
        for i in range(4):
            
            nx = x
            ny = y
            
            while True:
                
                mx = nx + dx[i]
                my = ny + dy[i]
                
                if not (0 <= mx < R and 0 <= my < C) or board[mx][my] == 'D':
                    break
                    
                nx = mx
                ny = my
                
            if not visited[nx][ny]:
                visited[nx][ny] = True
                queue.append((nx,ny,count + 1))
    
    return -1