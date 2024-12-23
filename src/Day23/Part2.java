package Day23;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Part2 {

    public static void main(String[] args) throws IOException {
        Map<String, Set<String>> mapping = new HashMap<>();
        Files.lines(Path.of("src/Day23/input.txt")).forEach(e -> {
            mapping.computeIfAbsent(e.substring(0, 2), k -> new HashSet<>()).add(e.substring(3));
            mapping.computeIfAbsent(e.substring(3), k -> new HashSet<>()).add(e.substring(0, 2));
        });
        Set<String> bestGroup = findBestGroup(mapping, Set.of(), mapping.keySet(), Set.of(), 0);
        System.out.println(bestGroup.stream().sorted().collect(Collectors.joining(",")));
    }

    private static Set<String> findBestGroup(Map<String, Set<String>> mapping, Set<String> group, Set<String> candidateSet, Set<String> visitedSet, int bestGroupSize) {
        if (group.size() + candidateSet.size() <= bestGroupSize) {
            return group;
        }
        if (candidateSet.isEmpty()) {
            return group;
        }

        Set<String> bestGroup = group;
        Set<String> newVisitedSet = new HashSet<>(visitedSet);
        for (String c : candidateSet) {
            if (!newVisitedSet.add(c)) {
                continue;
            }
            Set<String> linkSet = mapping.get(c);
            if (!linkSet.containsAll(group)) {
                continue;
            }

            Set<String> newGroup = new HashSet<>(group);
            newGroup.add(c);

            Set<String> newCandidateSet = new HashSet<>(candidateSet);
            newCandidateSet.remove(c);
            newCandidateSet.retainAll(linkSet);

            Set<String> tmpGroup = findBestGroup(mapping, newGroup, newCandidateSet, newVisitedSet, Math.max(bestGroup.size(), bestGroupSize));
            if (tmpGroup.size() > bestGroup.size()) {
                bestGroup = tmpGroup;
            }
        }
        return bestGroup;
    }

}