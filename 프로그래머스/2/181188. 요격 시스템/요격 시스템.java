import java.util.*;

class Solution {
    public int solution(int[][] targets) {

        Arrays.sort(targets, (o1, o2) -> {
            if (o1[1] == o2[1]) {
                return Integer.compare(o1[0], o2[0]);
            }
            return Integer.compare(o1[1], o2[1]);
        });

        int answer = 0;
        int lastIntercept = -1; // 마지막 요격 미사일의 위치
        
        for (int[] target : targets) {
            int s = target[0];
            int e = target[1];

            // 현재 미사일의 시작 지점(s)이 마지막 요격 위치보다 크거나 같다면
            // 기존 미사일로는 요격할 수 없으므로 새로 한 발 발사
            if (s >= lastIntercept) {
                answer++;
                lastIntercept = e; // 요격 위치를 현재 미사일의 종료 지점으로 갱신
            }
        }

        return answer;
    }
}