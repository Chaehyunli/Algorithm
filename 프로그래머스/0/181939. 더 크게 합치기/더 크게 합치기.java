class Solution {
    public int solution(int a, int b) {
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        
        sb1.append(a);
        sb1.append(b);
        
        sb2.append(b);
        sb2.append(a);
        
        return Math.max(Integer.parseInt(sb1.toString()),Integer.parseInt(sb2.toString()));
    }
}