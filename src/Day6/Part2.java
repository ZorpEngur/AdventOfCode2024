package Day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Part2 {
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.lines(Path.of("src/Day6/input.txt")).collect(Collectors.toCollection(ArrayList::new));
        for (String line : lines) {
            if (line.contains("^")) {
                placeBarrier(lines, lines.indexOf(line));
                return;
            }
        }
    }
    
    private static void placeBarrier(List<String> lines, int posY) {
        int result = 0;
        for (int i = 0; i < lines.size(); i++) {
            for (int x = 0; x < lines.get(i).length(); x++) {
                if (lines.get(i).charAt(x) != '.') {
                    continue;
                }
                List<String> copy = new ArrayList<>(lines);
                char[] chars = copy.get(i).toCharArray();
                chars[x] = 'O';
                copy.set(i, new String(chars));
                try {
                    up(copy, posY);
                } catch (StackOverflowError e) {
                    result++;
                }
            }
        }
        System.out.println(result);
    }

    private static boolean up(List<String> lines, int posY) {
        int posX = lines.get(posY).indexOf('^');
        for (int i = posY - 1; i >= 0; i--) {
            if (lines.get(i).charAt(posX) == '.' || lines.get(i).charAt(posX) == 'X') {
                char[] chars = lines.get(i + 1).toCharArray();
                chars[posX] = 'X';
                lines.set(i + 1, new String(chars));
            } else {
                char[] chars = lines.get(i + 1).toCharArray();
                chars[posX] = '>';
                lines.set(i + 1, new String(chars));
                return right(lines, i + 1);
            }
            char[] chars = lines.get(i).toCharArray();
            chars[posX] = '^';
            lines.set(i, new String(chars));
        }
        char[] chars = lines.getFirst().toCharArray();
        chars[posX] = 'X';
        lines.set(0, new String(chars));
        return false;
    }

    private static boolean right(List<String> lines, int posY) {
        int posX = lines.get(posY).indexOf('>');
        char[] line = lines.get(posY).toCharArray();
        for (int i = posX + 1; i < lines.get(posY).length(); i++) {
            if (line[i] == '.' || line[i] == 'X') {
                line[i - 1] = 'X';
            } else {
                line[i - 1] = 'v';
                lines.set(posY, new String(line));
                return down(lines, posY);
            }
            line[i] = '>';
        }
        line[line.length - 1] = 'X';
        lines.set(posY, new String(line));
        return false;
    }

    private static boolean down(List<String> lines, int posY) {
        int posX = lines.get(posY).indexOf('v');
        for (int i = posY + 1; i < lines.size(); i++) {
            if (lines.get(i).charAt(posX) == '.' || lines.get(i).charAt(posX) == 'X') {
                char[] chars = lines.get(i - 1).toCharArray();
                chars[posX] = 'X';
                lines.set(i - 1, new String(chars));
            } else {
                char[] chars = lines.get(i - 1).toCharArray();
                chars[posX] = '<';
                lines.set(i - 1, new String(chars));
                return left(lines, i - 1);
            }
            char[] chars = lines.get(i).toCharArray();
            chars[posX] = 'v';
            lines.set(i, new String(chars));
        }
        char[] chars = lines.getLast().toCharArray();
        chars[posX] = 'X';
        lines.set(lines.size() - 1, new String(chars));
        return false;
    }

    private static boolean left(List<String> lines, int posY) {
        int posX = lines.get(posY).indexOf('<');
        char[] line = lines.get(posY).toCharArray();
        for (int i = posX - 1; i >= 0; i--) {
            if (line[i] == '.' || line[i] == 'X') {
                line[i + 1] = 'X';
            } else {
                line[i + 1] = '^';
                lines.set(posY, new String(line));
                return up(lines, posY);
            }
            line[i] = '>';
        }
        line[0] = 'X';
        lines.set(posY, new String(line));
        return false;
    }
}