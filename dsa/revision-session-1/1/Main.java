
// TODO: Also implement the DP approach of this question.
public class Main {
  public static int f(int N, int currentLength, int clipboardLength) {
    if (currentLength == N) {
      return 0;
    }
    if (currentLength > N) {
      return N + 1;
    }

    int copyAndPaste = 2 + f(N, currentLength * 2, currentLength);
    int directPaste = 1 + f(N, currentLength + clipboardLength, clipboardLength);

    return Math.min(copyAndPaste, directPaste);
  }

  public static void main(String[] args) {
    int a = f(15, 1, 1);
    System.out.println(a);
  }
}
