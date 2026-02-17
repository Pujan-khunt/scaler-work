import java.util.ArrayList;
import java.util.List;

/**
 * Generate all N digit numbers using only 1 & 2.
 */
public class Main {

    public List<List<Integer>> generate(int n) {
        List<List<Integer>> result = new ArrayList<>();
        helper(result, new ArrayList<>(), n);
        return result;
    }

    private void helper(List<List<Integer>> result, List<Integer> path, int n) {
        if (path.size() == n) {
            result.add(new ArrayList<>(path));
            return;
        }

        // Define all possible choices.
        List<Integer> choices = List.of(1, 2);

        // For every choice: Choose -> Explore -> Unchoose
        for (Integer choice : choices) {
            // Choose
            path.add(choice);

            // Explore
            helper(result, path, n);

            // Unchoose
            path.remove(path.size() - 1);
        }
    }

    public static void main(String[] args) {
        List<List<Integer>> ansList = new ArrayList<>();
        for (List<Integer> ans : ansList) {
            System.out.print(ans.get(0) + ", " + ans.get(1));
        }
        System.out.println();
    }

}
