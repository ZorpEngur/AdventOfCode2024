package Day24;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Part2 {

    private static Map<String, Operation> operations = Map.of("AND", new And(), "OR", new Or(), "XOR", new Xor());

    public static void main(String[] args) throws IOException {
        List<Character> chars = List.of('x', 'y', 'z');
        Pattern wire = Pattern.compile("(?<wire>.{3}): (?<signal>\\d)");
        Pattern operation = Pattern.compile("(?<creation>(?<wireA>.{3}) (?<operation>OR|AND|XOR) (?<wireB>.{3})) -> (?<wireR>.{3})");
        Queue<Map.Entry<List<String>, Operation>> circuit = new ArrayDeque<>();
        Map<String, Boolean> wires = new HashMap<>();
        Set<String> wrong = new HashSet<>();
        Files.lines(Path.of("src/Day24/input.txt")).forEach(l -> {
            Matcher mw = wire.matcher(l);
            Matcher mo = operation.matcher(l);
            if (mw.matches()) {
                wires.put(mw.group("wire"), mw.group("signal").equals("1"));
            } else if (mo.matches()) {
                circuit.add(Map.entry(List.of(mo.group("wireA"), mo.group("wireB"), mo.group("wireR")), operations.get(mo.group("operation"))));
            }
        });
        for (Map.Entry<List<String>, Operation> op : circuit) {
            if (op.getKey().get(2).charAt(0) == 'z' && !(op.getValue() instanceof Xor) && !op.getKey().get(2).equals("z45")) {
                wrong.add(op.getKey().get(2));
            }
            if (op.getValue() instanceof Xor && op.getKey().stream().map(e -> e.charAt(0)).noneMatch(chars::contains)) {
                wrong.add(op.getKey().get(2));
            }
            if (op.getValue() instanceof And && op.getKey().stream().limit(2).noneMatch(e -> e.equals("x00"))) {
                for (Map.Entry<List<String>, Operation> subOp : circuit) {
                    if ((op.getKey().get(2).equals(subOp.getKey().get(0)) || op.getKey().get(2).equals(subOp.getKey().get(1))) && !(subOp.getValue() instanceof Or)) {
                        wrong.add(op.getKey().get(2));
                    }
                }
            }
            if (op.getValue() instanceof Xor) {
                for (Map.Entry<List<String>, Operation> subOp : circuit) {
                    if ((op.getKey().get(2).equals(subOp.getKey().get(0)) || op.getKey().get(2).equals(subOp.getKey().get(1))) && subOp.getValue() instanceof Or) {
                        wrong.add(op.getKey().get(2));
                    }
                }
            }
        }
        System.out.println(wrong.stream().sorted().collect(Collectors.joining(",")));
    }

    private interface Operation {
        Boolean apply(Boolean a, Boolean b);
    }

    private static class And implements Operation {
        @Override
        public Boolean apply(Boolean a, Boolean b) {
            if (a == null || b == null) {
                return null;
            }
            return a && b;
        }
    }

    private static class Or implements Operation {
        @Override
        public Boolean apply(Boolean a, Boolean b) {
            if (a == null || b == null) {
                return null;
            }
            return a || b;
        }
    }

    private static class Xor implements Operation { //cor
        @Override
        public Boolean apply(Boolean a, Boolean b) {
            if (a == null || b == null) {
                return null;
            }
            return a ^ b;
        }
    }
}