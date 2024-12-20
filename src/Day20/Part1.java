package Day20;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Part1 {

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
                Map.Entry<Integer, Integer> newPos =  Map.entry(pos.getKey() + dir.getKey(), pos.getValue() + dir.getValue());
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
        System.out.println(countCheats(map));
    }

    private static int countCheats(List<String> map) {
        Map<Integer, Integer> resultMap = new HashMap<>();
        for (int y = 1; y < map.size() - 1; y++) {
            Integer prev = null;
            for (int x = 1; x < map.getFirst().length() - 1; x++) {
                if (map.get(y).charAt(x) != '#') {
                    prev = shortestPath.get(Map.entry(x, y));
                } else if (prev != null) {
                    if (map.get(y).charAt(x + 1) != '#') {
                        resultMap.merge(Math.abs(prev - shortestPath.get(Map.entry(x + 1, y))) - 2, 1, Integer::sum);
                    }
                    prev = null;
                }
            }
        }
        for (int x = 1; x < map.size() - 1; x++) {
            Integer prev = null;
            for (int y = 1; y < map.getFirst().length() - 1; y++) {
                if (map.get(y).charAt(x) != '#') {
                    prev = shortestPath.get(Map.entry(x, y));
                } else if (prev != null) {
                    if (map.get(y + 1).charAt(x) != '#') {
                        resultMap.merge(Math.abs(prev - shortestPath.get(Map.entry(x, y + 1))) - 2, 1, Integer::sum);
                    }
                    prev = null;
                }
            }
        }
        System.out.println(resultMap);
        return resultMap.keySet().stream().filter(e -> e >= 100).map(resultMap::get).reduce(Integer::sum).get();
    }
}