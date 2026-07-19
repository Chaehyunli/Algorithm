class Solution {
    public int solution(int n) {
        // 처음 주어진 수 n의 2진수 기준 1의 개수를 구함
        int targetBitCount = Integer.bitCount(n);
        
        // n보다 큰 수부터 시작하여 1씩 증가시키며 찾음
        int nextNumber = n + 1;
        
        while (true) {
            if (Integer.bitCount(nextNumber) == targetBitCount) {
                return nextNumber;
            }
            nextNumber++;
        }
    }
}