package Day14;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part2 {

    public static void main(String[] args) throws IOException, InterruptedException {
        int wx = 101;
        int wy = 103;
        File file = new File("output.txt");
        file.createNewFile();
        FileWriter fw = new FileWriter("output.txt");
        Pattern pattern = Pattern.compile("p=(?<x>.+),(?<y>.+) v=(?<vx>.+),(?<vy>.+)");
        List<Robot> robots = Files.lines(Path.of("src/Day14/input.txt")).map(e -> {
            Matcher m = pattern.matcher(e);
            m.matches();
            return new Robot(Integer.parseInt(m.group("x")), Integer.parseInt(m.group("y")), Integer.parseInt(m.group("vx")), Integer.parseInt(m.group("vy")));
        }).toList();
        int sec = 0;
        while (true) {
            sec++;
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
            }
            for (int i = 0; i < wy; i++) {
                StringBuilder line = new StringBuilder();
                for (int z = 0; z < wx; z++) {
                    int x = z;
                    int y = i;
                    if (robots.stream().anyMatch(r -> r.x == x && r.y == y)) {
                        line.append("0");
                    } else {
                        line.append(" ");
                    }
                }
                line.append("\n");
                fw.append(line.toString());
            }
            fw.append("Seconds ").append(String.valueOf(sec)).append("\n\n");
            System.out.println("Seconds " + sec);
            if (sec > 20000) {
                fw.close();
                break; // Now go search for a bunch of 0 together. :P
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