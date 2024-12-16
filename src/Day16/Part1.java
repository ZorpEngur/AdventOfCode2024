package Day16;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Part1 {

    private static Map<Map.Entry<Integer, Integer>, Integer> floodFill = new HashMap<>();

    public static void main(String[] args) throws IOException {
        List<String> map = Files.lines(Path.of("src/Day16/input.txt")).toList();
        int xs = 0;
        int ys = 0;
        int xe = 0;
        int ye = 0;
        for (String line : map) {
            if (line.contains("S")) {
                xs = line.indexOf('S');
                ys = map.indexOf(line);
            }
            if (line.contains("E")) {
                xe = line.indexOf('E');
                ye = map.indexOf(line);
            }
        }
        floodFill.put(Map.entry(xs, ys), 0);
        fill(map, xs, ys, 1, 0);
        System.out.println(floodFill.get(Map.entry(xe, ye)));
    }

    private static void fill(List<String> map, int x, int y, int vx, int vy) {
        if (map.get(y).charAt(x) == '#') {
            return;
        }
        if (map.get(y + vy).charAt(x + vx) != '#') {
            int score = floodFill.get(Map.entry(x, y)) + 1;
            Map.Entry<Integer, Integer> newPos = Map.entry(x + vx, y + vy);
            if (floodFill.get(newPos) == null || !(floodFill.get(newPos) < score)) {
                floodFill.put(newPos, score);
                fill(map, x + vx, y + vy, vx, vy);
            }
        }
        if (vx == 0) {
            if (map.get(y).charAt(x + 1) != '#') {
                int score = floodFill.get(Map.entry(x, y)) + 1001;
                Map.Entry<Integer, Integer> newPos = Map.entry(x + 1, y);
                if (floodFill.get(newPos) == null || !(floodFill.get(newPos) < score)) {
                    floodFill.put(newPos, score);
                    fill(map, x + 1, y, 1, 0);
                }
            }
            if (map.get(y).charAt(x - 1) != '#') {
                int score = floodFill.get(Map.entry(x, y)) + 1001;
                Map.Entry<Integer, Integer> newPos = Map.entry(x - 1, y);
                if (floodFill.get(newPos) == null || !(floodFill.get(newPos) < score)) {
                    floodFill.put(newPos, score);
                    fill(map, x - 1, y, -1, 0);
                }
            }
        } else if (vy == 0) {
            if (map.get(y + 1).charAt(x) != '#') {
                int score = floodFill.get(Map.entry(x, y)) + 1001;
                Map.Entry<Integer, Integer> newPos = Map.entry(x, y + 1);
                if (floodFill.get(newPos) == null || !(floodFill.get(newPos) < score)) {
                    floodFill.put(newPos, score);
                    fill(map, x, y + 1, 0, 1);
                }
            }
            if (map.get(y - 1).charAt(x) != '#') {
                int score = floodFill.get(Map.entry(x, y)) + 1001;
                Map.Entry<Integer, Integer> newPos = Map.entry(x, y - 1);
                if (floodFill.get(newPos) == null || !(floodFill.get(newPos) < score)) {
                    floodFill.put(newPos, score);
                    fill(map, x, y - 1, 0, -1);
                }
            }
        }
    }

}