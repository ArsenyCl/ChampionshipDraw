import java.util.ArrayList;

public class TableCreation {
    Table table;
    public TableCreation(ArrayList<String> teamNames) {
        table = new Table(teamNames);
    }
    private void match(String match)  {
        Pair[] pairs = Parser.tokenizeMatch(match);
        if (pairs[0].getDig() >= 0 && pairs[1].getDig() >= 0) {
            table.result(pairs[0].getStr(), pairs[0].getDig(), pairs[1].getDig());
            table.result(pairs[1].getStr(), pairs[1].getDig(), pairs[0].getDig());
        }
    }
    public void createTable(String in) {
        Parser pars = new Parser(in);
        String match;
        while(!(match = pars.nextMatch()).equals("\n")) {
            match(match);
            pars.skipWhiteSpace();
        }
    }
    public String toString() {
        return table.toString();
    }
}
