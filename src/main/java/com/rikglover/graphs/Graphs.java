package com.rikglover.graphs;

import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Graphs {

  public static <T> boolean hasCycle(final Graph<T> graph) {
    return CycleFinder.of(graph).hasCycle();
  }

  public static <T> boolean hasRoute(final Graph<T> graph, final T source, final T destination) {
    return RouteDetector.of(graph).hasRoute(source, destination);
  }

  public static <T> List<T> topologicalSort(final Graph<T> graph) {
    return TopologicalSorter.of(graph).sort();
  }
}
