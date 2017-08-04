import java.util.Random;

/**
 * Created by fabio.fischer on 03/08/2017.
 */
public class Sudoku {

    public static int[][] newGame() {
        // Populate an empty matrix
        int[][] matrix = initMatrix();

        // Randomize values
        randomizeMatrix(matrix);

        // Strike some elements out
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                strikeOut(matrix, i, j);
            }
        }

        return matrix;
    }

    private static int[][] initMatrix(){
        int [][] matrix = new int[SudokuBoard.MATRIX_SIZE][SudokuBoard.MATRIX_SIZE];
        int aux = 1, num;

        for (int i = 0; i < matrix.length; i++) {
            num = aux;
            for (int j = 0; j < matrix.length; j++) {
                if (num > matrix.length) {
                    num = 1;
                    matrix[i][j] = num;
                    num++;
                } else {
                    matrix[i][j] = num;
                    num++;
                }
            }
            aux = num + SudokuBoard.MATRIX_SEGMENT_SIZE;
            if (num == (matrix.length + 1)) {
                aux = SudokuBoard.MATRIX_SEGMENT_SIZE + 1;
            }
            if (aux > matrix.length) {
                aux = (aux % matrix.length) + 1;
            }
        }

        return matrix;
    }

    private static void randomizeMatrix(int[][] matrix) {
        randomizeSegment(matrix, true);
        randomizeSegment(matrix, false);

        Random rand = new Random();
        int[] segments = new int[]{0, 3, 6};
        int elem1, elem2, index = 0;

        do {
            elem1 = segments[rand.nextInt(segments.length)];

            do {
                elem2 = segments[rand.nextInt(segments.length)];
            } while (elem1 == elem2);

            if (index == 0) {
                rowChange(matrix, elem1, elem2);
            } else {
                columnChange(matrix, elem1, elem2);
            }
        } while((index++) < 2);
    }

    private static void randomizeSegment(int[][] matrix, boolean row) {
        Random rand = new Random();
        int elem1, elem2;
        int max = 2, min = 0;

        for (int i = 0; i < SudokuBoard.MATRIX_SEGMENT_SIZE; i++) {
            elem1 = rand.nextInt(max - min + 1) + min;

            do {
                elem2 = rand.nextInt(max - min + 1) + min;
            } while (elem1 == elem2);

            max += SudokuBoard.MATRIX_SEGMENT_SIZE;
            min += SudokuBoard.MATRIX_SEGMENT_SIZE;

            if (row) {
                permuteRow(matrix, elem1, elem2);
            } else {
                permuteColumn(matrix, elem1, elem2);
            }
        }
    }

    private static void permuteRow(int[][] matrix, int elem1,int elem2) {
        int temp;
        for (int i = 0; i < matrix.length; i++) {
            temp = matrix[elem1][i];
            matrix[elem1][i] = matrix[elem2][i];
            matrix[elem2][i] = temp;
        }
    }

    private static void permuteColumn(int[][] matrix, int elem1,int elem2) {
        int temp;
        for (int i = 0; i < matrix.length; i++) {
            temp = matrix[i][elem1];
            matrix[i][elem1] = matrix[i][elem2];
            matrix[i][elem2] = temp;
        }
    }

    private static void rowChange(int[][] matrix, int elem1,int elem2) {
        int temp;
        for (int i = 1; i <= SudokuBoard.MATRIX_SEGMENT_SIZE; i++) {
            for (int j = 0; j < matrix.length; j++) {
                temp = matrix[elem1][j];
                matrix[elem1][j] = matrix[elem2][j];
                matrix[elem2][j] = temp;
            }
            elem1++;
            elem2++;
        }
    }

    private static void columnChange(int[][] matrix, int elem1,int elem2) {
        int temp;
        for (int i = 1; i <= SudokuBoard.MATRIX_SEGMENT_SIZE; i++) {
            for (int j = 0; j < matrix.length; j++) {
                temp = matrix[j][elem1];
                matrix[j][elem1] = matrix[j][elem2];
                matrix[j][elem2] = temp;
            }
            elem1++;
            elem2++;
        }
    }

    private static void strikeOut(int[][] matrix, int elem1,int elem2) {
        boolean flag;
        int count = matrix.length;

        for (int i = 1; i <= matrix.length; i++) {
            flag = true;
            for (int j = 0; j < matrix.length; j++) {
                if (j != elem2) {
                    if (i == matrix[elem1][j]) {
                        flag = false;
                        break;
                    }
                }
            }
            if (flag) {
                for (int k = 0; k < matrix.length; k++) {
                    if (k != elem1) {
                        if (i == matrix[k][elem2]) {
                            flag = false;
                            break;
                        }
                    }
                }
            }
            if (flag) {
                for (int j = (elem1 - (elem1 % SudokuBoard.MATRIX_SEGMENT_SIZE));
                     j <= (elem1 + (2 - (elem1 % SudokuBoard.MATRIX_SEGMENT_SIZE)));
                     j++)
                {
                    for (int k = (elem2 - (elem2 % SudokuBoard.MATRIX_SEGMENT_SIZE));
                         k <= (elem2 + (2 - (elem2 % SudokuBoard.MATRIX_SEGMENT_SIZE)));
                         k++)
                    {
                        if (j != elem1 && k != elem2) {
                            if (i == matrix[j][k]) {
                                flag = false;
                                break;
                            }
                        }
                    }
                }
            }

            count = (!flag) ? count - 1 : count;
        }

        matrix[elem1][elem2] = (count == 1) ? 0 : matrix[elem1][elem2];
    }


    public static int[][] solve(SudokuBoard board) {
        int[][] result = board.getMatrix();

        if (!solve(result, 0, 0)) {
            throw new IllegalArgumentException("Unable to find a solution.");
        }

        return result;
    }

    private static boolean solve(int[][] matrix, int row, int column) {
        if (row == SudokuBoard.MATRIX_SIZE) {
            row = SudokuBoard.EMPTY_CELL_MARKER;

            if (++column == SudokuBoard.MATRIX_SIZE) {
                return true;
            }
        }
        if (matrix[row][column] != SudokuBoard.EMPTY_CELL_MARKER) {
            return solve(matrix, row + 1, column);
        }
        for (int i = 1; i <= SudokuBoard.MATRIX_SIZE; ++i) {
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
        for (int k = 0; k < SudokuBoard.MATRIX_SIZE; ++k) {
            if (value == matrix[row][k]) {
                return false;
            }
        }
        for (int k = 0; k < SudokuBoard.MATRIX_SIZE; ++k) {
            if (value == matrix[k][column]) {
                return false;
            }
        }
        for (int k = 0; k < SudokuBoard.MATRIX_SEGMENT_SIZE; k++) {
            for (int m = 0; m < SudokuBoard.MATRIX_SEGMENT_SIZE; m++) {
                if (value == matrix[((row / SudokuBoard.MATRIX_SEGMENT_SIZE) * SudokuBoard.MATRIX_SEGMENT_SIZE) + k]
                                [((column / SudokuBoard.MATRIX_SEGMENT_SIZE) * SudokuBoard.MATRIX_SEGMENT_SIZE) + m]) {
                    return false;
                }
            }
        }
        return true;
    }
}
