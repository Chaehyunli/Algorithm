class Solution {
    
    private int gcd(int a, int b){
        while(b != 0){
            int r = a % b;
            a = b;
            b = r;
        }
        return a;
    }
    
    private boolean cannotDivideAll(int[] array, int gcd) {
        for (int num : array) {
            if (num % gcd == 0) {
                return false; 
            }
        }
        return true;
    }
    
    public int solution(int[] arrayA, int[] arrayB) {
        
        int gcdA = arrayA[0];
        for (int i = 1; i < arrayA.length; i++) {
            gcdA = gcd(gcdA, arrayA[i]);
        }
        
        int gcdB = arrayB[0];
        for (int i = 1; i < arrayB.length; i++) {
            gcdB = gcd(gcdB, arrayB[i]);
        }
        
        int answer = 0;
        
        if (cannotDivideAll(arrayB, gcdA)) {
            answer = Math.max(answer, gcdA);
        }
        
        if (cannotDivideAll(arrayA, gcdB)) {
            answer = Math.max(answer, gcdB);
        }
        
        return answer;
    }
}