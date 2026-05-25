def solution(n, lost, reserve):
    
    # lost 하면서 reserve 한 인원 제거
    actual_lost = list(set(lost) - set(reserve))
    actual_reserve = list(set(reserve) - set(lost))
    
    rent = 0
    
    for i in actual_lost:
        if (i-1) in actual_reserve:
            actual_reserve.remove(i-1)
            rent += 1
        elif (i+1) in actual_reserve:
            actual_reserve.remove(i+1)
            rent += 1
            
    answer = n - len(actual_lost) + rent
    
    return answer