package Day17;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Part2 {

    public static void main(String[] args) throws IOException {
        List<String> input = Files.lines(Path.of("src/Day17/input.txt")).toList();
        List<Integer> program = Arrays.stream(input.get(4).substring(9).split(",")).map(Integer::parseInt).toList();
        System.out.println(find(program, 0));
    }

    private static Long find(List<Integer> program, long answer) {
        if (program.isEmpty()) {
            return answer;
        }
        for (int i = 0; i < 8; i ++) {
            long a = (answer << 3) + i;
            long b = a % 8;
            b = b ^ 2;
            long c = a >> b;
            b = b ^ 3;
            b = b ^ c;
            if (b % 8 == program.getLast()) {
                Long solution = find(program.subList(0, program.size() - 1), a);
                if (solution == null) {
                    continue;
                }
                return solution;
            }
        }
        return null;
    }
}