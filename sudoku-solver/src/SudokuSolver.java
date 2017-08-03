/**
 * Created by fabio.fischer on 03/08/2017.
 */
public class SudokuSolver {

    public int[][] newGame() {
        return null;
    }

    public static int[][] solve(SudokuBoard board) {
        int[][] result = board.getMatrix();

        if (!solve(result, 0, 0)) {
            throw new IllegalArgumentException("Unable to find a solution.");
        }

        return result;
    }

    private static boolean solve(int[][] matrix, int row, int column) {
        if (row == matrix.length) {
            row = SudokuBoard.EMPTY_CELL_MARKER;

            if (++column == matrix.length) {
                return true;
            }
        }

        if (matrix[row][column] != SudokuBoard.EMPTY_CELL_MARKER) {
            return solve(matrix, row + 1, column);
        }

        for (int i = 1; i <= matrix.length; ++i) {
            if (verifyCases(matrix, row, column, i)) {
                matrix[row][column] = i;

                if (solve(matrix, row + 1, column)) {
                    return true;
                }
            }
        }
        matrix[row][column] = 0;
        return false;
    }

    private static boolean verifyCases(int[][] matrix, int row, int column, int value) {
        // Verify Row
        for (int k = 0; k < matrix.length; ++k) {
            if (value == matrix[row][k]) {
                return false;
            }
        }

        // Verify column
        for (int k = 0; k < matrix.length; ++k) {
            if (value == matrix[k][column]) {
                return false;
            }
        }

        int segmentSize = (int)Math.sqrt(matrix.length);

        // Verify segment
        for (int k = 0; k < segmentSize; k++) {
            for (int m = 0; m < segmentSize; m++) {
                if (value == matrix[((row / segmentSize) * segmentSize) + k][((column / segmentSize) * segmentSize) + m]) {
                    return false;
                }
            }
        }

        return true;
    }
}
