def solution(babbling):
    
    def isAvailable(word):
        
        can_speak = ["aya", "ye", "woo", "ma"]
        
        # 같은 발음이 연속해서 나오는지
        for speak in can_speak:
            if speak * 2 in word:  
                return False       
        
        # 발음할 수 있는 단어들을 공백으로 치환
        for speak in can_speak:
            word = word.replace(speak, " ")
            
        # 공백을 모두 지웠을 때 아무것도 남지 않는다면 완전히 발음 가능한 단어
        if word.strip() == "":
            return True
        else:
            return False
    
    answer = 0
    
    for word in babbling:
        if isAvailable(word):
            answer += 1
    
    return answer