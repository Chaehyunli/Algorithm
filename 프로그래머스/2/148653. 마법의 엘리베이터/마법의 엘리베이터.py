def solution(storey):
    # 자릿수 리스트
    # 맨 앞에 올림이 생길 수 있으니 [0] 추가
    digits = [0] + [int(char) for char in str(storey)]
    
    dic = {0:0, 1:1, 2:2, 3:3, 4:4, 5:5, 6:4, 7:3, 8:2, 9:1}
    
    answer = 0
    
    # 일의 자리부터 거꾸로
    for i in range(len(digits) - 1, -1, -1):
        curr = digits[i]
        
        # 만약 올림처리로 현재 자릿수가 10이 되었다면
        if curr == 10:
            digits[i - 1] += 1  # 앞자리에 1을 더해줌
            continue
            
        answer += dic[curr]
        
        if curr > 5:
            digits[i - 1] += 1  # 앞자리에 1을 더해줌
        elif curr == 5:
            # 딱 5일 때는 앞자리가 5 이상인지 확인하고 올림
            if digits[i - 1] >= 5:
                digits[i - 1] += 1
                
    return answer