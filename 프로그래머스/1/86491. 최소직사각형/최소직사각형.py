def solution(sizes):
    max_w = 0  # 긴 변들 중 가장 큰 값 (지갑의 가로)
    max_h = 0  # 짧은 변들 중 가장 큰 값 (지갑의 세로)
    
    for i in range(len(sizes)):

        current_max = max(sizes[i][0], sizes[i][1])
        current_min = min(sizes[i][0], sizes[i][1])
        
        if current_max > max_w:
            max_w = current_max
            
        if current_min > max_h:
            max_h = current_min
            
    return max_w * max_h