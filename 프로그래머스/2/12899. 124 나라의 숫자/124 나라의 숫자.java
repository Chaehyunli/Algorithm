class Solution {
    public String solution(int n) {
        StringBuilder sb = new StringBuilder();
        
        // 124 나라의 숫자에 대응하는 배열 (나머지 0 -> 4, 1 -> 1, 2 -> 2)
        String[] numbers = {"4", "1", "2"};
        
        while (n > 0) {
            int remainder = n % 3;
            n /= 3;
            
            // 나머지가 0 나누어 떨어지면 자릿수 하나를 사용했으므로 몫을 1 감소시킵니다.
            if (remainder == 0) {
                n--;
            }
            
            // 결과를 앞쪽에 계속 붙여줍니다.
            sb.insert(0, numbers[remainder]);
        }
        
        return sb.toString();
    }
}