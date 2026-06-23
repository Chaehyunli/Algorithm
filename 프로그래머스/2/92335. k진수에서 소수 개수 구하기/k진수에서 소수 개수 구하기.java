import java.util.*;

class Solution {
    public int solution(int n, int k) {
        int answer = 0;
        
        // n을 k진수 문자열로 변환
        String kStr = Integer.toString(n, k);
        
        // '0'이 하나 이상 연속되는 것을 기준으로 문자열을 쪼갬
        String[] tokens = kStr.split("0+");
 
        for (String token : tokens) {
            
            if (token.equals("")){
                continue;
            }
            
            long num = Long.parseLong(token);
            
            if (isPrime(num)) {
                answer++;
            }
        }
        
        return answer;
    }
    
    private boolean isPrime(long num) {
        if (num < 2) return false;
        
        // 2부터 숫자의 제곱근까지 확인
        for (long i = 2; i * i <= num; i++) {
            if (num % i == 0) return false;
        }
        
        return true;
    }
}