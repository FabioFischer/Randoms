/**
 * Created by fabio.fischer on 03/08/2017.
 */
public class SudokuBoard {
    public static final int EMPTY_CELL_MARKER = 0;

    private int[][] matrix;
    private int size;

    public SudokuBoard(){}

    public SudokuBoard(int[][] input){
        this.setSize(input.length);
        this.setMatrix(input);
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
