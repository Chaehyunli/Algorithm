import java.util.*;

// 전역으로 time 변수 만듬(1 시작)
// [1단계] 공격 준비(100 N id_1 p_1 r_1 id_2 ...)
//      - N 척을 선박이 map에 <Integer, Ship> 으로 넣음 - 선박 번호, 공격력, 재장전 시간, 버전 관리(1부터 시작), 준비되는 다음 시각
//      - 모든 선박을 readyPQ에 넣음(공격력 내림차순, 동률 시 선박 번호 오름차순)
// [2단계] 지원요청(200 id p r)
//      - 새로운 선박을 map에 넣음
//      - 해당 선박을 readyPQ에 넣음
// [3단계] 함포 교체(300 id pw)
//      - map에서 해당 번호의 선박의 공격력을 교체(버전에 1도 추가)
//      - 해당 함포가 사격 가능 상태라면, readyPQ에 변경된 공격력으로 넣어야함
// [4단계] 공격 명령(400)
//      - 발포 arrayList를 만듬
//      - 사격 대기 상태인 선박(readyPQ)를 poll하면서 꺼낸 배의 id를 map에서 조회해서 버전이 일치하면 발포 리스트에 넣음, 일치 X하면 패스
//      - 최대 5척 사격(공격력 내림차순, 동률 시 선박 번호 오름차순으로 공격)
//      - 발포 arrayList의 크기가 5개 될 때까지 반복
//      - 사격 후, 사격 완료한 배를 coolPQ에 넣음(id, 재장전 완료 시간) - 재장전 완료 시간 기준 오름차순
//      - map에서 방금 사격한 배들을 대상으로 준비되는 다음 시각을 현재 시각 + 재장전 시간 해줘야함
//      - 총 피해량, 사격 선박 수, 사격한 선박 번호(공격력 내림차순, 동률 시 선박 번호 오름차순으로 출력)

// 1,2,3,4 중 1개를 수행하고, time++ 하고, coolPQ에서 peek해서 재장전 완료 시간이 같을 때까지 poll해서 readyPQ에 넣음

// map - (Integer, Ship)
// Ship - (선박 번호, 공격력, 재장전 시간, 버전, 준비되는 다음 시각)
// readyPQ - (선박 번호, 공격력, 버전)
// coolPQ - (선박 번호, 재장전 완료 시간)
// 발포 arrayList - (선박 번호, 공격력)

public class Main {

    static int T; // 명령의 수
    static Map<Integer, Ship> map; 
    static PriorityQueue<int[]> readyPQ;
    static PriorityQueue<int[]> coolPQ;

    static class Ship{
        int id; // 선박 번호
        int attackPower; // 공격력
        int reloadTime; // 재장전 시간
        int version; // 버전 - 기본값 1
        int nextReadyTime; // 준비되는 다음 시각 - new Ship 할 때의 time으로 초기화

        public Ship(int id, int attackPower, int reloadTime, int version, int nextRedayTime){
            this.id = id;
            this.attackPower = attackPower;
            this.reloadTime = reloadTime;
            this.version = version;
            this.nextReadyTime = nextReadyTime;
        }
    }

