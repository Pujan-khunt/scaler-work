public class npower6 {

    // O(N^4) for 4 recursive calls every iteration
    private static int helper(int[][] A, int startRow, int startCol, int endRow, int endCol,
            int[][][][] dp) {
        if (startRow > endRow || startCol > endCol) {
            return 0;
        }

        if (allOnes(A, startRow, startCol, endRow, endCol)) {
            return (endRow - startRow + 1) * (endCol - startCol + 1);
        }

        if (dp[startRow][startCol][endRow][endCol] != -1) {
            return dp[startRow][startCol][endRow][endCol];
        }

        int excludeRightCol = helper(A, startRow, startCol, endRow, endCol - 1, dp);
        int excludeLeftCol = helper(A, startRow, startCol + 1, endRow, endCol, dp);
        int excludeTopRow = helper(A, startRow + 1, startCol, endRow, endCol, dp);
        int excludeBottomRow = helper(A, startRow, startCol, endRow - 1, endCol, dp);

        return dp[startRow][startCol][endRow][endCol] = Math.max(excludeBottomRow,
                Math.max(excludeLeftCol, Math.max(excludeRightCol, excludeTopRow)));
    }

    // O(N^2) for scanning entire array every iteration
    private static boolean allOnes(int[][] A, int startRow, int startCol, int endRow, int endCol) {
        for (int i = startRow; i <= endRow; i++) {
            for (int j = startCol; j <= endCol; j++) {
                if (A[i][j] == 0)
                    return false;
            }
        }
        return true;
    }



    public static void main(String[] args) {}

}
