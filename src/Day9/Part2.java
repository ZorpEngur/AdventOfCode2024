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

public class Part2 {

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.lines(Path.of("src/Day9/input.txt")).toList();
        List<Integer> disk = Arrays.stream(lines.getFirst().split("")).map(Integer::parseInt).collect(Collectors.toCollection(ArrayList::new));
        List<Integer> result = new ArrayList<>();
        Map<Integer, Integer> freeSpace = new HashMap<>();
        Map<Integer, Integer> files = new HashMap<>();
        long checksum = 0;
        for (int i = 0; i < disk.size(); i++) {
            if (i % 2 == 0) {
                files.put(i, disk.get(i));
            } else {
                freeSpace.put(i, disk.get(i));
            }
        }
        for (int i = 0; i < disk.size(); i++) {
            if (files.containsKey(i)) {
                if (files.get(i) < 0) {
                    for (int x = 0; x > files.get(i); x--) {
                        result.add(0);
                    }
                }
                for (int x = 0; x < files.get(i); x++) {
                    result.add(i / 2);
                }
            } else if (freeSpace.containsKey(i)){
                int space = freeSpace.get(i);
                fill: while (space > 0) {
                    for (int x = disk.size() - 1; x > i; x--) {
                        if (files.containsKey(x) && files.get(x) >= 0) {
                            if (files.get(x) <= space) {
                                for (int z = 0; z < files.get(x); z++) {
                                    result.add(x / 2);
                                    space--;
                                }
                                files.put(x, files.get(x) * -1);
                                if (space > 0) {
                                    continue fill;
                                }
                            }
                        }
                    }
                    for (int x = 0; x < space; x++) {
                        result.add(0);
                    }
                    break;
                }
            }
        }
        for (int i = 0; i < result.size(); i++) {
            checksum += result.get(i) * i;
        }
        System.out.println(checksum);
    }
}