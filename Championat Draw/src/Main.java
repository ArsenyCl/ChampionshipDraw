import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringJoiner;

public class Main {
    public static boolean hasSymbs(String line) {
        for (int i = 0; i < line.length(); i++) {
            if (!Character.isWhitespace(line.charAt(i))) return true;
        }
        return false;
    }
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("D:\\ИТМО\\jdk files\\input.txt"));
            Scanner in = new Scanner(System.in);
            System.out.println("1 - Жеребьевка\n2 - Таблица\nВведите число: ");
            int cin = in.nextInt();
            if (cin == 1) {
                ArrayList<String> teamsNames = new ArrayList<>();
                String line;
                while ((line = reader.readLine()) != null) {
                    teamsNames.add(Parser.parseTeam(line));
                }
                Controller draw = new Controller(teamsNames);
                draw.makeDraw();
                try {
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new PrintStream("D:\\ИТМО\\jdk files\\output.txt")));
                    writer.write(draw.toString());
                    writer.flush();
                } catch (IOException e) {
                    e.getLocalizedMessage();
                }
            } else if (cin == 2) {
                ArrayList<String> teamsNames = new ArrayList<>();
                String line;
                while ((line = reader.readLine()) != null && hasSymbs(line)) {
                    teamsNames.add(Parser.parseTeam(line));
                }
                StringBuilder sb = new StringBuilder();
                TableCreation tbCr = new TableCreation(teamsNames);
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append('\n');
                }
                tbCr.createTable(sb.toString());
                try {
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new PrintStream("D:\\ИТМО\\jdk files\\output.txt")));
                    writer.write(tbCr.toString());
                    writer.flush();
                } catch (IOException e) {
                    e.getLocalizedMessage();
                }
            }
        } catch (IOException e) {
            e.getLocalizedMessage();
        }
    }
}