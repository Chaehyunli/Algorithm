class Solution {
    public int solution(int n) {
        StringBuilder sb = new StringBuilder();
        
        // 1. 10진수 -> 3진수 변환 (나머지를 추가하면 자동으로 뒤집힌 상태가 됨)
        while (n > 0) {
            sb.append(n % 3);
            n /= 3;
        }
        
        // 2. 뒤집힌 3진수 문자열을 10진수 정수로 변환
        // Integer.parseInt(string, radix)를 사용
        return Integer.parseInt(sb.toString(), 3);
    }
}