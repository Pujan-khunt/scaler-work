public class backtracking {

    private static int lps(String s, int low, int high) {
        // Palindrome of a single character string is always the character itself.
        if (low == high) {
            return 1;
        }
        // Case when s = "AA", low = 0, high = 1
        // next iteration low -> low + 1 and high = high - 1
        // result: low = 1, high = 0
        if (low > high) {
            return 0;
        }
        // Prefer to consume characters at both ends to produce the longest palindrome
        if (s.charAt(low) == s.charAt(high)) {
            return 2 + lps(s, low + 1, high - 1);
        }
        return Math.max(lps(s, low + 1, high), lps(s, low, high - 1));
    }

    public static void main(String[] args) {
        String s = "acbadd";
        int answer = lps(s, 0, s.length() - 1);
        System.out.println(answer);
    }
}
