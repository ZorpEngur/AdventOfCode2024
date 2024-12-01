package Day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part1 {
    public static void main(String[] args) throws IOException {
        int result = 0;
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        Files.lines(Path.of("src/Day1/input.txt")).forEach(e -> {
            if (e.trim().isEmpty()) {
                return;
            }
            Matcher matcher = Pattern.compile("(?<num1>\\d+)\\D+(?<num2>\\d+)").matcher(e);
            matcher.matches();
            list1.add(Integer.valueOf(matcher.group("num1")));
            list2.add(Integer.valueOf(matcher.group("num2")));
        });
        list1.sort(Comparator.naturalOrder());
        list2.sort(Comparator.naturalOrder());
        for (int i = 0; i < list1.size(); i++) {
            result += Math.abs(list1.get(i) - list2.get(i));
        }
        System.out.println(result);
    }
}