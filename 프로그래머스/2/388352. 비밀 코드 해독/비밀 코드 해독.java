class Solution {
    
    private int answer = 0;
    private int[] currentCode = new int[5];
    private int[][] queries;
    private int[] answers;
    
    private void findCombinations(int start, int n, int depth) {
        // 5개의 숫자를 모두 골랐을 때
        if (depth == 5) {
            // 현재 고른 조합이 모든 쿼리 조건을 만족하는지 확인
            if (isValidCode()) {
                answer++;
            }
            return;
        }

        // 조합 생성 (오름차순 보장)
        for (int i = start; i <= n; i++) {
            currentCode[depth] = i;
            findCombinations(i + 1, n, depth + 1);
        }
    }
    
    private boolean isValidCode() {
        for (int i = 0; i < queries.length; i++) {
            int matchCount = 0;
            int[] query = queries[i];

            // currentCode와 query 모두 오름차순 정렬되어 있으므로 투 포인터로 빠르게 비교
            int p1 = 0, p2 = 0;
            while (p1 < 5 && p2 < 5) {
                if (currentCode[p1] == query[p2]) {
                    matchCount++;
                    p1++;
                    p2++;
                } else if (currentCode[p1] < query[p2]) {
                    p1++;
                } else {
                    p2++;
                }
            }

            // 하나라도 시스템이 답한 개수(ans)와 일치하지 않으면 올바른 비밀 코드가 아님
            if (matchCount != answers[i]) {
                return false;
            }
        }
        return true;
    }
    
    public int solution(int n, int[][] q, int[] ans) {
        
        this.queries = q;
        this.answers = ans;
        this.answer = 0;
        
        findCombinations(1, n, 0);
        
        return answer;
    }
}