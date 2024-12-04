package Day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Part1 {
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.lines(Path.of("src/Day4/input.txt")).toList();
        System.out.println(findWord(lines, "XMAS") + findWord(lines, "SAMX"));
    }
    
    private static int findWord(List<String> lines, String word) {
        int result = 0;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) == word.charAt(0)) {
                    result += findHorizontal(line, word, x);
                    result += findVertical(lines, i, word, x);
                    result += findDiagonal(lines, i, word, x, 1);
                    result += findDiagonal(lines, i, word, x, -1);
                }
            }
        }
        return result;
    }
    
    private static int findHorizontal(String line, String word, int pos) {
        for (int i = pos; i < line.length(); i++) {
            if (line.charAt(i) != word.charAt(i - pos)) {
                break;
            }
            if (i - pos == word.length() - 1) {
                return 1;
            }
        }
        return 0;
    }
    
    private static int findVertical(List<String> lines, int posY, String word, int posX) {
        for (int i = posY; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.charAt(posX) != word.charAt(i - posY)) {
                break;
            }
            if (i - posY == word.length() - 1) {
                return 1;
            }
        }
        return 0;
    }
    
    private static int findDiagonal(List<String> lines, int posY, String word, int posX, int direction) {
        for (int i = posY; i < lines.size(); i++) {
            String line = lines.get(i);
            int letter = i - posY;
            if (posX + letter*direction > line.length() - 1 || posX + letter*direction < 0) {
                break;
            }
            if (line.charAt(posX + letter*direction) != word.charAt(letter)) {
                break;
            }
            if (letter == word.length() - 1) {
                return 1;
            }
        }
        return 0;
    }
}