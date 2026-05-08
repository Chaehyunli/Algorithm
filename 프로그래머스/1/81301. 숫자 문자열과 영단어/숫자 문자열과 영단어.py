def solution(s):
    
    n = len(s)
    
    dic = {"zero":0,"one":1, "two":2, "three":3, "four":4, "five":5, "six":6, "seven":7, "eight":8, "nine":9}
    
    answer = ""
    
    buffer = ""
    
    for i in range(n):
        try:
            if type(int(s[i])) == int:
                answer += s[i]
        except:
            buffer += s[i]
            
            if dic.get(buffer) is not None:
                answer += str(dic.get(buffer))
                buffer = ""

    
    return int(answer)