public class Solution {
    public int solve(int A, int B) {

        // We fundamentally rely on the mathematical properties of modular arithmetic to solve this
        // in O(1) time complexity.
        // The core requirement is that A % M == B % M.
        // If two numbers yield the exact same remainder when divided by M, their difference must
        // yield a remainder of 0 when divided by M.
        // Mathematically, this translates perfectly to: (A - B) % M == 0.
        // This explicitly implies that M must be a perfect divisor of the mathematical difference
        // between A and B.

        // Because the problem strictly asks for the GREATEST possible positive integer M that
        // satisfies this divisibility condition,
        // and because the absolute greatest divisor of any non-zero integer is undeniably the
        // integer itself,
        // the optimal M is simply the absolute difference between A and B.

        // We first explicitly calculate the raw mathematical difference by logically subtracting B
        // from A.
        int rawDifference = A - B;

        // Since the problem strictly mandates that M must be a positive integer, and our raw
        // subtraction could easily yield a negative integer if B is strictly greater than A,
        // we must rigorously apply the Math.abs utility function to completely eliminate any
        // negative sign and extract the pure magnitude.
        int absoluteGreatestModulus = Math.abs(rawDifference);

        // Having mathematically proven and calculated that this absolute difference is the largest
        // possible valid divisor,
        // we confidently return it to the caller as our definitive optimal answer.
        return absoluteGreatestModulus;
    }
}
