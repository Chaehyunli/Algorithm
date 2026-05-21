def solution(sequence, k):
    n = len(sequence)
    start = 0
    end = 0
    current_sum = sequence[0]
    
    # [시작 인덱스, 끝 인덱스, 수열의 길이]를 저장할 변수
    # 최소 길이를 비교해야 하므로 초기 길이는 가장 큰 값(무한대)으로 설정합니다.
    answer = [0, 0, 1000001]
    
    while end < n:
        if current_sum == k:
            current_length = end - start + 1
            # 1. 길이가 더 짧거나
            # 2. 길이는 같지만 시작 인덱스가 더 앞선 경우 (조건상 먼저 나온 것이 무조건 start가 작음)
            if current_length < answer[2]:
                answer = [start, end, current_length]
            
            # 다음 후보를 찾기 위해 end를 이동시킵니다.
            end += 1
            if end < n:
                current_sum += sequence[end]
                
        elif current_sum < k:
            # 합이 부족하므로 범위를 오른쪽으로 확장
            end += 1
            if end < n:
                current_sum += sequence[end]
                
        else:
            # 합이 넘치므로 왼쪽에서 범위를 좁힘
            current_sum -= sequence[start]
            start += 1
            
    return [answer[0], answer[1]]