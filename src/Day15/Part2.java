package Day15;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Part2 {
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.lines(Path.of("src/Day15/input.txt")).toList();
        String movement = lines.subList(lines.indexOf("") + 1 , lines.size()).stream().reduce(String::concat).get();
        List<String> newMap = new ArrayList<>();
        for (String line : lines.subList(0, lines.indexOf(""))) {
            StringBuilder sb = new StringBuilder();
            for (char c : line.toCharArray()) {
                if (c == 'O') {
                    sb.append("[]");
                } else if (c == '@') {
                    sb.append("@.");
                } else {
                    sb.append(c).append(c);
                }
            }
            newMap.add(sb.toString());
        }
        newMap.forEach(System.out::println);
        for (String line : newMap) {
            if (line.contains("@")) {
                int posY = newMap.indexOf(line);
                for (char c : movement.toCharArray()) {
                    switch (c) {
                        case '>':
                            right(newMap, posY);
                            break;
                        case '^':
                            posY = up(newMap, posY - 1, List.of(newMap.get(posY).indexOf('@'))) ? posY - 1 : posY;
                            break;
                        case 'v':
                            posY = down(newMap, posY + 1, List.of(newMap.get(posY).indexOf('@'))) ? posY + 1 : posY;
                            break;
                        case '<':
                            left(newMap, posY);
                            break;
                    }
                }
                break;
            }
        }
        newMap.forEach(System.out::println);
        int result = 0;
        for (int y = 1; y < newMap.size() - 1; y++) {
            for (int x = 1; x < newMap.getFirst().length() - 1; x++) {
                if (newMap.get(y).charAt(x) == '[') {
                    result += 100 * y + x;
                }
            }
        }
        System.out.println(result);
    }

    private static boolean up(List<String> lines, int posY, Collection<Integer> moveX) {
        Set<Integer> toMove = new HashSet<>();
        for (int i : moveX) {
            if (lines.get(posY).charAt(i) == '#') {
                return false;
            } else if (lines.get(posY).charAt(i) == '[') {
                toMove.add(i);
                toMove.add(i+1);
            } else if (lines.get(posY).charAt(i) == ']') {
                toMove.add(i);
                toMove.add(i-1);
            }
        }
        if (!toMove.isEmpty()) {
            if (!up(lines, posY - 1, toMove)) {
                return false;
            }
        }
        int count = 0;
        for (int i : moveX.stream().sorted().toList()) {
            char[] chars = lines.get(posY).toCharArray();
            chars[i] = moveX.size() == 1 ? '@' : count % 2 == 0 ? '[' : ']';
            lines.set(posY, new String(chars));
            chars = lines.get(posY + 1).toCharArray();
            chars[i] = '.';
            lines.set(posY + 1, new String(chars));
            count++;
        }
        return true;
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

    private static boolean down(List<String> lines, int posY, Collection<Integer> moveX) {
        Set<Integer> toMove = new HashSet<>();
        for (int i : moveX) {
            if (lines.get(posY).charAt(i) == '#') {
                return false;
            } else if (lines.get(posY).charAt(i) == '[') {
                toMove.add(i);
                toMove.add(i+1);
            } else if (lines.get(posY).charAt(i) == ']') {
                toMove.add(i);
                toMove.add(i-1);
            }
        }
        if (!toMove.isEmpty()) {
            if (!down(lines, posY + 1, toMove)) {
                return false;
            }
        }
        int count = 0;
        for (int i : moveX.stream().sorted().toList()) {
            char[] chars = lines.get(posY).toCharArray();
            chars[i] = moveX.size() == 1 ? '@' : count % 2 == 0 ? '[' : ']';
            lines.set(posY, new String(chars));
            chars = lines.get(posY - 1).toCharArray();
            chars[i] = '.';
            lines.set(posY - 1, new String(chars));
            count++;
        }
        return true;
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