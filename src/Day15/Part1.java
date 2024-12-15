package Day15;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Part1 {
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.lines(Path.of("src/Day15/input.txt")).toList();
        List<String> map = new ArrayList<>(lines.subList(0, lines.indexOf("")));
        String movement = lines.subList(lines.indexOf("") + 1 , lines.size()).stream().reduce(String::concat).get();
        map.forEach(System.out::println);
        for (String line : map) {
            if (line.contains("@")) {
                int posY = map.indexOf(line);
                for (char c : movement.toCharArray()) {
                    switch (c) {
                        case '>':
                            right(map, posY);
                            break;
                        case '^':
                            posY = up(map, posY);
                            break;
                        case 'v':
                            posY = down(map, posY);
                            break;
                        case '<':
                            left(map, posY);
                            break;
                    }
                }
                break;
            }
        }
        map.forEach(System.out::println);
        int result = 0;
        for (int y = 1; y < map.size() - 1; y++) {
            for (int x = 1; x < map.getFirst().length() - 1; x++) {
                if (map.get(y).charAt(x) == 'O') {
                    result += 100 * y + x;
                }
            }
        }
        System.out.println(result);
    }

    private static int up(List<String> lines, int posY) {
        int posX = lines.get(posY).indexOf('@');
        for (int i = posY - 1; i > 0; i--) {
            if (lines.get(i).charAt(posX) == '.') {
                for (int z = i; z < posY; z++) {
                    char[] chars = lines.get(z).toCharArray();
                    chars[posX] = lines.get(z + 1).charAt(posX);
                    lines.set(z, new String(chars));
                }
                char[] chars = lines.get(posY).toCharArray();
                chars[posX] = '.';
                lines.set(posY, new String(chars));
                return posY - 1;
            } else if (lines.get(i).charAt(posX) == '#') {
                break;
            }
        }
        return posY;
    }

    private static void right(List<String> lines, int posY) {
        String line = lines.get(posY);
        int posX = line.indexOf('@');
        for (int i = posX + 1; i < line.length() - 1; i++) {
            if (line.charAt(i) == '.') {
                for (int z = i; z > posX; z--) {
                    char[] chars = lines.get(posY).toCharArray();
                    chars[z] = lines.get(posY).charAt(z - 1);
                    lines.set(posY, new String(chars));
                }
                char[] chars = lines.get(posY).toCharArray();
                chars[posX] = '.';
                lines.set(posY, new String(chars));
                break;
            } else if (lines.get(posY).charAt(i) == '#') {
                break;
            }
        }
    }

    private static int down(List<String> lines, int posY) {
        int posX = lines.get(posY).indexOf('@');
        for (int i = posY + 1; i < lines.size() - 1; i++) {
            if (lines.get(i).charAt(posX) == '.') {
                for (int z = i; z > posY; z--) {
                    char[] chars = lines.get(z).toCharArray();
                    chars[posX] = lines.get(z - 1).charAt(posX);
                    lines.set(z, new String(chars));
                }
                char[] chars = lines.get(posY).toCharArray();
                chars[posX] = '.';
                lines.set(posY, new String(chars));
                return posY + 1;
            } else if (lines.get(i).charAt(posX) == '#') {
                break;
            }
        }
        return posY;
    }

    private static void left(List<String> lines, int posY) {
        String line = lines.get(posY);
        int posX = line.indexOf('@');
        for (int i = posX - 1; i > 0; i--) {
            if (line.charAt(i) == '.') {
                for (int z = i; z < posX; z++) {
                    char[] chars = lines.get(posY).toCharArray();
                    chars[z] = lines.get(posY).charAt(z + 1);
                    lines.set(posY, new String(chars));
                }
                char[] chars = lines.get(posY).toCharArray();
                chars[posX] = '.';
                lines.set(posY, new String(chars));
                break;
            } else if (lines.get(posY).charAt(i) == '#') {
                break;
            }
        }
    }
}