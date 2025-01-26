package Day13;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

    private static long findResult(Arcade arcade) {
        long a = arcade.prizeX * arcade.buttonBY - arcade.buttonBX * arcade.prizeY * arcade.buttonBY / arcade.buttonBY;
        long b = arcade.prizeY * arcade.buttonAX - arcade.buttonAY * arcade.prizeX * arcade.buttonAX / arcade.buttonAX;
        long div = ((long) arcade.buttonAX * arcade.buttonBY - (long) arcade.buttonBX * arcade.buttonAY);
        if (a % div == 0 && b % div == 0) {
            return (a / div) * 3 + (b / div);
        }
        return 0;
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