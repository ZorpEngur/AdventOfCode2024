package Day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Part2 {

    private static final List<Operation> operations = List.of(new Plus(), new Multiply(), new Concat());

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.lines(Path.of("src/Day7/input.txt")).toList();
        long result = 0;
        for (String line : lines) {
            String[] parts = line.split(":");
            if (isEquation(Arrays.stream(parts[1].trim().split("\\D")).map(Long::parseLong).toList(), Long.parseLong(parts[0]))) {
                result += Long.parseLong(parts[0]);
            }
        }
        System.out.println(result);
    }

    private static boolean isEquation(List<Long> numbers, long result) {
        for (List<Operation> operations : generateOperations(numbers.size() - 1)) {
            long equationResult = numbers.getFirst();
            for (int i = 0; i < operations.size(); i++) {
                equationResult = operations.get(i).apply(equationResult, numbers.get(i +1));
            }
            if (result == equationResult) {
                return true;
            }
        }
        return false;
    }

    private static List<List<Operation>> generateOperations(int length) {
        List<List<Operation>> result = new ArrayList<>();
        generateOperationsRecursive(length, new ArrayList<>(), result);
        return result;
    }

    private static void generateOperationsRecursive(int length, List<Operation> current, List<List<Operation>> result) {
        if (length == 0) {
            result.add(current);
            return;
        }
        for (Operation op : operations) {
            List<Operation> operations = new ArrayList<>(current);
            operations.add(op);
            generateOperationsRecursive(length -1, operations, result);
        }
    }

    private interface Operation {
        long apply(long num1, long num2);
    }

    private static class Plus implements Operation {
        @Override
        public long apply(long num1, long num2) {
            return num1 + num2;
        }
    }

    private static class Multiply implements Operation {
        @Override
        public long apply(long num1, long num2) {
            return num1 * num2;
        }
    }

    private static class Concat implements Operation {
        @Override
        public long apply(long num1, long num2) {
            return Long.parseLong(String.valueOf(num1) + num2);
        }
    }
}