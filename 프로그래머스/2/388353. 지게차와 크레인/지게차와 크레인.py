from collections import deque

def solution(storage, requests):
    # 수정 가능한 2차원 리스트 형태로 변환
    storage = [list(s) for s in storage]
    r = len(storage)
    c = len(storage[0])
    
    # 상하좌우 탐색용 방향 배열
    dx = [-1, 1, 0, 0]
    dy = [0, 0, -1, 1]
    
    for request in requests:
        # 1. 요청 길이가 2일 때 (예: "AA")
        if len(request) == 2:
            target = request[0]
            for i in range(r):
                for j in range(c):
                    if storage[i][j] == target:
                        storage[i][j] = '0'  # '0'은 빈 공간(지워진 칸)을 의미
                        
        # 2. 요청 길이가 1일 때 (예: "A") -> 외부와 연결된(접근 가능한) 해당 알파벳만 제거
        else:
            target = request
            # 외부에서 접근 가능한 칸들을 기록할 배열
            accessible = [[False] * c for _ in range(r)]
            
            # 격자 밖(가상의 테두리)에서 안쪽으로 들어오는 BFS 탐색
            q = deque()
            
            # 가장자리(테두리)에 있는 빈 공간('0')들을 먼저 큐에 삽입
            for i in range(r):
                for j in range(c):
                    # 테두리이면서 이미 지워진 공간이면 통로가 됨
                    if (i == 0 or i == r - 1 or j == 0 or j == c - 1) and storage[i][j] == '0':
                        accessible[i][j] = True
                        q.append((i, j))
            
            # 외부 통로를 통해 안쪽 빈 공간들까지 확장 탐색
            while q:
                x, y = q.popleft()
                for d in range(4):
                    nx, ny = x + dx[d], y + dy[d]
                    if 0 <= nx < r and 0 <= ny < c:
                        if not accessible[nx][ny] and storage[nx][ny] == '0':
                            accessible[nx][ny] = True
                            q.append((nx, ny))
            
            # 지울 대상을 저장할 리스트 (탐색 도중에 바로 지우면 다른 칸 탐색에 영향을 주므로 몰아서 지움)
            to_delete = []
            for i in range(r):
                for j in range(c):
                    if storage[i][j] == target:
                        # 테두리에 바로 인접해 있거나, 외부와 연결된 빈 공간('0')과 인접해 있다면 삭제 대상
                        is_edge = (i == 0 or i == r - 1 or j == 0 or j == c - 1)
                        has_path = False
                        for d in range(4):
                            nx, ny = i + dx[d], j + dy[d]
                            if 0 <= nx < r and 0 <= ny < c and accessible[nx][ny]:
                                has_path = True
                                break
                                
                        if is_edge or has_path:
                            to_delete.append((i, j))
            
            # 확정된 칸들을 한 번에 비우기
            for x, y in to_delete:
                storage[x][y] = '0'
                
    # 3. 모든 요청을 처리한 뒤 남은 컨테이너('0'이 아닌 칸) 개수 세기
    answer = 0
    for i in range(r):
        for j in range(c):
            if storage[i][j] != '0':
                answer += 1
                
    return answer