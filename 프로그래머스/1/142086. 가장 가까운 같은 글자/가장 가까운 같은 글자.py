def solution(s):
    answer = []
    
    last_seen = {}
    
    for i in range(len(s)):
        char = s[i]
        
        if char in last_seen:
            # 현재 인덱스(i) - 마지막 인덱스
            answer.append(i - last_seen[char])
        else:
            # 처음 나온 글자라면 -1
            answer.append(-1)
        
        last_seen[char] = i
    
    return answer