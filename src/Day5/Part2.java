package Day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Part2 {
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.lines(Path.of("src/Day5/input.txt")).toList();
        int result = 0;
        Map<Integer, List<Integer>> rules = new HashMap<>();
        lines.subList(0, lines.indexOf("")).stream()
                .map(e -> Arrays.stream(e.split("\\|")).map(Integer::parseInt).toList())
                .forEach(e -> rules.computeIfAbsent(e.getFirst(), v -> new ArrayList<>()).add(e.get(1)));
        List<List<Integer>> manuals = lines.subList(lines.indexOf("") + 1, lines.size()).stream()
                .map(e -> Arrays.stream(e.split(","))
                        .map(Integer::parseInt).toList()).toList();
        for (List<Integer> manual : manuals) {
            List<Integer> ordered = manual.stream()
                    .sorted(Comparator.comparing(e -> rules.containsKey(e) ? rules.get(e).stream().filter(manual::contains).count() : 0))
                    .toList().reversed();
            if (manual.equals(ordered)) {
                continue;
            }
            result += ordered.get(ordered.size() / 2);
        }
        System.out.println(result);
    }
}