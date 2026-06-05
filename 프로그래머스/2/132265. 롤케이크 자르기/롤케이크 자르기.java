import java.util.*;

class Solution {
    
    // 롤케이크를 잘랐을 때 양쪽의 토핑 종류 수가 같은지 확인하는 함수
    private boolean isFair(Set<Integer> sister, Map<Integer, Integer> brother) {
        return sister.size() == brother.size();
    }
    
    public int solution(int[] topping) {
        int answer = 0;
        
        // 형과 동생의 주머니를 선언합니다.
        Map<Integer, Integer> brother = new HashMap<>();
        Set<Integer> sister = new HashSet<>();

        for (int t : topping) {
            brother.put(t, brother.getOrDefault(t, 0) + 1);
        }
        
        for (int i = 0; i < topping.length; i++) {
            int currentTopping = topping[i];
            
            // 동생에게 토핑을 추가하고, 형의 주머니에서는 하나 뺌
            sister.add(currentTopping);
            brother.put(currentTopping, brother.get(currentTopping) - 1);
            
            // 형 주머니에 해당 토핑이 0개가 되면 종류에서 완전히 제거
            if (brother.get(currentTopping) == 0) {
                brother.remove(currentTopping);
            }

            if (isFair(sister, brother)) {
                answer++;
            }
        }
        
        return answer;
    }
}