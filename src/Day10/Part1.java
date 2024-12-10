package Day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Part1 {

    public static void main(String[] args) throws IOException {
        List<List<Integer>> map = Files.lines(Path.of("src/Day10/input.txt"))
                .map(e -> Arrays.stream(e.split(""))
                        .map(x -> x.equals(".") ? -1 : Integer.parseInt(x)).toList())
                .toList();
        int result = 0;
        for (int x = 0; x < map.size(); x++) {
            for (int y = 0; y < map.getFirst().size(); y++) {
                if (map.get(x).get(y) == 0) {
                    Set<Map.Entry<Integer, Integer>> set = new HashSet<>();
                    path(map, x, y, 0, set);
                    result += set.size();
                }
            }
        }
        System.out.println(result);
    }
    
    private static void path(List<List<Integer>> map, int x, int y, int height, Set<Map.Entry<Integer, Integer>> result) {
        if (height == 9) {
            result.add(Map.entry(x, y));
        }
        for (Map.Entry<Integer, Integer> pos : List.of(Map.entry(x + 1 , y), Map.entry(x - 1, y), Map.entry(x, y + 1), Map.entry(x, y -1))) {
            if (pos.getKey() < 0 || pos.getKey() >= map.size() || pos.getValue() < 0 || pos.getValue() >= map.getFirst().size()) {
                continue;
            }
            int newPath = map.get(pos.getKey()).get(pos.getValue());
            if (newPath - height == 1) {
                path(map, pos.getKey(), pos.getValue(), newPath, result);
            }
        }
    }
}