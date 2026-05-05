def count_divisors(n):
    count = 0
    for i in range(1, int(n**0.5) + 1):
        if n % i == 0:
            if i * i == n:  # 제곱수일 경우 (예: 4*4=16)
                count += 1
            else:           # 쌍이 되는 약수가 있을 경우
                count += 2
    return count

def solution(number, limit, power):
    answer = 0
    
    sum = 0
    
    for i in range(1, number + 1):
        curr = count_divisors(i)
        
        if curr > limit:
            sum += power
        else:
            sum += curr
      
    return sum