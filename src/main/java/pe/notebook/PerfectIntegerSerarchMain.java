package pe.notebook;

public class PerfectIntegerSerarchMain {
    public static void main(String[] args) {
      int answer[] = { 1, 2, 3, 4, 5 };
      int a[] = { 1, 3, 2, 4, 2 };

      for (int i = 0; i < answer.length; i++) {
        // 반복 자랏수를 확인한다.
        System.out.println(answer[i] + " , " + a[i % 2] + " == " + (answer[i] == a[i % 2]));
      }
    }
}
