class GameOfLife {

    boolean[][] board = new boolean[25][25];

    public GameOfLife() {
    }

    public GameOfLife(String[] board) {
        set((i, j, s) -> board[i].charAt(j * 2) == '■');
    }

    public void print() {
        get((i, j, s) -> printCell(j, s));
    }

    public GameOfLife makeNextGeneration() {
        GameOfLife gol = new GameOfLife();

        gol.set((i, j, s) -> findIfCellIsAlive(i, j));

        return gol;
    }

    private void set(Setter setter) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = setter.set(i, j, board[i][j]);
            }
        }
    }

    private void get(Getter getter) {
        set((i, j, s) -> {
            getter.get(i, j, s);
            return s;
        });
    }

    private boolean findIfCellIsAlive(int i, int j) {
        int c = countNeighbors(i, j);
        if (board[i][j]) {
            return c == 2 || c == 3;
        } else {
            return c == 3;
        }
    }

    private int countNeighbors(int i, int j) {
        var counter = new Getter() {
            int count;

            @Override
            public void get(int li, int lj, boolean state) {
                if (distance(i, j, li, lj) == 1 && board[li][lj])
                    count++;
            }
        };

        get(counter);
        return counter.count;
    }

    private int distance(int i, int j, int li, int lj) {
        return Math.max(
                Math.abs(i - li),
                Math.abs(j - lj));
    }

    private void printCell(int j, boolean s) {
        if (j == 0) System.out.println();

        String cell = s ? "■ " : "□ ";
        System.out.print(cell);
    }
}