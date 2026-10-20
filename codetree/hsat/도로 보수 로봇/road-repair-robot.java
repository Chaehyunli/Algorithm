import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt(); // 총 구멍의 수
        int K = scanner.nextInt(); // 최대 보수 패치의 수
        int[] positions = new int[N];
        
        for (int i = 0; i < N; i++) {
            positions[i] = scanner.nextInt();
        }

        // Please write your code here.
        Arrays.sort(positions);

        int left = 1;
        int right = positions[N - 1] - positions[0] + 1;
        int answer = right;

        while(left <= right){
            int mid = (left + right) / 2;

            int count = 0;

            // 보수 완료된 범위
            int current = -1;

            for(int position : positions){
                // 이미 보수 완료된 범위에 새로운 구멍이 포함되어 있으면 넘어감
                if(current >= position){
                    continue;
                }

                count++;
                current = position + mid - 1;

                if(count > K){
                    break;
                }
            }

            if(count <= K){
                answer = mid;
                right = mid - 1;
            }else{
                left = mid + 1;
            }
        }

        System.out.println(answer);
        
    }
}