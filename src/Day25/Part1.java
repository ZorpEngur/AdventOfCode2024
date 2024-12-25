package Day25;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Part1 {

    public static void main(String[] args) throws IOException {
        List<List<Integer>> keys = new ArrayList<>();
        List<List<Integer>> locks = new ArrayList<>();
        List<String> lines = Files.lines(Path.of("src/Day25/input.txt")).toList();
        for (int i = 0; i < lines.size(); i += 8) {
            if (lines.get(i).contains("#")) {
                List<Integer> lock = new ArrayList<>();
                for (int x = 0; x < 5; x++) {
                    for (int y = 0; y < 7; y++) {
                       if (lines.get(i + 6 - y).charAt(x) == '#') {
                           lock.add(6 - y);
                           break;
                       }
                    }
                }
                locks.add(lock);
            } else {
                List<Integer> key = new ArrayList<>();
                for (int x = 0; x < 5; x++) {
                    for (int y = 0; y < 7; y++) {
                        if (lines.get(i + y).charAt(x) == '#') {
                            key.add(6 - y);
                            break;
                        }
                    }
                }
                keys.add(key);
            }
        }
        int result = 0;
        for (List<Integer> key : keys) {
            locks : for(List<Integer> lock : locks) {
                for (int i = 0; i < key.size(); i++) {
                    if (key.get(i) + lock.get(i) > 5) {
                        continue locks;
                    }
                }
                result += 1;
            }
        }
        System.out.println(result);
    }
}