class Solution {
    public int[] solution(String s) {
        int loopCount = 0;
        int removedZeros = 0;
        
        while (!s.equals("1")) {
            loopCount++;
            
            int originalLength = s.length();
            
            // 모든 0 제거
            s = s.replace("0", "");
            int newLength = s.length();
            
            removedZeros += (originalLength - newLength);
            
            s = Integer.toBinaryString(newLength);
        }
        
        return new int[]{loopCount, removedZeros};
    }
}