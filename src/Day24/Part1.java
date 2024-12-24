package Day24;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Part1 {

    private static Map<String, Operation> operations = Map.of("AND", new And(), "OR", new Or(), "XOR", new Xor());

    public static void main(String[] args) throws IOException {
        Pattern wire = Pattern.compile("(?<wire>.{3}): (?<signal>\\d)");
        Pattern operation = Pattern.compile("(?<wireA>.{3}) (?<operation>OR|AND|XOR) (?<wireB>.{3}) -> (?<wireR>.{3})");
        Queue<Map.Entry<List<String>, Operation>> circuit = new ArrayDeque<>();
        Map<String, Boolean> wires = new HashMap<>();
        Files.lines(Path.of("src/Day24/input.txt")).forEach(l -> {
            Matcher mw = wire.matcher(l);
            Matcher mo = operation.matcher(l);
            if (mw.matches()) {
                wires.put(mw.group("wire"), mw.group("signal").equals("1"));
            } else if (mo.matches()) {
                circuit.add(Map.entry(List.of(mo.group("wireA"), mo.group("wireB"), mo.group("wireR")), operations.get(mo.group("operation"))));
            }
        });

        while (!circuit.isEmpty()) {
            Map.Entry<List<String>, Operation> c = circuit.poll();
            Boolean res = c.getValue().apply(wires.get(c.getKey().get(0)), wires.get(c.getKey().get(1)));
            if (res == null) {
                circuit.add(c);
            } else {
                wires.put(c.getKey().get(2), res);
            }
        }
        System.out.println(Long.parseLong(wires.entrySet().stream()
                .filter(e -> e.getKey().charAt(0) == 'z')
                .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                .map(e -> e.getValue() ? "1" : "0")
                .collect(Collectors.joining()), 2));
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