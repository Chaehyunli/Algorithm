def calculate_distance(pre_left, pre_right, number,hand):
    # 키패드 위치 좌표 사전
    keypad = {
        1: (0, 0), 2: (0, 1), 3: (0, 2),
        4: (1, 0), 5: (1, 1), 6: (1, 2),
        7: (2, 0), 8: (2, 1), 9: (2, 2),
        '*': (3, 0), 0: (3, 1), '#': (3, 2)
    }
    
    # 각 위치의 좌표 가져오기
    L_pos = keypad[pre_left]
    R_pos = keypad[pre_right]
    N_pos = keypad[number]
    
    # 맨해튼 거리 계산
    dist_l = abs(L_pos[0] - N_pos[0]) + abs(L_pos[1] - N_pos[1])
    dist_r = abs(R_pos[0] - N_pos[0]) + abs(R_pos[1] - N_pos[1])
    
    if dist_l < dist_r:
        return 'L'
    elif dist_r < dist_l:
        return 'R'
    else:
        # 거리가 같으면 주 손잡이 기준
        return 'L' if hand == 'left' else 'R'
    

def solution(numbers, hand):
    answer = ''
    
    pre_right = '*' # 오른손이 최근에 누른 버튼의 번호
    pre_left = '#' # 왼손이 최근에 누른 버튼의 번호
    
    for number in numbers:
        if number in [1,4,7]:
            pre_left = number
            answer += 'L'
        elif number in [3,6,9]:
            pre_right = number
            answer += 'R'
        else:
            if calculate_distance(pre_left, pre_right, number,hand) == 'R':
                pre_right = number
                answer += 'R'
            else:
                pre_left = number
                answer += 'L'
    
    return answer