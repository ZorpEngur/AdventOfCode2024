package Day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part2 {
    public static void main(String[] args) throws IOException {
        String string = Files.lines(Path.of("src/Day3/input.txt")).reduce(String::concat).get();
        Matcher input = Pattern.compile("mul\\((?<num1>\\d{1,3}),(?<num2>\\d{1,3})\\)|(?<do>do\\(\\))|(?<dont>don't\\(\\))").matcher(string);
        boolean isDo = true;
        int result = 0;
        for (MatchResult match : input.results().toList()) {
            if (match.group("dont") != null) {
                isDo = false;
            } else if (match.group("do") != null) {
                isDo = true;
            } else if (isDo) {
                result += Integer.parseInt(match.group("num1")) * Integer.parseInt(match.group("num2"));
            }
        }
        System.out.println(result);
    }
}