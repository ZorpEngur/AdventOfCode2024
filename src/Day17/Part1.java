package Day17;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Part1 {

    private static int A = 0;
    private static int B = 0;
    private static int C = 0;
    private static int pointer = 0;
    private static Map<Integer, Operation> operations = Map.of(0, new Adv(), 1, new Bxl(), 2, new Bst(), 3, new Jnz(), 4, new Bxc(), 5, new Out(), 6, new Bdv(), 7, new Cdv());

    public static void main(String[] args) throws IOException {
        List<String> input = Files.lines(Path.of("src/Day17/input.txt")).toList();
        A = Integer.parseInt(input.get(0).substring(12));
        B = Integer.parseInt(input.get(1).substring(12));
        C = Integer.parseInt(input.get(2).substring(12));
        List<Integer> program = Arrays.stream(input.get(4).substring(9).split(",")).map(Integer::parseInt).toList();
        while (pointer < program.size()) {
            int instruction = program.get(pointer);
            int operand = program.get(pointer + 1);
            pointer += 2;
            operations.get(instruction).apply(operand);
        }
        System.out.println(); // btw u need to delete the last ','
        System.out.println(A + " " + B + " " + C);
    }

    private interface Operation {
        void apply(int operand);
        default int transformOperand(int operand) {
            if (operand == 4) return A;
            if (operand == 5) return B;
            if (operand == 6) return C;
            return operand;
        }
    }

    private static class Adv implements Operation {
        @Override
        public void apply(int operand) {
            A = A / ((int)Math.pow(2, transformOperand(operand)));
        }
    }

    private static class Bxl implements Operation {
        @Override
        public void apply(int operand) {
            B = B ^ operand;
        }
    }

    private static class Bst implements Operation { //cor
        @Override
        public void apply(int operand) {
            B = transformOperand(operand) % 8;
        }
    }

    private static class Jnz implements Operation {
        @Override
        public void apply(int operand) {
            if (A != 0) {
                pointer = operand;
            }
        }
    }

    private static class Bxc implements Operation {
        @Override
        public void apply(int operand) {
            B = B ^ C;
        }
    }

    private static class Out implements Operation { //cor
        @Override
        public void apply(int operand) {
            System.out.print(transformOperand(operand) % 8 + ",");
        }
    }

    private static class Bdv implements Operation {
        @Override
        public void apply(int operand) {
            B = A / ((int)Math.pow(2, transformOperand(operand)));
        }
    }

    private static class Cdv implements Operation {
        @Override
        public void apply(int operand) {
            C = A / ((int)Math.pow(2, transformOperand(operand)));
        }
    }
}