package Day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Part2 {
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.lines(Path.of("src/Day4/input.txt")).toList();
        System.out.println(findWord(lines));
    }
    
    private static int findWord(List<String> lines) {
        int result = 0;
        for (int i = 1; i < lines.size() - 1; i++) {
            String line = lines.get(i);
            for (int x = 1; x < line.length() - 1; x++) {
                if (line.charAt(x) == 'A') {
                    if (((lines.get(i - 1).charAt(x - 1) == 'M' && lines.get(i + 1).charAt(x + 1) == 'S') || (lines.get(i - 1).charAt(x - 1) == 'S' && lines.get(i + 1).charAt(x + 1) == 'M')) &&
                            ((lines.get(i - 1).charAt(x + 1) == 'M' && lines.get(i + 1).charAt(x - 1) == 'S') || (lines.get(i - 1).charAt(x + 1) == 'S' && lines.get(i + 1).charAt(x - 1) == 'M'))) {
                        result++;
                    }
                }
            }
        }
        return result;
    }
}