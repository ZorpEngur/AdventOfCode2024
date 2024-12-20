package Day20;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Part2 {

    private static Map<Map.Entry<Integer, Integer>, Integer> shortestPath = new HashMap<>();

    public static void main(String[] args) throws IOException {
        List<String> map = Files.lines(Path.of("src/Day20/input.txt")).toList();
        Map.Entry<Integer, Integer> start = null;
        Map.Entry<Integer, Integer> end = null;
        for (String line : map) {
            if (line.contains("S")) {
                start = Map.entry(line.indexOf('S'), map.indexOf(line));
            }
            if (line.contains("E")) {
                end = Map.entry(line.indexOf('E'), map.indexOf(line));
            }
        }
        Queue<Map.Entry<Integer, Integer>> queue = new ArrayDeque<>();
        queue.add(start);
        shortestPath.put(start, 0);
        while (!queue.isEmpty()) {
            Map.Entry<Integer, Integer> pos = queue.poll();
            int distance = shortestPath.get(pos);
            for (Map.Entry<Integer, Integer> dir : List.of(Map.entry(0, 1), Map.entry(0, -1), Map.entry(1, 0), Map.entry(-1, 0))) {
                Map.Entry<Integer, Integer> newPos = Map.entry(pos.getKey() + dir.getKey(), pos.getValue() + dir.getValue());
                if (map.get(newPos.getValue()).charAt(newPos.getKey()) == '#') {
                    continue;
                }
                if (!shortestPath.containsKey(newPos) || distance + 1 < shortestPath.get(newPos)) {
                    shortestPath.put(newPos, distance + 1);
                    queue.add(newPos);
                }
            }
        }
        System.out.println(shortestPath.get(end));
        System.out.println(countCheats());
    }

    private static int countCheats() {
        List<Map.Entry<Map.Entry<Integer, Integer>, Integer>> paths = shortestPath.entrySet().stream().toList();
        Map<Integer, Integer> resultMap = new HashMap<>();
        for (int i = 0; i < paths.size(); i++) {
            for (int z = i + 1; z < paths.size(); z++) {
                Map.Entry<Map.Entry<Integer, Integer>, Integer> p1 = paths.get(i);
                Map.Entry<Map.Entry<Integer, Integer>, Integer> p2 = paths.get(z);
                if (Math.abs(p1.getKey().getKey() - p2.getKey().getKey()) + Math.abs(p1.getKey().getValue() - p2.getKey().getValue()) <= 20) {
                    int diff = Math.abs(Math.abs(p1.getValue() - p2.getValue()) - Math.abs(p1.getKey().getKey() - p2.getKey().getKey()) - Math.abs(p1.getKey().getValue() - p2.getKey().getValue()));
                    if (diff > 99) {
                        resultMap.merge(diff, 1, Integer::sum);
                    }
                }
            }
        }
        System.out.println(resultMap);
        return resultMap.values().stream().reduce(Integer::sum).get();
    }
}