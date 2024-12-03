package Day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part1 {
    public static void main(String[] args) throws IOException {
        System.out.println(Files.lines(Path.of("src/Day3/input.txt")).map(e -> {
            Matcher input = Pattern.compile("mul\\((?<num1>\\d{1,3}),(?<num2>\\d{1,3})\\)").matcher(e);
            return input.results().map(m -> Integer.parseInt(m.group("num1")) * Integer.parseInt(m.group("num2"))).reduce(Integer::sum).orElse(0);
        }).reduce(Integer::sum));
    }
}