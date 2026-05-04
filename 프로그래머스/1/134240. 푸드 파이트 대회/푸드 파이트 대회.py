def solution(food):
    
    answer = ''
    
    for i in range(1,len(food)):
        
        num = food[i] // 2 
        
        answer += str(i) * num

    return answer + "0" + answer[::-1]