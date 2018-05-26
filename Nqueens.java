// this program finds and prints the solutions for n non-attacking queens in a n x n board.
import java.util.*;
public class Nqueens {
 static int n;;
  private static int[] b = new int[n];
  private static int s = 0;
 
  public static void main(String[] args) {
	  Scanner sc = new Scanner(System.in);
	  n = sc.nextInt();
	  b = new int[n];
    int y = 0;
    b[0] = -1;
    while (y >= 0) {
      do {
        b[y]++;
      } while ((b[y] < n) && unsafe(y));
      if (b[y] < n) {
        if (y < n-1) {
          b[++y] = -1;
        } else {
          putboard();
        }
      } else {
        y--;
      }
    }
    sc.close();
  }

  static boolean unsafe(int y) {
    int x = b[y];
    for (int i = 1; i <= y; i++) {
      int t = b[y - i];
      if (t == x ||
          t == x - i ||
          t == x + i) {
        return true;
      }
    }
 
    return false;
  }
 
  public static void putboard() {
    System.out.println("\n\nSolution " + (++s));
    for (int y = 0; y < n; y++) {
      for (int x = 0; x < n; x++) {
        System.out.print((b[y] == x) ? "|Q" : "|_");
      }
      System.out.println("|");
    }
  }
}
