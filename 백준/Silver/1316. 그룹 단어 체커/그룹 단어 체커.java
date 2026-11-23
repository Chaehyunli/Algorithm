import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        sc.nextLine();

        int count = 0;

        for(int i = 0; i < N; i++){

            String word = sc.nextLine();

            if(checkWord(word)){
                count++;
            }
        }

        System.out.println(count);
    }

    static boolean checkWord(String word){

        Set<Character> set = new HashSet<>();

        char pre = word.charAt(0);
        set.add(pre);

        for(int i = 1; i < word.length(); i++){
            char c = word.charAt(i);

            if (c != pre) { // 이전 문자와 다른 새 문자가 시작될 때만 검사
                if (set.contains(c)) {
                    return false; // 이전에 이미 나왔던 문자가 떨어져서 다시 등장
                }
                set.add(c);
                pre = c;
            }
        }

        return true;
    }


}
