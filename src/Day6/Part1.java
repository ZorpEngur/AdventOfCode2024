package Day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Part1 {
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.lines(Path.of("src/Day6/input.txt")).collect(Collectors.toCollection(ArrayList::new));
        for (String line : lines) {
            if (line.contains("^")) {
                up(lines, lines.indexOf(line));
                System.out.println(lines.stream().map(e -> e.chars().filter( x -> x == 'X').count()).reduce(Long::sum));
                return;
            }
        }
    }

    private static void up(List<String> lines, int posY) {
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
                right(lines, i + 1);
                return;
            }
            char[] chars = lines.get(i).toCharArray();
            chars[posX] = '^';
            lines.set(i, new String(chars));
        }
        char[] chars = lines.getFirst().toCharArray();
        chars[posX] = 'X';
        lines.set(0, new String(chars));
        lines.forEach(System.out::println);
    }

    private static void right(List<String> lines, int posY) {
        int posX = lines.get(posY).indexOf('>');
        char[] line = lines.get(posY).toCharArray();
        for (int i = posX + 1; i < lines.get(posY).length(); i++) {
            if (line[i] == '.' || line[i] == 'X') {
                line[i - 1] = 'X';
            } else {
                line[i - 1] = 'v';
                lines.set(posY, new String(line));
                down(lines, posY);
                return;
            }
            line[i] = '>';
        }
        line[line.length - 1] = 'X';
        lines.set(posY, new String(line));
        lines.forEach(System.out::println);
    }

    private static void down(List<String> lines, int posY) {
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
                left(lines, i - 1);
                return;
            }
            char[] chars = lines.get(i).toCharArray();
            chars[posX] = 'v';
            lines.set(i, new String(chars));
        }
        char[] chars = lines.getLast().toCharArray();
        chars[posX] = 'X';
        lines.set(lines.size() - 1, new String(chars));
        lines.forEach(System.out::println);
    }

    private static void left(List<String> lines, int posY) {
        int posX = lines.get(posY).indexOf('<');
        char[] line = lines.get(posY).toCharArray();
        for (int i = posX - 1; i >= 0; i--) {
            if (line[i] == '.' || line[i] == 'X') {
                line[i + 1] = 'X';
            } else {
                line[i + 1] = '^';
                lines.set(posY, new String(line));
                up(lines, posY);
                return;
            }
            line[i] = '>';
        }
        line[0] = 'X';
        lines.set(posY, new String(line));
        lines.forEach(System.out::println);
    }
}