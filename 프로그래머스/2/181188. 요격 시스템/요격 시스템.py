def solution(targets):
    
    targets.sort(key=lambda x: (x[1], x[0]))
    
    answer = 0
    last_defensed = -1
    
    for s,e in targets:
        if s >= last_defensed:
            answer += 1
            last_defensed = e
    
    return answer