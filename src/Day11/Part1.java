package Day11;

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
        List<Long> origin = Files.lines(Path.of("src/Day11/input.txt"))
                .map(e -> Arrays.stream(e.split(" ")).map(Long::parseLong).toList())
                .toList().getFirst();
        List<Long> mod = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            for (Long x : origin) {
                String num = String.valueOf(x);
                if (x == 0) {
                    mod.add(1L);
                } else if (num.length() % 2 == 0) {
                    mod.add(Long.parseLong(num.substring(0, num.length() / 2)));
                    mod.add(Long.parseLong(num.substring(num.length() / 2)));
                } else {
                    mod.add(x * 2024);
                }
            }
            origin = mod;
            mod = new ArrayList<>();
        }
        System.out.println(origin.size());
    }
}