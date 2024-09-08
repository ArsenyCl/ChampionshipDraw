import java.io.IOException;

public class Team {
    private int games =  0;
    private int wins = 0;
    private int loses = 0;

    private int goalsScored = 0;
    private int goalsMissed = 0;
    private final String name;
    public Team(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public int getGames() {
        return games;
    }
    public int getLoses() {
        return loses;
    }
    public int getWins() {
        return wins;
    }
    public int getDraws() {
        return games - wins - loses;
    }
    public int getPoints() {
        return 3 * getWins() + getDraws();
    }

    public int getGoalsScored() {
        return goalsScored;
    }
    public int getGoalsMissed() {
        return goalsMissed;
    }
    public void match(int res, int scored, int missed) {
        games++;
        goalsScored += scored;
        goalsMissed += missed;
        switch (res) {
            case (1) -> wins++;
            case (-1) -> loses++;
        }
    }

    public boolean nameFits(String name) {
        int iter1 = 0;
        int iter2 = 0;
        while (Character.isWhitespace(name.charAt(iter1))) {
            iter1++;
        }
        while (Character.isWhitespace(this.name.charAt(iter2))) {
            iter2++;
        }
        for (int i = 0; i + iter1 < name.length() && i + iter2 < this.name.length(); i++) {
            if (name.charAt(i + iter1) != this.name.charAt(i+iter2)) {
                return false;
            }
        }
        return true;
    }
}
