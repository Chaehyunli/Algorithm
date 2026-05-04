def solution(price, money, count):
    
    sum = 0
    
    for i in range(1,count + 1):
        sum += i
    
    total = sum * price
    
    if money - total < 0:
        return total - money
    else:
        return 0