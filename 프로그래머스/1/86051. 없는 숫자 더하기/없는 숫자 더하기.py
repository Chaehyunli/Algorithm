def solution(numbers):
    
    sum = 0
    
    for number in numbers:
        sum += number
        
    answer = 45 - sum
    
    return answer