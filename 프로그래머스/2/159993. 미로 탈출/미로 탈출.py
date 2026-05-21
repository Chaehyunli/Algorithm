from collections import deque

def bfs(start, end, maps):
    # 상, 하, 좌, 우 이동을 위한 방향 배열
    dr = [-1, 1, 0, 0]
    dc = [0, 0, -1, 1]
    
    row_len = len(maps)
    col_len = len(maps[0])
    
    # 방문 여부 및 거리를 저장할 배열 (-1로 초기화)
    visited = [[-1] * col_len for _ in range(row_len)]
    
    queue = deque([start])
    visited[start[0]][start[1]] = 0 # 시작 지점의 거리는 0
    
    while queue:
        r, c = queue.popleft()
        
        # 목적지에 도달했다면 걸린 시간을 즉시 반환
        if (r, c) == end:
            return visited[r][c]
            
        # 4방향 탐색
        for i in range(4):
            nr, nc = r + dr[i], c + dc[i]
            
            # 격자 범위를 벗어나지 않고, 벽('X')이 아니며, 아직 방문하지 않은 경우
            if 0 <= nr < row_len and 0 <= nc < col_len:
                if maps[nr][nc] != 'X' and visited[nr][nc] == -1:
                    visited[nr][nc] = visited[r][c] + 1
                    queue.append((nr, nc))
                    
    return -1

def solution(maps):
    start, lever, end = None, None, None
    
    # S, L, E의 위치(좌표) 찾기
    for r in range(len(maps)):
        for c in range(len(maps[0])):
            if maps[r][c] == 'S':
                start = (r, c)
            elif maps[r][c] == 'L':
                lever = (r, c)
            elif maps[r][c] == 'E':
                end = (r, c)
                
    # 출발지 -> 레버 최단 거리 구하기
    to_lever = bfs(start, lever, maps)
    if to_lever == -1:
        return -1
        
    # 레버 -> 출구 최단 거리 구하기
    to_end = bfs(lever, end, maps)
    if to_end == -1:
        return -1
        
    # 두 거리를 합산한 총 시간 반환
    return to_lever + to_end