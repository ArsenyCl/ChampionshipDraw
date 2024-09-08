import java.io.FileNotFoundException;
import java.util.*;

public class Controller {
    private final HashMap<Integer, Team> numToTeams = new HashMap<>();
    private final HashMap<Integer, Integer> numToNum = new HashMap<>();
    private final Draw draw;
    private final int numOfTeams;
    int[] firstInd;
    int[] secondInd;
    public Controller(ArrayList<String> teamsNames) throws FileNotFoundException {
        numOfTeams = teamsNames.size();
        firstInd = new int[numOfTeams];
        secondInd = new int[numOfTeams];
        for (int i = 0; i < numOfTeams; i++) {
            firstInd[i] = i+1;
            secondInd[i] = i+numOfTeams+1;
        }
        randomize(firstInd);
        randomize(secondInd);
        draw = new Draw(numOfTeams);
        for (int i = 0; i < numOfTeams; i++) {
            numToTeams.put(firstInd[i], new Team(teamsNames.get(i)));
            numToTeams.put(secondInd[i], new Team(teamsNames.get(i)));
            numToNum.put(firstInd[i], secondInd[i]);
        }
    }
    public void makeDraw() {
        int[][] split1 = split(1, numOfTeams);
        int[][] split2 = split(numOfTeams + 1, numOfTeams * 2);
        randomize(split1);
        for (int i = 1; i < split1.length; i+=2) {
            for (int j = 0; j < split1[i].length; j+=2) {
                if (j + 1 < split1.length) swap(split1[i], j, j+1);
            }
        }
        for (int i = 0; i < draw.size() / 2; i++) {
            draw.addRound(new Round(split1[i]));
        }
        for (int i = draw.size() / 2; i < draw.size(); i++) {
            draw.addRound(new Round(split2[i-draw.size() / 2]));
        }
        homeAwayBalance();
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < draw.size() / 2; i++) {
            Round round = draw.getRound(i+1);
            sb.append("MatchDay ").append(i+1).append('\n');
            sb.append('\n');
            for (int j = 0; j < round.size(); j++) {
                int[] pair = round.getPair(j);
                sb.append(numToTeams.get(pair[0]).getName()).append('\n');
                sb.append(numToTeams.get(pair[1]).getName()).append('\n');
                sb.append('\n');
            }
        }
        for (int i = draw.size() / 2; i < draw.size(); i++) {
            Round round = draw.getRound(i+1);
            sb.append("MatchDay ").append(i+1).append('\n');
            sb.append('\n');
            for (int j = 0; j < round.size(); j++) {
                int[] pair = round.getPair(j);
                sb.append(numToTeams.get(pair[0]).getName()).append('\n');
                sb.append(numToTeams.get(pair[1]).getName()).append('\n');
                sb.append('\n');
            }
        }
        return sb.toString();
    }
    private  int[][] split(int first, int last) {
        if (last - first == 1) {
            return new int[][]{new int[]{first, last}};
        } else if ((last - first + 1) % 2 == 0) {
            int[][] ans = new int[last - first][last - first + 1];
            int mid = (last - first + 1) / 2;
            int[][] fst = split(first, (first + last - 1) / 2);
            int[][] snd = split((first + last - 1) / 2 + 1, last);
            if (mid % 2 == 0) {
                int[] arr = new int[last - first + 1];
                for (int i = 0; i < arr.length; i+=2) {
                    arr[i] = first + (i / 2);
                }
                int[] specArr = new int[mid];
                for (int i = 0; i < mid; i++) {
                    specArr[i] = first + i + mid;
                }
                for(int i = 0; i < mid; i++) {
                    ans[i] = arr.clone();
                    for (int j = 1; j < ans[i].length; j += 2) {
                        ans[i][j] = specArr[((j / 2) + i) % mid];
                    }
                }
                for (int i = mid; i < ans.length; i++) {
                    for (int j = 0; j < fst[i - mid].length; j++) {
                        ans[i][j] = fst[i-mid][j];
                    }
                    for (int j = snd[i-mid].length; j < 2 * snd[i-mid].length; j++) {
                        ans[i][j] = snd[i-mid][j % snd[i-mid].length];
                    }
                }
            } else {
               for (int i = 0; i < mid; i++) {
                   for (int j = 0; j < mid - 1; j++) {
                       ans[i][j] = fst[i][j];
                   }
                   for (int j = mid - 1; j < 2 * mid - 2 ; j++) {
                       ans[i][j] = snd[i][j - mid + 1];
                   }
                   ans[i][2 * mid - 2] = fst[i][mid-1];
                   ans[i][2 * mid - 1] = snd[i][mid-1];
               }
               for (int i = mid; i < ans.length; i++) {
                   for (int j = 0; j < fst.length; j++) {
                       ans[i][j*2] = fst[j][mid-1];
                       ans[i][j*2+1] = snd[(j+i+1) % snd.length][mid-1];
                   }
               }
            }
            return ans;
        } else {
            int[][] fst = split(first, last + 1);
            int[][] ans = new int[fst.length][last-first+1];
            for (int i = 0; i < fst.length; i++) {
                for (int j = 0; j < fst[i].length; j++) {
                    if(fst[i][j] == last + 1) {
                        ans[i][last-first] = fst[i][j-1];
                        int t = last - first-1;
                        for (int k = fst.length; k >=0; k--) {
                            if (fst[i][k] == last + 1) {
                                k -= 2;
                            }
                            if (k >= 0) {
                                ans[i][t] = fst[i][k];
                                t--;
                            }
                        }
                    }
                }
            }
            Arrays.sort(ans, new Comparator<int[]>() {
                @Override
                public int compare(int[] o1, int[] o2) {
                    return o2[o2.length-1] - o1[o1.length-1];
                }
            });
            return ans;
        }
    }
    private void homeAwayBalance() {
        for (int i = 1; i <= draw.size() / 2; i++) {
            for (int j = 0; j < draw.getRound(i).size(); j++) {
                int first1 = draw.getRound(i).getPair(j)[0];
                int first2 = draw.getRound(i).getPair(j)[1];
                int[] match = draw.findPair(numToNum.get(first1), numToNum.get(first2), draw.size() / 2 + 1);
                if (numToNum.get(first1) == draw.getRound(match[0]).getPair(match[1])[0]) draw.getRound(match[0]).swap(match[1]);
            }
        }
    }
    public void swap(int[][] arr, int ind1, int ind2) {
        int[] c = arr[ind1];
        arr[ind1] = arr[ind2];
        arr[ind2] = c;
    }
    public void randomize(int[][] arr) {
        Random random = new Random();
        for (int i = 0; i < 500; i++) {
            int ind1 = random.nextInt(0, arr.length);
            int ind2 = random.nextInt(0, arr.length);
            swap(arr, ind1, ind2);
        }
    }
    public void swap(int[] arr, int ind1, int ind2) {
        int c = arr[ind1];
        arr[ind1] = arr[ind2];
        arr[ind2] = c;
    }
    public void randomize(int[] arr) {
        Random random = new Random();
        for (int i = 0; i < 500; i++) {
            int ind1 = random.nextInt(0, arr.length);
            int ind2 = random.nextInt(0, arr.length);
            swap(arr, ind1, ind2);
        }
    }
}
