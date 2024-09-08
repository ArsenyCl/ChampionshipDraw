public class Round {
    private final int[][] games;
    private int iterator = 0;
    public Round(int[] arr) {
        games = new int[arr.length / 2][2];
        for (int i = 0; i < games.length; i++) {
            games[i][0] = arr[i * 2];
            games[i][1] = arr[i * 2 + 1];
            iterator++;
        }
    }

    public int[] getPair(int i) {
        return games[i];
    }
    public int size() {
        return games.length;
    }
    public int containsPair(int first, int second) {
        for (int i = 0; i < iterator; i++) {
            if ((games[i][0] == first && games[i][1] == second) ||(games[i][0] == second && games[i][1] == first)) {
                return i;
            }
        }
        return -1;
    }
    public void swap(int i) {
        int c = games[i][0];
        games[i][0] = games[i][1];
        games[i][1] = c;
    }
}
