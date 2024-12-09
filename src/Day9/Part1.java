package Day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Part1 {

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.lines(Path.of("src/Day9/input.txt")).toList();
        List<Integer> disk = Arrays.stream(lines.getFirst().split("")).map(Integer::parseInt).toList();
        List<Integer> result = new ArrayList<>();
        long checksum = 0;
        int end = disk.size() - 1;
        int rem = disk.get(end);
        for (int i = 0; i < disk.size(); i++) {
            if (i % 2 == 0) {
                for (int x = 0; x < disk.get(i); x++) {
                    result.add(i / 2);
                }
            } else {
                for (int x = 0; x < disk.get(i); x++) {
                    while (rem == 0) {
                        end -= 2;
                        rem = disk.get(end);
                    }
                    if (end <= i + 1) {
                        break;
                    }
                    result.add(end / 2);
                    rem--;
                }
            }
            if (end <= i + 1) {
                break;
            }
        }
        while (rem != 0) {
            result.add(end / 2);
            rem--;
        }
        for (int i = 0; i < result.size(); i++) {
            checksum += result.get(i) * i;
        }
        System.out.println(checksum);
    }
}