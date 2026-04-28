def solution(s, skip, index):
    # 알파벳 리스트
    alphabet = [
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    ]
    
    # skip에 포함되지 않은 문자만으로 리스트 생성
    valid_chars = [char for char in alphabet if char not in skip]
    
    answer = ''
    
    for char in s:
        # 현재 문자가 유효한 리스트 내에서 몇 번째 인덱스인지
        current_idx = valid_chars.index(char)
        
        # index만큼 이동하고, 리스트 길이를 넘어가면 다시 처음으로 돌아오게 함
        new_idx = (current_idx + index) % len(valid_chars)
        
        answer += valid_chars[new_idx]
        
    return answer