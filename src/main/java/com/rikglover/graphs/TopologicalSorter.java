package com.rikglover.graphs;

import com.rikglover.graphs.Graph.Edge;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class TopologicalSorter<T> {
  private final Graph<T> graph;

  private final Map<T, VertexLabel> labelMap;
  private final List<T> finishList = new LinkedList<>();

  public static <T> TopologicalSorter<T> of(final Graph<T> graph) {
    return new TopologicalSorter<>(graph, getLabelMap(graph));
  }

  private static <T> Map<T, VertexLabel> getLabelMap(final Graph<T> graph) {
    return graph.getVertices().stream()
        .collect(Collectors.toMap(v -> v, v -> VertexLabel.UNVISITED));
  }

  public List<T> sort() {
    for (final T start : graph.getVertices()) {
      if (labelMap.get(start) == VertexLabel.UNVISITED && dfsSort(start)) {
        return Collections.emptyList();
      }
    }

    return finishList;
  }

  private boolean dfsSort(final T current) {
    if (labelMap.get(current) == VertexLabel.VISITING) {
      return true;
    }

    labelMap.put(current, VertexLabel.VISITING);

    for (final Edge<T> edge : graph.getEdges(current)) {
      final T neighbor = edge.getDestination();

      if (labelMap.get(neighbor) != VertexLabel.VISITED && dfsSort(neighbor)) {
        return true;
      }
    }

    labelMap.put(current, VertexLabel.VISITED);
    finishList.add(0, current);

    return false;
  }

  private enum VertexLabel {
    UNVISITED,
    VISITING,
    VISITED
  }
}
