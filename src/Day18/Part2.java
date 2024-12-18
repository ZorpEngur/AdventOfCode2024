package Day18;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Part2 {

    private static Map<Map.Entry<Integer, Integer>, Integer> shortestPath = new HashMap<>();

    public static void main(String[] args) throws IOException {
        List<String> input = Files.lines(Path.of("src/Day18/input.txt")).toList();
        int width = 70;
        int height = 70;
        int corruptedMemoryCount = 1024;
        do {
            shortestPath = new HashMap<>();
            corruptedMemoryCount++;
            Set<Map.Entry<Integer, Integer>> corruptedMemory = input.stream().map(e -> {
                String[] pos = e.split(",");
                return Map.entry(Integer.parseInt(pos[0]), Integer.parseInt(pos[1]));
            }).limit(corruptedMemoryCount).collect(Collectors.toSet());
            Queue<Map.Entry<Integer, Integer>> queue = new ArrayDeque<>();
            queue.add(Map.entry(0, 0));
            shortestPath.put(Map.entry(0, 0), 0);
            while (!queue.isEmpty()) {
                Map.Entry<Integer, Integer> pos = queue.poll();
                int distance = shortestPath.get(pos);
                for (Map.Entry<Integer, Integer> dir : List.of(Map.entry(0, 1), Map.entry(0, -1), Map.entry(1, 0), Map.entry(-1, 0))) {
                    Map.Entry<Integer, Integer> newPos = Map.entry(pos.getKey() + dir.getKey(), pos.getValue() + dir.getValue());
                    if (corruptedMemory.contains(newPos) || newPos.getKey() < 0 || newPos.getKey() > width || newPos.getValue() < 0 || newPos.getValue() > height) {
                        continue;
                    }
                    if (!shortestPath.containsKey(newPos) || distance + 1 < shortestPath.get(newPos)) {
                        shortestPath.put(newPos, distance + 1);
                        queue.add(newPos);
                    }
                }
            }
        } while (shortestPath.get(Map.entry(width, height)) != null);
        System.out.println(input.get(corruptedMemoryCount - 1));
    }
}