package Day13;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part1 {

    public static void main(String[] args) throws IOException {
        List<String> input = Files.lines(Path.of("src/Day13/input.txt")).toList();
        Pattern pattern = Pattern.compile("\\D+(?<x>\\d+)\\D+(?<y>\\d+)");
        int result = 0;
        List<Arcade> arcades = new ArrayList<>();
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
    
    private static int findResult(Arcade arcade) {
        int bCount = Math.min(arcade.prizeX / arcade.buttonBX, arcade.prizeY / arcade.buttonBY);
        int aCount = 0;
        if (arcade.buttonBX * bCount == arcade.prizeX && arcade.buttonBY * bCount == arcade.prizeY) {
            return bCount;
        }
        while (bCount > 0) {
            if (arcade.buttonBX * bCount + arcade.buttonAX * aCount == arcade.prizeX &&
                    arcade.buttonBY * bCount + arcade.buttonAY * aCount == arcade.prizeY) {
                return bCount + aCount * 3;
            } else if (arcade.buttonBX * bCount + arcade.buttonAX * aCount < arcade.prizeX &&
                    arcade.buttonBY * bCount + arcade.buttonAY * aCount < arcade.prizeY) {
                aCount++;
            } else {
                bCount--;
            }
        }
        return 0;
    }
    
    private static class Arcade {
        public int buttonAX;
        public int buttonAY;
        public int buttonBX;
        public int buttonBY;
        public int prizeX;
        public int prizeY;

        public Arcade(String buttonAX, String buttonAY, String buttonBX, String buttonBY, String prizeX, String prizeY) {
            this.buttonAX = Integer.parseInt(buttonAX);
            this.buttonAY = Integer.parseInt(buttonAY);
            this.buttonBX = Integer.parseInt(buttonBX);
            this.buttonBY = Integer.parseInt(buttonBY);
            this.prizeX = Integer.parseInt(prizeX);
            this.prizeY = Integer.parseInt(prizeY);
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