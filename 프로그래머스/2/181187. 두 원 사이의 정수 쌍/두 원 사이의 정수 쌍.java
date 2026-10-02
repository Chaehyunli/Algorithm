class Solution {
    public long solution(int r1, int r2) {
        long answer = 0;
        
        // long 타입 변환으로 제곱 계산 시 오버플로우 방지
        long r1Sq = (long) r1 * r1;
        long r2Sq = (long) r2 * r2;

        // x좌표 1부터 r2까지 반복
        for (int x = 1; x <= r2; x++) {
            long xSq = (long) x * x;

            // r2 원 안에서의 최대 y값 (내림)
            long yMax = (long) Math.floor(Math.sqrt(r2Sq - xSq));

            // r1 원 밖에서의 최소 y값 (올림)
            long yMin = 0;
            if (x < r1) {
                yMin = (long) Math.ceil(Math.sqrt(r1Sq - xSq));
            }

            // 해당 x좌표에서 조건을 만족하는 y의 개수
            answer += (yMax - yMin + 1);
        }

        return answer * 4;
    }
}