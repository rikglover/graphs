package com.rikglover.graphs;

import com.rikglover.graphs.Graph.Edge;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Experiment {

  public static void main(final String[] args) {
    final Graph<Character> graph =
        Graph.builder('A', 'B', 'C', 'D', 'D', 'E', 'F', 'G')
            .edge('A', 'B')
            .edge('B', 'A')
            .edge('A', 'C')
            .edge('C', 'A')
            .edge('A', 'E')
            .edge('E', 'A')
            .edge('B', 'D')
            .edge('D', 'B')
            .edge('B', 'F')
            .edge('F', 'B')
            .edge('C', 'G')
            .edge('G', 'C')
            .edge('E', 'F')
            .edge('F', 'E')
            .build();

    new Experiment().recursiveDfs(graph, 'A');
    System.out.println();
    new Experiment().iterativeDfs(graph, 'A');
    System.out.println();
    new Experiment().iterativeDfs2(graph, 'A');
  }

  private void recursiveDfs(final Graph<Character> graph, final char startVertex) {
    final Set<Character> visited = new HashSet<>();

    recursiveDfs(graph, visited, startVertex);
  }

  private void recursiveDfs(
      final Graph<Character> graph, final Set<Character> visited, final char current) {
    visited.add(current);
    System.out.print(current + " ");

    for (final Edge<Character> edge : graph.getEdges(current)) {
      final char neighbor = edge.getDestination();

      if (!visited.contains(neighbor)) {
        recursiveDfs(graph, visited, neighbor);
      }
    }
  }

  private void iterativeDfs(final Graph<Character> graph, final char startVertex) {
    final Set<Character> visited = new HashSet<>();
    final Map<Character, Iterator<Edge<Character>>> iteratorMap = new HashMap<>();
    final Deque<Character> stack = new ArrayDeque<>();

    stack.push(startVertex);
    iteratorMap.put(startVertex, graph.edgeIterator(startVertex));
    visited.add(startVertex);
    System.out.print(startVertex + " ");

    while (!stack.isEmpty()) {
      final char current = stack.peek();
      final Iterator<Edge<Character>> currentIterator = iteratorMap.get(current);

      if (currentIterator.hasNext()) {
        final char neighbor = currentIterator.next().getDestination();

        if (!visited.contains(neighbor)) {
          stack.push(neighbor);
          iteratorMap.put(neighbor, graph.edgeIterator(neighbor));
          visited.add(neighbor);
          System.out.print(neighbor + " ");
        }
      } else {
        stack.pop();
      }
    }
  }

  private void iterativeDfs2(final Graph<Character> graph, final char startVertex) {
    final Deque<Character> stack = new ArrayDeque<>();
    final Set<Character> visited = new HashSet<>();

    stack.push(startVertex);

    while (!stack.isEmpty()) {
      final char current = stack.pop();

      if (!visited.contains(current)) {
        visited.add(current);
        System.out.print(current + " ");

        for (final Edge<Character> edge : graph.getEdges(current)) {
          final char neighbor = edge.getDestination();

          stack.push(neighbor);
        }
      }
    }
  }
}
