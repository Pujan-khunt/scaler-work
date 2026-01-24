import java.util.ArrayList;
import java.util.List;

public class Main {

    static void generate(List<List<Integer>> ansList, List<Integer> currList, int currIdx, int n) {
        if (currIdx == n) {
            ansList.add(new ArrayList<>(currList));
            return;
        }

        currList.add(1);
        generate(ansList, currList, currIdx + 1, n);
        currList.remove(currList.size() - 1);
        currList.add(2);
        generate(ansList, currList, currIdx + 1, n);
        currList.remove(currList.size() - 1);
    }

    static void generate2(int[] curr, int currIdx, int n) {
        if (currIdx == n) {
            for (int val : curr) {
                System.out.print(val + " ");
            }
            System.out.println();
            return;
        }

        curr[currIdx] = 1;
        generate2(curr, currIdx + 1, n);
        curr[currIdx] = 2;
        generate2(curr, currIdx + 1, n);
    }

    public static void main(String[] args) {
        int n = 3;
        List<List<Integer>> ans = new ArrayList<>();

        // generate(ans, new ArrayList<>(), 0, n);
        //
        // for (List<Integer> col : ans) {
        //     for (Integer ele : col) {
        //         System.out.print(ele + " ");
        //     }
        //     System.out.println();
        // }

        generate2(new int[n], 0, n);
    }

}
