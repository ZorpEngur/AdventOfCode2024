package Day12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Part2 {

    public static void main(String[] args) throws IOException {
        List<List<String>> field = Files.lines(Path.of("src/Day12/input.txt"))
                .map(e -> Arrays.stream(e.split("")).collect(Collectors.toCollection(ArrayList::new)))
                .collect(Collectors.toCollection(ArrayList::new));
        int result = 0;
        for (int i = 0; i < field.size(); i++) {
            for (int z = 0; z < field.getFirst().size(); z++) {
                if (field.get(z).get(i) != null) {
                    List<Map.Entry<Integer, Integer>> coordinates = coordinates(field, i, z);
                    result += freeSites(coordinates) * coordinates.size();
                }
            }
        }
        System.out.println(result);
    }
    
    private static List<Map.Entry<Integer, Integer>> coordinates(List<List<String>> field, int x, int y) {
        List<Map.Entry<Integer, Integer>> pos = new ArrayList<>();
        String crop = field.get(y).get(x);
        field.get(y).set(x, null);
        if (x > 0) {
            if (crop.equals(field.get(y).get(x - 1))) {
               pos.addAll(coordinates(field, x - 1, y));
            }
        } if (y > 0) {
            if (crop.equals(field.get(y - 1).get(x))) {
                pos.addAll(coordinates(field, x, y - 1));
            }
        } if (x < field.getFirst().size() - 1) {
            if (crop.equals(field.get(y).get(x + 1))) {
                pos.addAll(coordinates(field, x + 1, y));
            }
        } if (y < field.size() - 1) {
            if (crop.equals(field.get(y + 1).get(x))) {
                pos.addAll(coordinates(field, x, y + 1));
            }
        }
        pos.add(Map.entry(x, y));
        return pos;
    }
    
    private static int freeSites(List<Map.Entry<Integer, Integer>> coordinates) {
        int result = 0;
        for (Map.Entry<Integer, Integer> pos : coordinates) {
            if (!coordinates.contains(Map.entry(pos.getKey() - 1, pos.getValue())) && (
                    !coordinates.contains(Map.entry(pos.getKey(), pos.getValue() + 1)) || coordinates.contains(Map.entry(pos.getKey() - 1, pos.getValue() + 1)))) {
                result++;
            }
            if (!coordinates.contains(Map.entry(pos.getKey(), pos.getValue() - 1)) && (
                    !coordinates.contains(Map.entry(pos.getKey() + 1, pos.getValue())) || coordinates.contains(Map.entry(pos.getKey() + 1, pos.getValue() - 1)))) {
                result++;
            }
            if (!coordinates.contains(Map.entry(pos.getKey() + 1, pos.getValue())) && (
                    !coordinates.contains(Map.entry(pos.getKey(), pos.getValue() + 1)) || coordinates.contains(Map.entry(pos.getKey() + 1, pos.getValue() + 1)))) {
                result++;
            }
            if (!coordinates.contains(Map.entry(pos.getKey(), pos.getValue() + 1)) && (
                    !coordinates.contains(Map.entry(pos.getKey() + 1, pos.getValue())) || coordinates.contains(Map.entry(pos.getKey() + 1, pos.getValue() + 1)))) {
                result++;
            }
        }
        return result;
    }

}