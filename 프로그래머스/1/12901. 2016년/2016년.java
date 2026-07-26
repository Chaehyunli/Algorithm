class Solution {
    public String solution(int a, int b) {
        // 2016년 각 달의 일수 (윤년이므로 2월은 29일)
        int[] months = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        
        // 1월 1일이 FRI이므로, 나머지가 0일 때 FRI부터 시작
        String[] days = {"FRI", "SAT", "SUN", "MON", "TUE", "WED", "THU"};
        
        int totalDays = 0;
        
        // (a - 1)월까지의 모든 일수를 합산
        for (int i = 0; i < a - 1; i++) {
            totalDays += months[i];
        }
        
        // b일에서 1월 1일(1일차)을 뺀 경과 일수 추가
        totalDays += (b - 1);
        
        // 7로 나눈 나머지에 해당하는 요일 반환
        return days[totalDays % 7];
    }
}