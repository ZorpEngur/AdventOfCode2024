package Day22;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Part2 {

    public static void main(String[] args) throws IOException {
        List<Integer> numbers = Files.lines(Path.of("src/Day22/input.txt")).map(Integer::parseInt).toList();
        Map<String, Long> sequences = new HashMap<>();
        for (Integer secret : numbers) {
            Map<String, Long> numberSequences = new HashMap<>();
            long newSecret = secret;
            long lastPrice = secret % 10;
            LinkedList<Long> priceChanges = new LinkedList<>();
            for (int i = 0; i < 2000; i++) {
                newSecret = calculate(newSecret);
                long newPrice = newSecret % 10;
                priceChanges.addLast(newPrice - lastPrice);
                lastPrice = newPrice;
                if (priceChanges.size() >= 4) {
                    if (!numberSequences.containsKey(priceChanges.toString())) {
                        numberSequences.put(priceChanges.toString(), newPrice);
                    }
                    priceChanges.removeFirst();
                }
            }
            numberSequences.forEach((key, value) -> sequences.merge(key, value, Long::sum));
        }
        System.out.println(sequences.entrySet().stream().max(Map.Entry.comparingByValue()).get());
    }

    private static long calculate(long secret) {
        secret = ((secret * 64) ^ secret) % 16777216;
        secret = ((secret / 32) ^ secret) % 16777216;
        return ((secret * 2048) ^ secret) % 16777216;
    }
}