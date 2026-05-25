import heapq

def solution(k, score):
    answer = []
    
    k_list = []
    
    for s in score:
        if len(k_list) >= k:
            if s > k_list[0]:
                heapq.heappop(k_list)
                heapq.heappush(k_list,s)
        else:
            heapq.heappush(k_list,s)
        answer.append(k_list[0])
                
    return answer