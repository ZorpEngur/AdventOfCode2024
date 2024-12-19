package Day19;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Part1 {

    public static void main(String[] args) throws IOException {
        List<String> input = Files.lines(Path.of("src/Day19/input.txt")).toList();
        Map<Character, List<String>> towels = Arrays.stream(input.getFirst().split(", ")).collect(Collectors.groupingBy(e -> e.charAt(0)));
        int result = 0;
        for (String pattern : input.subList(2, input.size())) {
            if (isPossible(towels, pattern, 0)) {
                result++;
            }
        }
        System.out.println(result);
    }

    private static boolean isPossible(Map<Character, List<String>> towels, String pattern, int pos) {
        if (pos >= pattern.length()) {
            return true;
        }
        if (!towels.containsKey(pattern.charAt(pos))) {
            return false;
        }
        for (String towel : towels.get(pattern.charAt(pos))) {
            if (pattern.startsWith(towel, pos) && isPossible(towels, pattern, pos + towel.length())) {
                return true;
            }
        }
        return false;
    }
}