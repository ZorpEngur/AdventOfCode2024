package Day23;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Part1 {

    private static final Set<Set<String>> result = new HashSet<>();

    public static void main(String[] args) throws IOException {
        Map<String, List<String>> mapping = new HashMap<>();
        Files.lines(Path.of("src/Day23/input.txt")).forEach(e -> {
            mapping.computeIfAbsent(e.substring(0, 2), k -> new ArrayList<>()).add(e.substring(3));
            mapping.computeIfAbsent(e.substring(3), k -> new ArrayList<>()).add(e.substring(0, 2));
        });
        for (String key : mapping.keySet().stream().filter(e -> e.charAt(0) == 't').toList()) {
            Set<String> set = new HashSet<>();
            set.add(key);
            findCycles(mapping, set, key, key);
        }
        System.out.println(result.size());
    }

    private static void findCycles(Map<String, List<String>> mapping, Set<String> cycle, String start, String last) {
        if (cycle.size() == 3) {
            if (mapping.get(last).contains(start)) {
                result.add(cycle);
            }
            return;
        }
        for (String key : mapping.get(last)) {
            Set<String> set = new HashSet<>(cycle);
            set.add(key);
            if (set.size() > cycle.size()) {
                findCycles(mapping, set, start, key);
            }
        }
    }

}