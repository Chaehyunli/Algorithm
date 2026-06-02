import heapq

def solution(arr):
    buffer = []
    
    for i in range(len(arr)):
        heapq.heappush(buffer, arr[i])
    
    arr.remove(heapq.heappop(buffer))
    
    if len(arr) == 0:
        return [-1]
    
    return arr