import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class SudokuGenaretor {
    private int sudukoSize;
    private int blockLength;
    private int board[][];

    /**
     * default constructor
     * create a Sudoku Board with default 4 size
     */
    public SudokuGenaretor() {
        this(4);
    }

    /**
     * creates the board of specified size using backtracking
     *
     * @param size should be a perfect square or an IllegalArgumentException is thrown
     * @throws IllegalArgumentException if @size is not a perfect square
     */
    public SudokuGenaretor(int size) throws IllegalArgumentException {
        if (!isPerfectSquare(size))
            throw new IllegalArgumentException("Sudoku size should be a perfect square");
        this.sudukoSize = size;
        board = new int[sudukoSize][sudukoSize];
        blockLength = (int) Math.sqrt(sudukoSize);
    }

    /**
     * check if the given value is a perfect square
     *
     * @param value any integer value.
     * @return true  : if is a perfect square
     * false : if not a perfect square
     */
    private boolean isPerfectSquare(int value) {
        double sqrt = Math.sqrt(value);
        int x = (int) sqrt;
        if (Math.pow(sqrt, 2) == Math.pow(x, 2))
            return true;
        else
            return false;
    }

    /**
     * creates a board with default number of holes:10
     */
    void createBoard() {
        createBoard(10);
    }

    /**
     * creates a board with specified number of holes
     *
     * @param noOfHoles : integer value less that square of sudoku size or IllegalArgumentException is thrown
     * @throws IllegalArgumentException if @noOfHoles greater than square of the size     *
     */
    void createBoard(int noOfHoles) throws IllegalArgumentException {
        if (fillBoard(0, 0))
            System.out.println("board created :)");
        else
            System.out.println("board creation failed :(");
        makeSpaces(noOfHoles);
    }

    /**
     * recursive function used to place valid values in the board
     *
     * @param xVal x value of the current position
     * @param yVal y value of the current position
     * @return true: if board is filled.
     * false: if board cannot be filled.
     */
    private boolean fillBoard(int xVal, int yVal) {
        int nextX = xVal, nextY = yVal;
        int possibleValues[] = getRandomPossibleValues();
        for (int i = 0; i < sudukoSize; i++) {
            if (isValidMove(xVal, yVal, possibleValues[i])) {
                board[xVal][yVal] = possibleValues[i];
                if (xVal == (sudukoSize - 1)) {
                    if (yVal == (sudukoSize - 1))
                        return true;
                    else {
                        nextX = 0;
                        nextY = yVal + 1;
                    }
                } else {
                    nextX = xVal + 1;
                }
                if (fillBoard(nextX, nextY))
                    return true;
            }


        }
        board[xVal][yVal] = 0;
        return false;
    }

    /**
     * takes in possible integer value and determines its validity based on the provided
     * x-value and y-value
     *
     * @param x     x value of the board square
     * @param y     y value of the board square
     * @param value possible value which is to be verified
     * @return true: if valid
     * false: if not valid
     */
    private boolean isValidMove(int x, int y, int value) {
        for (int i = 0; i < sudukoSize; i++) {
            if (value == board[x][i])
                return false;
        }
        for (int i = 0; i < sudukoSize; i++) {
            if (value == board[i][y])
                return false;
        }
        int blockStartX = getBlock(x);
        int blockStartY = getBlock(y);
        for (int i = blockStartX; i < sudukoSize && i < blockStartX + blockLength; i++)
            for (int j = blockStartY; j < sudukoSize && j < blockStartY + blockLength; j++)
                if (value == board[i][j])
                    return false;
        return true;
    }

    /**
     * prints the board to the console
     */
    void printBoard() {
        for (int i = 0; i < sudukoSize; i++) {
            for (int j = 0; j < sudukoSize; j++)
                System.out.print(board[i][j] + "  ");
            System.out.println();
        }
        System.out.println();
    }

    /**
     * used to identify startIndex of the block which the provide value lie in
     *
     * @param value (x or y)index of the board square
     * @return start index of the block where value lie in
     */
    private int getBlock(int value) {
        for (int i = 0; i < sudukoSize; i += blockLength) {
            if (value >= i && value < (i + blockLength)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * used to get random possible values
     *
     * @return int array of random possible values
     */
    private int[] getRandomPossibleValues() {
        List<Integer> l = new ArrayList<>();
        int[] randomRow = new int[sudukoSize];
        for (int i = 1; i <= sudukoSize; i++) {
            l.add(i);
        }
        Collections.shuffle(l);
        for (int i = 0; i < sudukoSize; i++) {
            randomRow[i] = l.get(i);
        }
        return randomRow;
    }

    /**
     * used to erase the value from specified random indices form sudoku board
     *
     * @param noOfHoles specifies number of spaces that are to be made in suduko board
     * @throws IllegalArgumentException if @noOfHoles greater than square of the size
     */
    private void makeSpaces(int noOfHoles) throws IllegalArgumentException {
        double totalSquares = sudukoSize * sudukoSize;
        double holes = (double) noOfHoles;
        if (holes > totalSquares)
            throw new IllegalArgumentException("value for number of spaces should be less than " + totalSquares);
        for (int i = 0; i < sudukoSize; i++)
            for (int j = 0; j < sudukoSize; j++) {
                double holeChance = holes / totalSquares;
                if (Math.random() <= holeChance) {
                    board[i][j] = 0;
                    holes--;
                }
                totalSquares--;
            }
    }

}
