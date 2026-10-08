import java.util.*;

public class Main {

    static int Q; // 명령의 횟수
    static int N; // 거리의 크기 : 1 ~ N
    static int M; // 가로등의 개수
    static int nextId; // 다음 가로등의 번호

    static PriorityQueue<LampEdge> queue;  // 가로등 사이의 간선의 정보를 기록할 우선순위 큐
    static Map<Integer , Lamp> lampMap; // 가로등 map ( i, lamp -> i번 id의 lamp)

    static class LampEdge{
        Lamp start; // 시작 가로등
        Lamp end; // 종료 가로등
        int value; // 두 가로등 간의 거리
        boolean valid; // 삭제되었는지의 여부

        public LampEdge(Lamp start, Lamp end){
            this.start = start;
            this.end = end;
            this.value = Math.abs(end.location - start.location);
            this.valid = true;
        }
    }

    static class Lamp{
        int id; // 고유 변호
        int location; // 가로드의 위치
        Lamp prev; // 이전 가로등
        Lamp next; // 다음 가로등

        LampEdge prevEdge; // prev -> this
        LampEdge nextEdge; // this -> next

        public Lamp(int id, int location){
            this.id = id;
            this.location = location;
            this.prev = null;
            this.next = null;
            this.prevEdge = null;
            this.nextEdge = null;
        }

        public void addPrev(Lamp prev){
            this.prev = prev;
        }

        public void addNext(Lamp next){
            this.next = next;
        }
    }

    public static void main(String[] args) {
        // Please write your code here.
        Scanner sc = new Scanner(System.in);

        Q = sc.nextInt();

        // 간선의 거리가 큰 것부터, 거리가 같으면 좌표값이 작은 가로등 쌍 선택
        queue = new PriorityQueue<>((a, b)->{
            if(a.value == b.value){
                return a.start.location - b.start.location;
            }

            return b.value - a.value;
        });
        lampMap = new HashMap<>();

        Lamp head = null; // 가장 첫 가로등의 번호
        Lamp bottom = null; // 가장 마지막 가로등의 번호

        while(Q > 0){

            int command = sc.nextInt();

            switch(command){
                // 마을 상태 확인
                case 100: {
                    N = sc.nextInt(); // 거리의 크기 : 1 ~ N
                    M = sc.nextInt(); // 가로등의 개수
                    nextId = M + 1;

                    // 가로등 추가
                    for(int i = 1; i <= M; i++){
                        int location = sc.nextInt();
                        // lampmap에 lamp 넣기
                        lampMap.put(i, new Lamp(i, location));
                        if(i == 1){
                            head = lampMap.get(i);
                        }

                        if(i == M){
                            bottom = lampMap.get(i);
                        }
                    }

                    // 간선 추가
                    for(int i = 1; i < M; i++){

                        Lamp left = lampMap.get(i);
                        Lamp right = lampMap.get(i + 1);

                        // 가로등 연결
                        left.next = right;
                        right.prev = left;

                        // 간선 생성
                        LampEdge edge = new LampEdge(left, right);

                        // 각 가로등에 간선 정보 저장
                        left.nextEdge = edge;
                        right.prevEdge = edge;

                        // 우선순위 큐에 추가
                        queue.offer(edge);
                    }

                    // print(head);
                    // print(queue);

                    break;
                }

                // 가로등 추가
                case 200: {
                    // 가장 먼 간선을 찾기 전에 무효 간선을 제거함
                    while (!queue.isEmpty() && !queue.peek().valid) {
                        queue.poll();
                    }

                    // 우선순위큐에서 가장 먼 간선을 찾음
                    LampEdge oldEdge = queue.poll();

                    Lamp left = oldEdge.start;
                    Lamp right = oldEdge.end;
                    int nextIdx = nextId++;

                    // 두 가로등 중앙의 위치(올림)
                    int nextLocation = (oldEdge.start.location + oldEdge.end.location + 1) / 2;

                    Lamp added = new Lamp(nextIdx, nextLocation);
                    lampMap.put(nextIdx, added);

                    // 연결 리스트 갱신
                    left.next = added;
                    added.prev = left;
                    added.next = right;
                    right.prev = added;

                    LampEdge leftEdge = new LampEdge(left, added);
                    LampEdge rightEdge = new LampEdge(added, right);

                    left.nextEdge = leftEdge;
                    added.prevEdge = leftEdge;
                    added.nextEdge = rightEdge;
                    right.prevEdge = rightEdge;

                    // 쪼개진 두 간선 추가
                    queue.offer(leftEdge);
                    queue.offer(rightEdge);

                    break;
                }

                // 가로등 제거
                case 300: {
                    // 제거할 가로등
                    Lamp current = lampMap.get(sc.nextInt());

                    Lamp prev = current.prev;
                    Lamp next = current.next;

                    // current와 연결된 기존 간선만 정확하게 제거
                    if (current.prevEdge != null) {
                        current.prevEdge.valid = false;
                    }

                    if (current.nextEdge != null) {
                        current.nextEdge.valid = false;
                    }

                    if (prev != null && next != null) {
                        prev.next = next;
                        next.prev = prev;

                        LampEdge edge = new LampEdge(prev, next);
                        prev.nextEdge = edge;
                        next.prevEdge = edge;
                        queue.offer(edge);
                    } else if (prev != null) {
                        prev.next = null;
                        prev.nextEdge = null;
                        bottom = prev;
                    } else if (next != null) {
                        next.prev = null;
                        next.prevEdge = null;
                        head = next;
                    } else {
                        head = null;
                        bottom = null;
                    }

                    lampMap.remove(current.id);
                    M--;
                    break;
                }

                // 최적 위치 계산 - head와 1 / bottom과 N / 우선순위큐에서의 거리 중의 최대가 필요한 전력임
                case 400: {
                    // 가장 먼 간선을 찾기 전에 무효 간선을 제거함
                    while (!queue.isEmpty() && !queue.peek().valid) {
                        queue.poll();
                    }

                    int answer = Math.max(
                        2 * (head.location - 1),
                        2 * (N - bottom.location)
                    );

                    if (!queue.isEmpty()) {
                        answer = Math.max(answer, queue.peek().value);
                    }

                    System.out.println(answer);
                    break;
                }
            }

            Q--;
        }
    }

    static void print(Lamp head){

        Lamp current = head; 
        System.out.println("==========디버깅==========");

        while(current != null){
            System.out.print(current.id + "(" + current.location + ") -> ");
            current = current.next;
        }
        System.out.println();
    }

    static void print(PriorityQueue<LampEdge> queue){
        // temp 복사
        PriorityQueue<LampEdge> temp = new PriorityQueue<>(queue);
        System.out.println("==========디버깅==========");

        while(!temp.isEmpty()){
            LampEdge current = temp.poll();
            System.out.println(current.start.id + "->" + current.end.id + "(" + current.value + ")");
        }
    }
}