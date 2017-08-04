/**
 * Created by fabio.fischer on 03/08/2017.
 */

public class App {
    public static void writeMatrix(int[][] matrix) {
        System.out.println("=========================");

        for (int i = 0; i < SudokuBoard.MATRIX_SIZE; ++i) {
            for (int j = 0; j < SudokuBoard.MATRIX_SIZE; ++j) {
                if (j % SudokuBoard.MATRIX_SEGMENT_SIZE == 0) System.out.print("| ");
                System.out.print(matrix[i][j] == 0 ? " " : Integer.toString(matrix[i][j]));
                System.out.print(' ');
            }
            System.out.println("|");
        }

        System.out.println("=========================");
    }

    public static void main(String[] args) {
        SudokuBoard game;

        try {
            for (int i = 0; i <= 10; i++) {
                game = new SudokuBoard(Sudoku.newGame());

                System.out.println("\nProblem: ");
                writeMatrix(game.getMatrix());
                System.out.println("Result: ");
                writeMatrix(Sudoku.solve(game));
            }
        } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
        }
    }
}