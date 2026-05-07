def solution(board, moves):
    
    r_board = [[] for _ in range(len(board))] # 각 열(Column)별의 인형 리스트
    
    reversed_board = board[::-1]
    
    for b in reversed_board:
        for i in range(len(board)):
            if b[i] != 0:
                r_board[i].append(b[i])
            
    basket = []
    answer = 0
    
    for m in moves:
        col_idx = m - 1 # moves는 1부터 시작하므로 인덱스 조정
        
        # 해당 열에 인형이 있다면 뽑기
        if r_board[col_idx]:
            doll = r_board[col_idx].pop() # 가장 위에 있는 인형 꺼내기
            
            # 바구니의 마지막 인형과 같은지 확인
            if basket and basket[-1] == doll:
                basket.pop() # 같으면 제거
                answer += 2  # 인형 2개가 사라짐
            else:
                basket.append(doll) # 다르면 바구니에 추가
    
    return answer