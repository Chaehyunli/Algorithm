def solution(s):
    answer = 0
    
    # 반복문을 돌면서 문자열을 확인
    i = 0
    while i < len(s):
        x = s[i]  # 첫 글자 지정
        same = 0  # x와 같은 글자 수
        diff = 0  # x와 다른 글자 수
        
        # i부터 문자열 끝까지 탐색
        while i < len(s):
            if s[i] == x:
                same += 1
            else:
                diff += 1
            
            i += 1 # 다음 글자로 이동
            
            # 두 횟수가 같아지는 순간 멈춤
            if same == diff:
                break
        
        # 한 덩어리가 만들어졌으므로 개수 추가
        answer += 1
        
    return answer