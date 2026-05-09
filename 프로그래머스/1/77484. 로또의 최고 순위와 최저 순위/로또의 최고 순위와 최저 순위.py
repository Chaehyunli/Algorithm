def solution(lottos, win_nums):
    
    min_num = 0
    
    for i in range(6):
        if win_nums[i] in lottos:
            min_num += 1
    
    n_zero = lottos.count(0)
    
    rank = [6, 6, 5, 4, 3, 2, 1]

    return [rank[min_num + n_zero], rank[min_num]]