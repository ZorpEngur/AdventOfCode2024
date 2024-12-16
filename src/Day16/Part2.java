package Day16;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Part2 {

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
        Set<Map.Entry<Integer, Integer>> seats = findSeats(floodFill.get(Map.entry(xe, ye)), Map.entry(xe, ye), Map.entry(xs, ys), 0, null, 0);
        System.out.println(seats.size());
        for (int y = 0; y < map.size(); y++) {
            for (int x = 0; x < map.getFirst().length(); x++) {
                if (seats.contains(Map.entry(x, y))) {
                    System.out.print(" ");
                    System.out.print(floodFill.get(Map.entry(x, y)));
                    for (int i = String.valueOf(floodFill.get(Map.entry(x, y))).length(); i < 6; i++) {
                        System.out.print(" ");
                    }
                    System.out.print(" ");
                } else {
                    for (int i = 0; i < 8; i++) {
                        System.out.print(map.get(y).charAt(x));
                    }
                }
            }
            System.out.println();
        }
        System.out.println(seats.size());
    }

    private static Set<Map.Entry<Integer, Integer>> findSeats(int score, Map.Entry<Integer, Integer> pos, Map.Entry<Integer, Integer> start, int prev, Map.Entry<Integer, Integer> prevPos, int realPreviousScore) {
        if (pos.equals(start)) {
            return Set.of(pos);
        }
        Set<Map.Entry<Integer, Integer>> result = new HashSet<>();
        for (Map.Entry<Integer, Integer> v : List.of(Map.entry(1, 0), Map.entry(0, 1), Map.entry(-1, 0) ,Map.entry(0, -1))) {
            Map.Entry<Integer, Integer> newPos = Map.entry(pos.getKey() + v.getKey(), pos.getValue() + v.getValue());
            if (!floodFill.containsKey(newPos) || (!(floodFill.get(newPos) < score) && prev - 2 != floodFill.get(newPos)) || newPos.equals(prevPos)) {
                continue;
            }
            if (prevPos != null && !(floodFill.get(prevPos) - floodFill.get(newPos) == 2 || floodFill.get(prevPos) - floodFill.get(newPos) == 1002 || realPreviousScore - floodFill.get(pos) == 1 || realPreviousScore - floodFill.get(pos) == 1001)) {
                continue;
            }
            Set<Map.Entry<Integer, Integer>> res = findSeats(floodFill.get(newPos), newPos, start, score, pos, prevPos == null ? 0 : floodFill.get(prevPos) - floodFill.get(newPos) == 2 ? floodFill.get(prevPos) -1 : floodFill.get(prevPos) - 1001);
            if (!res.isEmpty()) {
                result.addAll(res);
                result.add(pos);
            }
        }
        return result;
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