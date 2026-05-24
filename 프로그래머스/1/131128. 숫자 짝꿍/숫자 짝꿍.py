from collections import Counter

def solution(X, Y):
    answer = []
    
    counterX = Counter(X)
    counterY = Counter(Y)
    
    common = counterX & counterY
    
    if not common:
        return "-1"
        
    sorted_keys = sorted(common.keys(), reverse=True)  # 예: ['9', '6', '3', '0']

    result = []
    
    for digit in sorted_keys:
        result.append(digit * common[digit])

    answer = ''.join(result)
    
    if answer[0] == "0":
        return "0"
    
    return answer