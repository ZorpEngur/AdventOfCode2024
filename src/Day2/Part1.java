package Day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part1 {
    public static void main(String[] args) throws IOException {
        System.out.println(Files.lines(Path.of("src/Day2/input.txt")).filter(e -> {
            if (e.trim().isEmpty()) {
                return false;
            }
            List<Integer> numbers = Arrays.stream(e.split("\\D+")).map(Integer::parseInt).toList();
            int oldDiff = 0;
            for (int i = 0; i < numbers.size() - 1; i++) {
                int diff = numbers.get(i) - numbers.get(i+1);
                if (!(Math.abs(diff) <= 3 && Math.abs(diff) >= 1) || (oldDiff != 0 && Integer.signum(oldDiff) != Integer.signum(diff))) {
                    return false;
                }
                oldDiff = diff;
            }
            return true;
        }).count());
    }
}