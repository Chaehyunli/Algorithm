def solution(wallpaper):
    
    files = []
    
    for i, w in enumerate(wallpaper):
        for j in range(len(w)):
            if w[j] == "#":
                files.append((i,j))
                
    min_r, min_c = float("inf"), float("inf")
    max_r, max_c = float("-inf"), float("-inf")

    for file in files:
        r = file[0]
        c = file[1]
        
        if r < min_r: min_r = r
        if r > max_r: max_r = r
        if c < min_c: min_c = c
        if c > max_c: max_c = c
    
    return [min_r, min_c, max_r + 1, max_c + 1]