import java.util.*;

class Solution {
    
    static class Stock{
        int price;
        int location;
        
        Stock(int price, int location){
            this.price = price;
            this.location = location;
        }
    }
    
    public int[] solution(int[] prices) {
        
        int[] answer = new int[prices.length];
        Stack<Stock> stack = new Stack<>();
        
        for(int i = 0; i < prices.length; i++){
            
            // 스택의 top에 있는 주식보다 현재(i) 주식 가격이 떨어졌다면 pop
            while (!stack.isEmpty() && stack.peek().price > prices[i]) {
                Stock top = stack.pop();
                // 떨어지기까지 걸린 시간 = 현재 인덱스 - 들어갔던 인덱스
                answer[top.location] = i - top.location;
            }
            
            // 현재 주식을 스택에 추가
            stack.push(new Stock(prices[i], i));
        }
        
        // 끝까지 가격이 떨어지지 않고 남아있는 주식들 처리
        while (!stack.isEmpty()) {
            Stock top = stack.pop();
            // 끝까지 안 떨어졌으므로 (전체 길이 - 1 - 들어갔던 인덱스)
            answer[top.location] = (prices.length - 1) - top.location;
        }
        
        return answer;
        
    }
}