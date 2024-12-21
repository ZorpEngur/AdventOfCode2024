package Day21;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Part1 {

    private static Map<String, String> paths = new HashMap<>();

    static {
        paths.put("A^", "<A");
        paths.put("Av", "<vA");
        paths.put("A<", "v<<A");
        paths.put("A>", "vA");

        paths.put("^A", ">A");
        paths.put("^v", "vA");
        paths.put("^<", "v<A");
        paths.put("^>", "v>A");

        paths.put("v<", "<A");
        paths.put("v>", ">A");
        paths.put("v^", "^A");
        paths.put("vA", ">^A");

        paths.put(">A", "^A");
        paths.put(">v", "<A");
        paths.put(">^", "<^A");
        paths.put("><", "<<A");

        paths.put("<A", ">>^A");
        paths.put("<^", ">^A");
        paths.put("<v", ">A");
        paths.put("<>", ">>A");
    }

    public static void main(String[] args) throws IOException {
        List<Integer> result = new ArrayList<>();
        Files.lines(Path.of("src/Day21/input1.txt")).forEach(line -> {
            String path = line.substring(5);
            char pos;
            StringBuilder pathBuilder = null;
            for (int i = 0; i < 2; i++) {
                pos = 'A';
                pathBuilder = new StringBuilder();
                for (char c : path.toCharArray()) {
                    if (c == pos) {
                        pathBuilder.append("A");
                    } else {
                        pathBuilder.append(paths.get("" + pos + c));
                    }
                    pos = c;
                }
                path = pathBuilder.toString();
            }
            result.add(pathBuilder.length() * Integer.parseInt(line.substring(0, 3)));
        });
        System.out.println(result.stream().reduce(Integer::sum).get());
    }

}