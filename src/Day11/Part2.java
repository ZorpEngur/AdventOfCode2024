package Day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Part2 {
    
    public static Map<Map.Entry<Long, Integer>, Long> cache = new HashMap<>();

    public static void main(String[] args) throws IOException {
        List<Long> origin = Files.lines(Path.of("src/Day11/input.txt"))
                .map(e -> Arrays.stream(e.split(" ")).map(Long::parseLong).toList())
                .toList().getFirst();
        long result = 0;
        for (Long x : origin) {
            result += logic(x, 0);
        }
        System.out.println(result);
    }
    
    private static long logic(long origin, int start) {
        if (start == 75) {
            return 1;
        }
        Map.Entry<Long, Integer> pos = Map.entry(origin, start);
        if (cache.containsKey(pos)) {
            return cache.get(pos);
        }
        int len = (int) Math.log10(origin) + 1;
        long result = 0;
        if (origin == 0) {
            result += logic(1, start + 1);
        } else if (len % 2 == 0) {
            result += logic(origin / (int)Math.pow(10, len / 2), start + 1);
            result += logic(origin % (int)Math.pow(10, len / 2), start + 1);
        } else {
            result += logic(origin * 2024, start + 1); 
        }
        cache.put(pos, result);
        return result;
    }
}