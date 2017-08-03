/**
 * Created by fabio.fischer on 03/08/2017.
 */
public interface ISudoku {
    int[][] newGame();
    int[][] solve(int [][] input);
    boolean verify(int[][] input);
}
