package Day22;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Part1 {

    public static void main(String[] args) throws IOException {
        List<Integer> numbers = Files.lines(Path.of("src/Day22/input.txt")).map(Integer::parseInt).toList();
        long result = 0;
        for (Integer secret : numbers) {
            long newSecret = secret;
            for (int i = 0; i < 2000; i++) {
                newSecret = calculate(newSecret);
            }
            result += newSecret;
        }
        System.out.println(result);
    }

    private static long calculate(long secret) {
        secret = ((secret * 64) ^ secret) % 16777216;
        secret = ((secret / 32) ^ secret) % 16777216;
        return ((secret * 2048) ^ secret) % 16777216;
    }
}