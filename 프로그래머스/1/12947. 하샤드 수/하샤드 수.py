def solution(x):
    digital_sum = 0
    
    for i in str(x):
        digital_sum += int(i)
        
    if x % digital_sum != 0:
        return False
        
    return True