class Solution {
    public String solution(String s) {
        int length = s.length();
        int mid = length / 2;
        
        // 짝수인 경우 가운데 두 글자, 홀수인 경우 가운데 한 글자 반환
        if (length % 2 == 0) {
            return s.substring(mid - 1, mid + 1);
        } else {
            return s.substring(mid, mid + 1);
        }
    }
}