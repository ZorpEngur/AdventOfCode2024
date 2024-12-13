package Day13;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part2 {

    public static void main(String[] args) throws IOException {
        List<String> input = Files.lines(Path.of("src/Day13/input.txt")).toList();
        Pattern pattern = Pattern.compile("\\D+(?<x>\\d+)\\D+(?<y>\\d+)");
        long result = 0;
        for (int i = 0; i < input.size(); i += 4) {
            Matcher buttonA = pattern.matcher(input.get(i));
            buttonA.matches();
            Matcher buttonB = pattern.matcher(input.get(i+1));
            buttonB.matches();
            Matcher prize = pattern.matcher(input.get(i+2));
            prize.matches();
            result += findResult(new Arcade(buttonA.group("x"), buttonA.group("y"), buttonB.group("x"), buttonB.group("y"), prize.group("x"), prize.group("y")));
        }
        System.out.println(result);
    }

    /**
     * Credits to SimonBaars. I came here to do programing challenges not math.
     * Solution copied from <a href="https://github.com/SimonBaars/AdventOfCode-Java/blob/master/src/main/java/com/sbaars/adventofcode/year24/days/Day13.java">https://github.com/SimonBaars/AdventOfCode-Java/blob/master/src/main/java/com/sbaars/adventofcode/year24/days/Day13.java</a>
     */
    private static long findResult(Arcade arcade) {
        long numerator = arcade.prizeX * arcade.buttonAY - arcade.prizeY * arcade.buttonAX;
        long b = numerator / ((long)arcade.buttonBX * (long)arcade.buttonAY - (long)arcade.buttonBY * (long)arcade.buttonAX);
        long remX = arcade.prizeX - b * arcade.buttonBX;
        long l = arcade.buttonAX == 0 ? arcade.prizeY : remX;
        long r = arcade.buttonAX == 0 ? arcade.buttonAY : arcade.buttonAX;
        long a = l / r;
        return (a * arcade.buttonAY + b * arcade.buttonBY == arcade.prizeY && l % r == 0) ? 3 * a + b : 0;
    }
    
    private static class Arcade {
        public int buttonAX;
        public int buttonAY;
        public int buttonBX;
        public int buttonBY;
        public long prizeX;
        public long prizeY;

        public Arcade(String buttonAX, String buttonAY, String buttonBX, String buttonBY, String prizeX, String prizeY) {
            this.buttonAX = Integer.parseInt(buttonAX);
            this.buttonAY = Integer.parseInt(buttonAY);
            this.buttonBX = Integer.parseInt(buttonBX);
            this.buttonBY = Integer.parseInt(buttonBY);
            this.prizeX = Integer.parseInt(prizeX) + 10000000000000L;
            this.prizeY = Integer.parseInt(prizeY) + 10000000000000L;
        }

        @Override
        public String toString() {
            return "Arcade{" +
                    "buttonAX=" + buttonAX +
                    ", buttonAY=" + buttonAY +
                    ", buttonBX=" + buttonBX +
                    ", buttonBY=" + buttonBY +
                    ", prizeX=" + prizeX +
                    ", prizeY=" + prizeY +
                    '}';
        }
    }
}