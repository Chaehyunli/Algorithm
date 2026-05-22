import heapq

def solution(m, n, h, w, drops):
    answer = []
    
    maps = [[m * n + 1 for _ in range(n)] for _ in range(m)] # 모든 칸을 m * n + 1 로 초기화
    
    # drops 를 map에 1,2,3... 번호 매기기
    count = 1
    for drop in drops:
        x,y = drop
        maps[x][y] = count
        count += 1
        
    # 1. 가로 방향 최솟값 구하기 (힙 활용)
    row_min = [[0] * (n - w + 1) for _ in range(m)]
    for i in range(m):
        heap = []
        for j in range(n):
            # (값, 인덱스) 형태로 힙에 삽입
            heapq.heappush(heap, (maps[i][j], j))
            if j >= w - 1:
                # 최솟값이 윈도우 범위를 벗어났다면 제거
                while heap[0][1] < j - w + 1:
                    heapq.heappop(heap)
                row_min[i][j - w + 1] = heap[0][0]

    # 2. 세로 방향 최솟값 구하기 (힙 활용)
    max_of_min = -1
    answer_x, answer_y = 0, 0
    
    for j in range(n - w + 1):
        heap = []
        for i in range(m):
            heapq.heappush(heap, (row_min[i][j], i))
            if i >= h - 1:
                while heap[0][1] < i - h + 1:
                    heapq.heappop(heap)
                
                current_min = heap[0][0]
                
                start_x = i - h + 1
                start_y = j

                if current_min > max_of_min:
                    max_of_min = current_min
                    answer_x, answer_y = start_x, start_y
                elif current_min == max_of_min:
                # 값이 같다면, 더 위쪽(x가 작음)이거나, 행이 같으면서 더 왼쪽(y가 작음)일 때만 정답 갱신
                    if start_x < answer_x or (start_x == answer_x and start_y < answer_y):
                        answer_x, answer_y = start_x, start_y

    return [answer_x, answer_y]