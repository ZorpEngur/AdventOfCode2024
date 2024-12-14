package Day14;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part2 {

    public static void main(String[] args) throws IOException, InterruptedException {
        int wx = 101;
        int wy = 103;
        Pattern pattern = Pattern.compile("p=(?<x>.+),(?<y>.+) v=(?<vx>.+),(?<vy>.+)");
        List<Robot> robots = Files.lines(Path.of("src/Day14/input.txt")).map(e -> {
            Matcher m = pattern.matcher(e);
            m.matches();
            return new Robot(Integer.parseInt(m.group("x")), Integer.parseInt(m.group("y")), Integer.parseInt(m.group("vx")), Integer.parseInt(m.group("vy")));
        }).toList();
        int sec = 0;
        while (true) {
            sec++;
            Set<String> cache = new HashSet<>();
            for (Robot robot : robots) {
                int rx = (robot.x + robot.vx) % wx;
                if (rx < 0) {
                    rx = wx + rx;
                }
                int ry = (robot.y + robot.vy) % wy;
                if (ry < 0) {
                    ry = wy + ry;
                }
                robot.x = rx;
                robot.y = ry;
                cache.add(rx + " " + ry);
            }
            List<String> picture = new ArrayList<>();
            int maxAdjust = 0;
            for (int i = 0; i < wy; i++) {
                StringBuilder line = new StringBuilder();
                int adjust = 0;
                for (int z = 0; z < wx; z++) {
                    int x = z;
                    int y = i;
                    if (cache.contains(x + " " + y)) {
                        line.append("0");
                        adjust++;
                    } else {
                        line.append(" ");
                        maxAdjust = Math.max(maxAdjust, adjust);
                        adjust = 0;
                    }
                }
                maxAdjust = Math.max(maxAdjust, adjust);
                picture.add(line.toString());
            }
            if (maxAdjust > 10) {
                picture.forEach(System.out::println);
                System.out.println("Seconds " + sec);
                break; // Works for my input, but nothing guaranties that there will be no false positives.
            }
        }
    }

    private static class Robot {
        int x;
        int y;
        int vx;
        int vy;

        public Robot(int x, int y, int vx, int vy) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
        }
    }
}