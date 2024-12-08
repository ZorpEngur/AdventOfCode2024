package Day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Part2 {

    private static List<String> test = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.lines(Path.of("src/Day8/input.txt")).toList();
        test = new ArrayList<>(lines);
        Map<Character, List<Map.Entry<Integer, Integer>>> antennas = new HashMap<>();
        for (int i = 0; i < lines.size(); i++) {
            for (int x = 0; x < lines.get(i).length(); x++) {
                if (lines.get(i).charAt(x) != '.') {
                    antennas.computeIfAbsent(lines.get(i).charAt(x), c -> new ArrayList<>()).add(Map.entry(i, x));
                }
            }
        }
        checkAntinodes(antennas, findAntinodes(antennas), lines.get(0).length(), lines.size());
    }

    private static List<Map.Entry<Integer, Integer>> findAntinodes(Map<Character, List<Map.Entry<Integer, Integer>>> antennas) {
        List<Map.Entry<Integer, Integer>> antinodes = new ArrayList<>();
        for (List<Map.Entry<Integer, Integer>> pos : antennas.values()) {
            for (int i = 0; i < pos.size(); i++) {
                for (int y = 0; y < pos.size(); y++) {
                    if (y == i) {
                        continue;
                    }
                    int yDiff = Math.abs(pos.get(i).getKey() - pos.get(y).getKey());
                    int xDiff = Math.abs(pos.get(i).getValue() - pos.get(y).getValue());
                    int yMod = pos.get(i).getKey() > pos.get(y).getKey() ? 1 : -1;
                    int xMod = pos.get(i).getValue() > pos.get(y).getValue() ? 1 : -1;
                    int n = 1;
                    while (n < 50) {
                        antinodes.add(Map.entry(pos.get(i).getKey() + yDiff * yMod * n, pos.get(i).getValue() + xDiff * xMod * n));
                        antinodes.add(Map.entry(pos.get(i).getKey() - yDiff * yMod * n, pos.get(i).getValue() - xDiff * xMod * n));
                        n++;
                    }
                }
            }
        }
        return antinodes;
    }

    private static void checkAntinodes(Map<Character, List<Map.Entry<Integer, Integer>>> antennas, List<Map.Entry<Integer, Integer>> antinodes, int lineLength, int rows) {
        for (Map.Entry<Integer, Integer> antinode : antinodes) {
            if (antinode.getKey() < 0 || antinode.getKey() >= rows || antinode.getValue() < 0 || antinode.getValue() >= lineLength) {
                continue;
            }
            char[] line = test.get(antinode.getKey()).toCharArray();
            line[antinode.getValue()] = '#';
            test.set(antinode.getKey(), new String(line));
        }
        test.forEach(System.out::println);
        System.out.println(test.stream().map(e -> e.chars().filter(x -> x == '#').count()).reduce(Long::sum));
    }
}