    public static void main(String[] args) {
        // Please write your code here.
        Scanner sc = new Scanner(System.in);

        T = sc.nextInt();

        Map<Integer, Ship> map = new HashMap<>();

        readyPQ = new PriorityQueue<>((a, b) ->{
            if(a[1] == b[1]){
                // 공격력 동률 시, id 기준 오름차순
                return a[0] - b[0];
            }
            // 공격력 기준 내림차순
            return b[1] - a[1];
        });

        coolPQ = new PriorityQueue<>((a, b) -> {
            // 재장전 완료 시각 기준 오름차순
            return a[1] - b[1];
        });

        int time = 1; // 현재 시각

        while(T > 0){
            int command = sc.nextInt();

            switch(command){
                // 공격 준비
                case 100:{
                    int shipCount = sc.nextInt(); // 초기 배의 개수
                    while(shipCount > 0){
                        int id = sc.nextInt();
                        int attackPower = sc.nextInt();
                        int reloadTime = sc.nextInt();

                        Ship ship = new Ship(id, attackPower, reloadTime, 1, time);
                        map.put(id, ship);

                        int[] readyShip = new int[]{id, attackPower, 1};
                        readyPQ.add(readyShip);

                        shipCount--;
                    }
                    break;
                }

                // 지원 요청
                case 200:{
                    int id = sc.nextInt();
                    int attackPower = sc.nextInt();
                    int reloadTime = sc.nextInt();

                    Ship ship = new Ship(id, attackPower, reloadTime, 1, time);
                    map.put(id, ship);

                    int[] readyShip = new int[]{id, attackPower, 1};
                    readyPQ.add(readyShip);

                    break;
                }

                // 합포 교체
                case 300:{
                    int id = sc.nextInt();
                    int newAttackPower = sc.nextInt();

                    // map에서 해당 번호의 선박의 공격력을 교체(버전에 1도 추가)
                    Ship ship = map.get(id);
                    ship.version += 1;
                    ship.attackPower = newAttackPower;

                    // 해당 함포가 사격 가능 상태라면, readyPQ에 변경된 공격력으로 넣어야함
                    if(time >= ship.nextReadyTime){
                        int[] newShip = new int[]{id, ship.attackPower, ship.version};
                        readyPQ.add(newShip);
                    }

                    break;
                }

                // 공격 명령
                case 400:{
                    // 발포 arrayList - (선박 번호, 공격력)
                    ArrayList<int[]> attackList = new ArrayList<>();
                    int totalPower = 0;

                    // 사격 대기 상태인 선박(readyPQ)를 poll하면서 꺼낸 배의 id를 map에서 조회해서 버전이 일치하면 발포 리스트에 넣음, 일치 X하면 패스
                    // 최대 5척 사격(공격력 내림차순, 동률 시 선박 번호 오름차순으로 공격) - readyPQ에서 꺼내는 순
                    // 발포 arrayList의 크기가 5개 될 때까지 반복
                    while(!readyPQ.isEmpty() && attackList.size() < 5){
                        int[] readyShip = readyPQ.poll();
                        int id = readyShip[0];
                        int attackPower = readyShip[1];
                        int version = readyShip[2];
                        
                        // 버전이 일치하지 않으면 패스
                        if(map.get(id).version != version){
                            continue;
                        }

                        totalPower += attackPower;

                        attackList.add(new int[]{id, attackPower});

                        // map에서 방금 사격한 배들을 대상으로 준비되는 다음 시각을 현재 시각 + 재장전 시간 해줘야함
                        // 사격 후, 사격 완료한 배를 coolPQ에 넣음(id, 재장전 완료 시간) - 재장전 완료 시간 기준 오름차순
                        map.get(id).nextReadyTime = time + map.get(id).reloadTime;
                        int[] coolShip = new int[]{id, map.get(id).nextReadyTime};
                        coolPQ.add(coolShip);
                    }

                    // 총 피해량, 사격 선박 수, 사격한 선박 번호(공격력 내림차순, 동률 시 선박 번호 오름차순으로 출력)
                    attackList.sort((a, b) ->{
                        // 공격력 동률 시 선박 번호 오름차순
                        if(a[1] == b[1]){
                            return a[0] - b[0];
                        }

                        // 공격력 내림차순
                        return b[1] - a[1];
                    });

                    // 총 피해량
                    System.out.print(totalPower + " ");

                    // 사격 선박 수
                    System.out.print(attackList.size() + " ");

                    // 사격한 선박 번호
                    for(int i = 0; i < attackList.size(); i++){
                        System.out.print(attackList.get(i)[0] + " ");
                    }
                    System.out.println();

                    break;
                }
            }

            // 시간 처리
            // time++ 하고, coolPQ에서 peek해서 재장전 완료 시간이 같을 때까지 poll해서 readyPQ에 넣음
            time++;
            while(!coolPQ.isEmpty() && coolPQ.peek()[1] <= time){
                int[] cooledShip = coolPQ.poll();
                int id = cooledShip[0]; // id
                int attackPower = map.get(id).attackPower;
                int version = map.get(id).version;

                int[] readyedShip = new int[]{id, attackPower, version};

                readyPQ.add(readyedShip);
            }

            T--;
        }
    }
}