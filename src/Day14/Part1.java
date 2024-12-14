package Day14;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part1 {

    public static void main(String[] args) throws IOException {
        int wx = 101;
        int wy = 103;
        int[] quadrants = new int[]{0, 0, 0, 0};
        Pattern pattern = Pattern.compile("p=(?<x>.+),(?<y>.+) v=(?<vx>.+),(?<vy>.+)");
        Files.lines(Path.of("src/Day14/input.txt")).forEach(e -> {
            Matcher m = pattern.matcher(e);
            m.matches();
            int x = Integer.parseInt(m.group("x"));
            int y = Integer.parseInt(m.group("y"));
            int vx = Integer.parseInt(m.group("vx"));
            int vy = Integer.parseInt(m.group("vy"));
            int rx = (x + vx * 100) % wx;
            if (rx < 0) {
                rx = wx + rx;
            }
            int ry = (y + vy * 100) % wy;
            if (ry < 0) {
                ry = wy + ry;
            }
            if (rx < wx / 2) {
                if (ry < wy / 2) {
                    quadrants[0] = quadrants[0] + 1;
                } else if (ry > wy / 2) {
                    quadrants[2] = quadrants[2] + 1;
                }
            } else if (ry < wy / 2 && rx > wx / 2) {
                quadrants[1] = quadrants[1] + 1;
            } else if (ry > wy / 2 && rx > wx / 2) {
                quadrants[3] = quadrants[3] + 1;
            }
//            System.out.println(rx + " " + ry);
        });
        System.out.println(Arrays.toString(quadrants));
        System.out.println(quadrants[0] * quadrants[1] * quadrants[2] * quadrants[3]);
    }
}