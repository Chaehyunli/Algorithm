class Solution {
    
    // x 좌표가 결정되었을 때, 원점과의 거리가 d 이하가 되는 '최대 y 값'을 반환하는 함수
    private long getMaxY(long x, long d) {
        // x^2 + y^2 <= d^2  =>  y = sqrt(d^2 - x^2)
        return (long) Math.sqrt(d * d - x * x);
    }

    public long solution(int k, int d) {
        long answer = 0;
        long maxDistance = (long) d;

        // x 좌표를 0부터 d까지 k 간격으로 증가
        for (long x = 0; x <= maxDistance; x += k) {
            // 현재 x에서 원점과의 거리가 d 이하를 만족하는 최대 y 구하기
            long maxY = getMaxY(x, maxDistance);

            // y는 0부터 maxY 이하까지 k 간격으로 점을 찍을 수 있음 (0 포함이므로 +1)
            answer += (maxY / k) + 1;
        }

        return answer;
    }
}