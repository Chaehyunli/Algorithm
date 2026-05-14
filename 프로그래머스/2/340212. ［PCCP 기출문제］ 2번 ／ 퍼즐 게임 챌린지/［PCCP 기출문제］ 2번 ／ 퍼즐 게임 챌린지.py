def solve_time(diffs, times, level):
    total_time = 0
    for i in range(len(diffs)):
        diff = diffs[i]
        time_cur = times[i]
        time_prev = times[i-1] if i > 0 else 0 # 이전 퍼즐 시간
        
        if diff <= level:
            # 숙련도가 충분하면 현재 퍼즐 시간만 소요
            total_time += time_cur
        else:
            # 부족한 만큼 틀림: (현재+이전 시간) * 틀린 횟수 + 현재 시간
            mistakes = diff - level
            total_time += mistakes * (time_cur + time_prev) + time_cur
            
    return total_time

def solution(diffs, times, limit):
    low = 1
    high = max(diffs)
    answer = high
    
    while low <= high:
        mid = (low + high) // 2
        # mid 숙련도로 제한 시간 내에 풀 수 있는지 확인
        if solve_time(diffs, times, mid) <= limit:
            answer = mid  # 일단 기록하고, 더 낮은 숙련도가 있는지 확인
            high = mid - 1
        else:
            low = mid + 1 # 시간이 초과되면 숙련도를 높여야 함
            
    return answer