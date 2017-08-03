/**
 * Created by fabio.fischer on 03/08/2017.
 */
public class Sudoku implements ISudoku {
    private int[][] matrix;

    public Sudoku(){}

    public Sudoku(int[][] input){
        if (this.validateInput(input)) {
            this.setMatrix(input);
        }
    }

    private boolean validateInput(int[][] input) {
        return true;
    }

    @Override
    public int[][] newGame() {
        return null;
    }

    @Override
    public int[][] solve(int[][] input) {
        return new int[0][];
    }

    @Override
    public boolean verify(int[][] input) {
        return false;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }
}
