public class Draw {
    private final Round[] rounds;
    private int iterator = 0;
    public Draw(int num) {
        if (num % 2 == 0) {
            rounds = new Round[(num - 1) * 2];
        } else {
            rounds = new Round[num * 2];
        }
    }
    public int size() {
        return rounds.length;
    }
    public void addRound(Round round) {
        rounds[iterator++] = round;
    }
    public Round getRound(int num) {
        return rounds[num-1];
    }
    public int[] findPair(int first, int second, int from) {
        for (int i = from - 1; i < rounds.length; i++) {
            int a = rounds[i].containsPair(first, second);
            if (a >= 0) {
                return new int[]{i+1, a};
            }
        }
        return new int[]{-1, -1};
    }
}
