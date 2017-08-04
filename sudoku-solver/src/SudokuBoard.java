/**
 * Created by fabio.fischer on 03/08/2017.
 */
public class SudokuBoard {
    public static final int EMPTY_CELL_MARKER = 0;
    public static final int MATRIX_SIZE = 9;
    public static final int MATRIX_SEGMENT_SIZE = 3;

    private int[][] matrix;

    public SudokuBoard(){}

    public SudokuBoard(int[][] input){
        this.setMatrix(input);
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }
}
