package Day19;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Part2 {

    private static Map<Integer, Long> cache = new HashMap<>();

    public static void main(String[] args) throws IOException {
        List<String> input = Files.lines(Path.of("src/Day19/input.txt")).toList();
        Map<Character, List<String>> towels = Arrays.stream(input.getFirst().split(", ")).collect(Collectors.groupingBy(e -> e.charAt(0)));
        long result = 0;
        for (String pattern : input.subList(2, input.size())) {
            result += isPossible(towels, pattern, 0);
            cache = new HashMap<>();
        }
        System.out.println(result);
    }

    private static long isPossible(Map<Character, List<String>> towels, String pattern, int pos) {
        if (cache.containsKey(pos)) {
            return cache.get(pos);
        }
        if (pos >= pattern.length()) {
            return 1;
        }
        if (!towels.containsKey(pattern.charAt(pos))) {
            return 0;
        }
        long result = 0;
        for (String towel : towels.get(pattern.charAt(pos))) {
            if (pattern.startsWith(towel, pos)) {
                result += isPossible(towels, pattern, pos + towel.length());
            }
        }
        cache.put(pos, result);
        return result;
    }
}