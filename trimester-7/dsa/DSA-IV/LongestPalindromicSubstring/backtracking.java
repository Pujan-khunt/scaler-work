public class backtracking {

    private static int lps(String s, int low, int high) {
        // palindrome of a single character string is the character itself.
        // s = "c", lps would be 1.
        if (low == high) {
            return 1;
        }

        // Case when s = "AA", low = 0, high = 1
        // next iteration low -> low + 1 and high = high - 1
        // result: low = 1, high = 0
        if (low > high) {
            return 0;
        }

        // S is from (low, high). Then the string excluding the ends would be (low + 1, high - 1);
        // The distance for that internal string would be:
        int internalDistance = (high - 1) - (low + 1) + 1;

        // For the entire substring to be a palindrome
        // both ends should be the same and the internal string should be a palindrome.
        // internal string is a palindrome if its length is equal to its longest palindromic
        // substring's length.
        if (s.charAt(low) == s.charAt(high) && lps(s, low + 1, high - 1) == internalDistance) {
            return 2 + internalDistance;
        }

        // Compute lps for both strings where one end is discarded in each of the strings.
        return Math.max(lps(s, low + 1, high), lps(s, low, high - 1));
    }

    public static void main(String[] args) {
        String s = "dcabaqf";
        int low = 0, high = s.length() - 1;
        int ans = lps(s, low, high);
        System.out.println(ans);
    }

}
