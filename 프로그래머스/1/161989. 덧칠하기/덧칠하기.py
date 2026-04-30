def solution(n, m, section):
    answer = 0
    last_painted = 0  # 마지막으로 칠해진 지점
    
    for s in section:
        # 현재 구역이 마지막으로 칠한 범위 밖에 있다면
        if s > last_painted:
            answer += 1 
            last_painted = s + m - 1  # 현재 구역부터 m만큼 칠함
            
    return answer