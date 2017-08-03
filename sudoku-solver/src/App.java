/**
 * Created by fabio.fischer on 03/08/2017.
 */
public class App {
    public static void writeMatrix(int[][] matrix) {
        System.out.println("\n=================================");

        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix.length; ++j) {
                if (j % (Math.sqrt(matrix.length)) == 0) System.out.print("| ");
                System.out.print(matrix[i][j] == 0 ? " " : Integer.toString(matrix[i][j]));
                System.out.print(' ');
            }
            System.out.println("|");
        }

        System.out.println("=================================");
    }

    public static void main(String[] args) {
        SudokuBoard nGame = new SudokuBoard(new int[][]{
                {0,8,0,4,0,2,0,6,0},
                {0,3,4,0,0,0,9,1,0},
                {9,6,0,0,0,0,0,8,4},
                {0,0,0,2,1,6,0,0,0},
                {0,0,0,0,0,0,0,0,0},
                {0,0,0,3,5,7,0,0,0},
                {8,4,0,0,0,0,0,7,5},
                {0,2,6,0,0,0,1,3,0},
                {0,9,0,7,0,1,0,4,0}
            });

        writeMatrix(nGame.getMatrix());
        try {
            writeMatrix(SudokuSolver.solve(nGame));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}