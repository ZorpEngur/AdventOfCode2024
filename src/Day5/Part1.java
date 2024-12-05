package Day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Part1 {
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
        manualsLoop:for (List<Integer> manual : manuals) {
            for (int i = 0; i < manual.size() - 1; i++) {
                if (!rules.get(manual.get(i)).containsAll(manual.subList(i + 1, manual.size()))) {
                    continue manualsLoop;
                }
            }
            result += manual.get(manual.size() / 2);
        }
        System.out.println(result);
    }
}