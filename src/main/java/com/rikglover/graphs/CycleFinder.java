package com.rikglover.graphs;

import com.rikglover.graphs.Graph.Edge;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CycleFinder<T> {
  private final Graph<T> graph;
  private final Map<T, VertexLabel> labelMap;

  public static <T> CycleFinder<T> of(final Graph<T> graph) {
    final Map<T, VertexLabel> map =
        graph.getVertices().stream().collect(Collectors.toMap(v -> v, v -> VertexLabel.UNVISITED));

    return new CycleFinder<>(graph, map);
  }

  public boolean hasCycle() {
    return hasCycle(graph.getVertices());
  }

  public boolean hasCycle(final Collection<T> order) {
    for (final T start : order) {
      if ((labelMap.get(start) == VertexLabel.UNVISITED) && hasCycle(start)) {
        return true;
      }
    }

    return false;
  }

  private boolean hasCycle(final T current) {
    if (labelMap.get(current) != VertexLabel.UNVISITED) {
      return true;
    }

    labelMap.put(current, VertexLabel.VISITING);

    for (final Edge<T> edge : graph.getEdges(current)) {
      final T dest = edge.getDestination();

      if (labelMap.get(dest) != VertexLabel.VISITED && hasCycle(dest)) {
        return true;
      }
    }

    labelMap.put(current, VertexLabel.VISITED);

    return false;
  }

  private enum VertexLabel {
    UNVISITED,
    VISITING,
    VISITED
  }
}
