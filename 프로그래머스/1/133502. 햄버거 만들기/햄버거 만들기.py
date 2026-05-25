def solution(ingredient):
    answer = 0
    stack = []
    
    for s in ingredient:
        stack.append(s)
        
        if len(stack) >= 4 and stack[-4:] == [1, 2, 3, 1]:
            
            stack.pop()
            stack.pop()
            stack.pop()
            stack.pop()
            
            answer += 1
            
    return answer