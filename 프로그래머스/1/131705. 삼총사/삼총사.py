def solution(number):
    
    def combinations(arr, r):
        # nCr 에서 r이 0이면 원소를 안 뽑는 1가지 경우 (빈 리스트)
        if r == 0:
            return [[]]
    
        result = []
        for i in range(len(arr)):
            elem = arr[i]
            # 현재 원소(elem) 뒤에 있는 녀석들 중에서 r-1 개를 뽑는 조합을 재귀로 구함
            for rest in combinations(arr[i + 1:], r - 1):
                result.append([elem] + rest)
            
        return result

    answer = 0
    
    for trio in combinations(number, 3):
        if sum(trio) == 0:
            answer += 1
    
    return answer