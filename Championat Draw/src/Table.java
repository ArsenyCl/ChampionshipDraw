import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Table {
    Team[] table;

    public Table(ArrayList<String> teams) {
        table = new Team[teams.size()];
        for (int i = 0; i < teams.size(); i++) {
            table[i] = new Team(teams.get(i));
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        tableSort();
        int i = 1;
        for (Team team : table) {
            sb.append(i++).append(") ").append(team.getName()).append(" ").append(team.getPoints()).append(" pts ").append(team.getGames()).append(": ").append(team.getWins()).append(" ").append(team.getDraws()).append(" ").append(team.getLoses()).append(" ").append(team.getGoalsScored()).append("-").append(team.getGoalsMissed()).append('\n');
        }
        return sb.toString();
    }

    private void tableSort() {
        Arrays.sort(table, new Comparator<Team>() {
            @Override
            public int compare(Team o1, Team o2) {
                if (o2.getPoints() == o1.getPoints()) {
                    if (o2.getGames() == o1.getGames()) {
                        if ((o2.getGoalsScored() - o2.getGoalsMissed()) == (o1.getGoalsScored() - o1.getGoalsMissed())) {
                            if (o2.getWins() == o1.getWins()) {
                                return o2.getGoalsScored() - o1.getGoalsScored();
                            }
                            return o2.getWins() - o1.getWins();
                        }
                        return (o2.getGoalsScored() - o2.getGoalsMissed()) - (o1.getGoalsScored() - o1.getGoalsMissed());
                    }
                    return o2.getGames() - o1.getGames();
                }
                return o2.getPoints() - o1.getPoints();
            }
        });
    }
    public void result(String teamName, int gs, int gm) {
        int res = 0;
        if (gs > gm) {
            res = 1;
        } else if (gs < gm) {
            res = -1;
        }
        for (Team each: table) {
            if (each.nameFits(teamName)) {
                each.match(res, gs, gm);
                break;
            }
        }
    }
}
