public class Configurations {

    private int SIZE = 9973;
    private char HUMAN = 'X';
    private char COMPUTER = 'O';

    private char[][] board;
    private int board_size;
    private int lengthToWin;
    private int max_levels;

    public Configurations(int board_size, int lengthToWin, int max_levels) {
        this.board_size = board_size;
        this.lengthToWin = lengthToWin;
        this.max_levels = max_levels;
        board = new char[board_size][board_size];

        // Initially set all to spaces
        for (int row = 0; row < board_size; row++) {
            for (int col = 0; col < board_size; col++) {
                board[row][col] = ' ';
            }
        }
    }

    public HashDictionary createDictionary() {
        return new HashDictionary(SIZE);
    }

    public int repeatedConfiguration(HashDictionary hashTable) {
        String config = boardToString();

        return hashTable.get(config);
    }

    public void addConfiguration(HashDictionary hashDictionary, int score) {
        String config = boardToString();
        Data pair = new Data(config, score);

        hashDictionary.put(pair);
    }

    public void savePlay(int row, int col, char symbol) {
        board[row][col] = symbol;
    }

    public boolean squareIsEmpty(int row, int col) {
        if (board[row][col] == ' ') {
            return true;
        }

        return false;
    }

    public boolean wins(char symbol) {

        // Check for row or horizontal win
        for (int row = 0; row < board_size; row++) {
            for (int col = 0; col < board_size - lengthToWin + 1; col++) {
                boolean win = true;

                for (int i = 0; i < lengthToWin; i++) {
                    if (board[row][col + i] != symbol) {
                        win = false;
                    }
                }

                if (win) {
                    return true;
                }
            }
        }

        // Check for column or vertical win
        for (int row = 0; row < board_size - lengthToWin + 1; row++) {
            for (int col = 0; col < board_size; col++) {
                boolean win = true;

                for (int i = 0; i < lengthToWin; i++) {
                    if (board[row + i][col] != symbol) {
                        win = false;
                    }
                }

                if (win) {
                    return true;
                }
            }
        }

        // Check for diagonal (backward slash orientation)
        for (int row = 0; row < board_size - lengthToWin + 1; row++) {
            for (int col = 0; col < board_size - lengthToWin + 1; col++) {
                boolean win = true;

                for (int i = 0; i < lengthToWin; i++) {
                    if (board[row + i][col + i] != symbol) {
                        win = false;
                    }
                }

                if (win) {
                    return true;
                }
            }
        }

        // Check for diagonal (forward slash orientation)
        for (int row = lengthToWin - 1; row < board_size; row++) {
            for (int col = 0; col < board_size - lengthToWin + 1; col++) {
                boolean win = true;

                for (int i = 0; i < lengthToWin; i++) {
                    if (board[row - i][col + i] != symbol) {
                        win = false;
                    }
                }

                if (win) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isDraw() {

        // Check if any row is empty
        for (int row = 0; row < board_size; row++) {
            for (int col = 0; col < board_size; col++) {
                if (squareIsEmpty(row, col)) {
                    return false;
                }
            }
        }

        if (wins(HUMAN) || wins(COMPUTER)) {
            return false;
        }

        return true;
    }

    public int evalBoard() {

        if (wins(COMPUTER)) {
            return 3;
        } else if (wins(HUMAN)) {
            return 0;
        } else if (isDraw()) {
            return 2;
        } else {
            return 1;
        }
    }

    // Method to convert 2D character array into string
    private String boardToString() {
        String config = "";

        for (int row = 0; row < board_size; row++) {
            for (int col = 0; col < board_size; col++) {
                config += board[row][col];
            }
        }

        return config;
    }

}
