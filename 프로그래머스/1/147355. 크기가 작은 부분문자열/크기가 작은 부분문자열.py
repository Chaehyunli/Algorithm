def solution(t, p):
    
    size = len(p) # p의 자리수
    
    part_numbers = []
    
    for i in range(len(t) - size + 1):
        part_numbers.append(t[i:i+size])
    
    answer = 0
    
    for number in part_numbers:
        if int(number) <= int(p):
            answer += 1
    return answer