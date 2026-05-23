from collections import Counter

def solution(weights):
    
    answer = 0
    
    weight_counts = Counter(weights) # {100 : 1, 180 : 2}
    
    # 1 : 1
    for count in weight_counts.values():
        if count > 1:
            answer += count * (count - 1) // 2
    
    unique_weights = set(weights)
    
    for unique_weight in unique_weights:
        
        # 2 : 3, 1 : 2, 3 : 4
        
        if unique_weight * 3 % 2 == 0 and (unique_weight * 3 // 2) in unique_weights:
            answer += weight_counts[unique_weight] * weight_counts[unique_weight * 3 // 2]
            
        if unique_weight * 2 in unique_weights:
            answer += weight_counts[unique_weight] * weight_counts[unique_weight * 2]
            
        if unique_weight * 4 % 3 == 0 and (unique_weight * 4 // 3) in unique_weights:
            answer += weight_counts[unique_weight] * weight_counts[unique_weight * 4 // 3]
    
    return answer