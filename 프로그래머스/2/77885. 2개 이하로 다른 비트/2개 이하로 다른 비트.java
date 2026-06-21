class Solution {
    public long[] solution(long[] numbers) {
        long[] answer = new long[numbers.length];
        
        for (int i = 0; i < numbers.length; i++) {
            long x = numbers[i];
            
            if (x % 2 == 0) {
                // 짝수인 경우: x + 1
                answer[i] = x + 1;
            } else {
                // 홀수인 경우: 가장 마지막에 있는 "01"을 찾아서 "10"으로 변경
                
                // 가장 오른쪽에 있는 0의 위치
                long zeroCount = Long.numberOfTrailingZeros(~x);
                
                long lastZero = 1L << zeroCount;
                
                // 해당 위치의 0을 1로 바꾸고, 그 오른쪽 비트를 0으로 바꿈
                answer[i] = x + lastZero - (lastZero >> 1);
            }
        }
        
        return answer;
    }
}