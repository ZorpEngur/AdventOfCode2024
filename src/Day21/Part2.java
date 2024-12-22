package Day21;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Part2 {

    private static Map<Character, Map.Entry<Integer, Integer>> m1 = new HashMap<>();
    private static Map<Character, Map.Entry<Integer, Integer>> m2 = new HashMap<>();
    private static Map<Character, Map.Entry<Integer, Integer>> dir = new HashMap<>();
    private static Map<String, Long> cache = new HashMap<>();

    static {
        m1.put('7', Map.entry(0, 0));
        m1.put('8', Map.entry(1, 0));
        m1.put('9', Map.entry(2, 0));
        m1.put('4', Map.entry(0, 1));
        m1.put('5', Map.entry(1, 1));
        m1.put('6', Map.entry(2, 1));
        m1.put('1', Map.entry(0, 2));
        m1.put('2', Map.entry(1, 2));
        m1.put('3', Map.entry(2, 2));
        m1.put('0', Map.entry(1, 3));
        m1.put('A', Map.entry(2, 3));

        m2.put('^', Map.entry(1, 0));
        m2.put('A', Map.entry(2, 0));
        m2.put('<', Map.entry(0, 1));
        m2.put('v', Map.entry(1, 1));
        m2.put('>', Map.entry(2, 1));

        dir.put('^', Map.entry(0, -1));
        dir.put('>', Map.entry(1, 0));
        dir.put('<', Map.entry(-1, 0));
        dir.put('v', Map.entry(0, 1));
    }

    public static void main(String[] args) throws IOException {
        List<Long> result = new ArrayList<>();
        Files.lines(Path.of("src/Day21/input.txt")).forEach(line -> {
            result.add(bigKeypad(line) * Integer.parseInt(line.substring(0, 3)));
        });
        System.out.println(result.stream().reduce(Long::sum).get());
    }

    private static long bigKeypad(String code) {
        char pos = 'A';
        long result = 0;
        for (char c : code.toCharArray()) {
            List<Character> dir = directions(m1.get(pos), m1.get(c));
            result += smallKeypad(dir, 25, m1.get(pos), true);
            pos = c;
        }
        return result;
    }

    private static long smallKeypad(List<Character> dirs, int depth, Map.Entry<Integer, Integer> pos, boolean bigKeypad) {
        if (depth == 0) {
            return dirs.size() + 1;
        }
        if (cache.containsKey(dirs.toString() + depth + pos)) {
            return cache.get(dirs.toString() + depth + pos);
        }
        Set<List<Character>> perms = filteredPerm(new ArrayList<>(dirs), pos, bigKeypad);
        long result = Long.MAX_VALUE;
        for (List<Character> line : perms) {
            char prev = 'A';
            line.add('A');
            long smallestResult = 0;
            for (char c : line) {
                smallestResult += smallKeypad(directions(m2.get(prev), m2.get(c)), depth - 1, m2.get(prev), false);
                prev = c;
            }
            result = Math.min(smallestResult, result);
        }
        cache.put(dirs.toString() + depth + pos, result);
        return result;
    }

    private static List<Character> directions(Map.Entry<Integer, Integer> cur, Map.Entry<Integer, Integer> next) {
        List<Character> result = new ArrayList<>();
        for (int i = 0; i < Math.abs(next.getValue() - cur.getValue()); i++) {
            if (Math.signum(next.getValue() - cur.getValue()) < 0) {
                result.add('^');
            } else {
                result.add('v');
            }
        }
        for (int i = 0; i < Math.abs(next.getKey() - cur.getKey()); i++) {
            if (Math.signum(next.getKey() - cur.getKey()) < 0) {
                result.add('<');
            } else {
                result.add('>');
            }
        }
        return result;
    }

    private static Set<List<Character>> filteredPerm(List<Character> original, Map.Entry<Integer, Integer> position, boolean bigKeypad) {
        return generatePerm(original).stream().filter(e -> {
            Map.Entry<Integer, Integer> pos = position;
            for (char c : e) {
                pos = Map.entry(pos.getKey() + dir.get(c).getKey(), pos.getValue() + dir.get(c).getValue());
                if (pos.getKey() == 0 && ((pos.getValue() == 3 && bigKeypad) || (pos.getValue() == 0 && !bigKeypad))) {
                    return false;
                }
            }
            return true;
        }).collect(Collectors.toSet());
    }

    private static Set<List<Character>> generatePerm(List<Character> original) {
        if (original.isEmpty()) {
            Set<List<Character>> result = new HashSet<>();
            result.add(new ArrayList<>());
            return result;
        }
        Character firstElement = original.removeFirst();
        Set<List<Character>> returnValue = new HashSet<>();
        Set<List<Character>> permutations = generatePerm(original);
        for (List<Character> smallerPermutated : permutations) {
            for (int index = 0; index <= smallerPermutated.size(); index++) {
                List<Character> temp = new ArrayList<>(smallerPermutated);
                temp.add(index, firstElement);
                returnValue.add(temp);
            }
        }
        return returnValue;
    }
}