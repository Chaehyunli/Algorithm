class Solution {
    
    static long[] fibo = new long[100001];
    
    private void cal_fibo(int n){
        
        if(n == 0 || n == 1){
            return;
        }
        
        for(int i = 2; i <= n; i++){
            fibo[i] = (fibo[i - 1] + fibo[i - 2]) % 1234567;
        }
    }
    
    public int solution(int n) {
        fibo[0] = 0;
        fibo[1] = 1;
        
        cal_fibo(n);
        
        return (int) fibo[n];
    }
}