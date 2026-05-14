def solution(n):
    answer_str = ""
    
    while n > 0:
        re = n % 3
        n = n // 3

        answer_str += str(re) # 나머지를 뒤에 붙이면 자연스럽게 역순이 됨

    return int(answer_str, 3)