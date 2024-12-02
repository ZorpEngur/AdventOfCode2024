package Day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Part2 {
    public static void main(String[] args) throws IOException {
        System.out.println(Files.lines(Path.of("src/Day2/input.txt")).filter(e -> {
            if (e.trim().isEmpty()) {
                return false;
            }
            List<Integer> numbers = Arrays.stream(e.split("\\D+")).map(Integer::parseInt).toList();
            for (int i = 0; i < numbers.size(); i++) {
                List<Integer> modNum = new ArrayList<>(numbers);
                modNum.remove(i);
                if (validate(modNum)) {
                    return true;
                }
            }
            return false;
        }).count());
    }

    public static boolean validate(List<Integer> numbers) {
        int oldDiff = 0;
        for (int i = 0; i < numbers.size() - 1; i++) {
            int diff = numbers.get(i) - numbers.get(i+1);
            if (!(Math.abs(diff) <= 3 && Math.abs(diff) >= 1) || (oldDiff != 0 && Integer.signum(oldDiff) != Integer.signum(diff))) {
                return false;
            }
            oldDiff = diff;
        }
        return true;
    }
}