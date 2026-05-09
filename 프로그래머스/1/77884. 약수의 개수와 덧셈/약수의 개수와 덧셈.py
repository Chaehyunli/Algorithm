def isPlus(num):
    count = 0
    
    for i in range(1, int(num**0.5) + 1):
    
        if num % i == 0:
            if i * i == num:
                count += 1
            else:
                count += 2
    
    if count % 2 == 0:
        return True
    else:
        return False
        
def is_perfect_square(num):
    if num < 0:
        return False
    
    i = 0
    while i * i <= num:
        if i * i == num:
            return True
        i += 1
    return False

def solution(left, right):
    answer = 0
    
    for i in range(left, right + 1):
        if isPlus(i):
            answer += i
        else:
            answer -= i
    return answer