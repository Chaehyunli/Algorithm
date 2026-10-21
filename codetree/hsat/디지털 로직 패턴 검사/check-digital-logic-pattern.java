import java.util.*;

public class Main {

    static Map<Long, Integer> map;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String S = scanner.next();
        int K = scanner.nextInt(); // K 길이 이상 패턴이
        int M = scanner.nextInt(); // M 번 이상 반복
        // Please write your code here.

        map = new HashMap<>();

        int size = S.length();

        long target = 0;

        for(int i = 0; i < size - K + 1; i++){
            // substring을 2진수로 읽어서 저장함
            target = Long.parseLong(S.substring(i, i + K), 2);
            map.put(target, map.getOrDefault(target, 0) + 1);

            if(map.get(target) >= M){
                System.out.println(1);
                return;
            }
        }

        System.out.println(0);
        
    }
}