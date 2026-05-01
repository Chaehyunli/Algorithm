def solution(survey, choices):
    # 각 지표의 기준이 되는 글자
    standard = ["R", "C", "J", "A"]
    oppose = ["T", "F", "M", "N"]
    scores = [0, 0, 0, 0]
    
    for i, s in enumerate(survey):
        point = choices[i] - 4 
        
        # 질문의 왼쪽 글자가 기준 리스트(standard)의 어디에 있는지 확인
        if s[0] in standard:
            idx = standard.index(s[0])
            scores[idx] += point # 기준 글자가 왼쪽에 있으면 point 그대로 더함
        else:
            idx = oppose.index(s[0])
            scores[idx] -= point # 기준 글자가 오른쪽에 있으면 point를 빼줌
            
    answer = ""
    for i, s in enumerate(scores):
        if s <= 0: # 0보다 작거나 같으면 왼쪽 글자
            answer += standard[i]
        else:
            answer += oppose[i]
            
    return answer