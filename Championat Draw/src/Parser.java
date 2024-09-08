public class Parser {
    private final String input;
    private  int iterator = 0;
    public Parser(String in) {
        input = in;
    }
    public boolean skipWhiteSpace() {
        boolean ret = false;
        while (iterator < input.length() && Character.isWhitespace(input.charAt(iterator))) {
            iterator++;
            ret = true;
        }
        return ret;
    }
    public static String parseTeam(String in) {
        Parser pars = new Parser(in);
        StringBuilder sb = new StringBuilder();
        pars.skipWhiteSpace();
        while (pars.iterator < in.length()) {
            sb.append(pars.input.charAt(pars.iterator));
            boolean ret = pars.skipWhiteSpace();
            pars.iterator = ret ? pars.iterator : pars.iterator + 1;
        }
        return Character.isWhitespace(sb.charAt(sb.length()-1)) ? sb.substring(0, sb.length()-1) : sb.toString();
    }
    public static Pair[] tokenizeMatch(String in) {
        int iter = 0;
        while (iter < in.length() && in.charAt(iter) != '\n') {
            iter++;
        }
        String firstTeam = Parser.parseTeam(in.substring(0, iter));
        String secondTeam = Parser.parseTeam(in.substring(iter));
        int[] scoreFirst = getScore(firstTeam);
        int[] scoreSecond = getScore(secondTeam);
        Pair firstPair = Character.isDigit(firstTeam.charAt(firstTeam.length()-1)) ? new Pair(scoreFirst[0] ,parseTeam(firstTeam.substring(0, scoreFirst[1]))) :
                new Pair(-1 ,parseTeam(firstTeam.substring(0, scoreFirst[1]+1)));
        Pair secondPair = Character.isDigit(secondTeam.charAt(secondTeam.length()-1)) ? new Pair(scoreSecond[0] ,parseTeam(secondTeam.substring(0, scoreSecond[1]))) :
                new Pair(-1 ,parseTeam(secondTeam.substring(0, scoreSecond[1]+1)));
        return new Pair[] {firstPair, secondPair};
    }
    private static int[] getScore(String in) {
        int iter = in.length()-1;
        int score = 0;
        int counter = 1;
        while (iter >= 0 && Character.isDigit(in.charAt(iter))) {
            score += counter * Integer.parseInt(String.valueOf(in.charAt(iter)));
            counter *= 10;
            iter--;
        }
        return new int[] {score, iter};
    }
    public String nextMatch() {
        String rdL = readLine();
        String rdL1;
        if (rdL.startsWith("MatchDay")) {
            rdL1 = readLine();
        } else {
            rdL1 = rdL;
        }
        String rdL2 = readLine();
        return rdL1 + '\n' + rdL2;
    }
    private String readLine() {
           skipWhiteSpace();
           StringBuilder sb = new StringBuilder();
           while (iterator < input.length() && input.charAt(iterator) != '\n') {
               sb.append(input.charAt(iterator));
               iterator++;
           }
           return sb.toString();
    }
}
