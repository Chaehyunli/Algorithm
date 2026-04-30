def solution(keymap, targets):
    answer = []
    
    for target in targets:
        total_sum = 0
        possible = True
        
        for char in target:
            
            min_click = float('inf') 
            
            for key in keymap:
            
                idx = key.find(char)
            
                if idx != -1:
                    # 횟수는 '인덱스 + 1' 이므로 비교해서 최솟값 갱신
                    min_click = min(min_click, idx + 1)
                
            if min_click == float('inf'):
                possible = False
                break
            else:
                total_sum += min_click
                
        if possible:
            answer.append(total_sum)
        else:
            answer.append(-1)
    
    return answer