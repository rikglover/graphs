package com.rikglover.graphs;

import com.rikglover.graphs.Graph.Edge;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class BipartiteChecker<T> {
  private final Graph<T> graph;

  public boolean isBipartite() {
    final Map<T, VertexColor> colorMap = new HashMap<>();

    for (final T vertex : graph.getVertices()) {
      if (!colorMap.containsKey(vertex) && !isBipartite(colorMap, vertex)) {
        return false;
      }
    }

    return true;
  }

  private boolean isBipartite(final Map<T, VertexColor> colorMap, final T start) {
    final Queue<T> queue = new LinkedList<>();

    queue.offer(start);
    colorMap.put(start, VertexColor.BLACK);

    while (!queue.isEmpty()) {
      final T current = queue.remove();
      final VertexColor currentColor = colorMap.get(current);

      for (final Edge<T> edge : graph.getEdges(current)) {
        final T neighbor = edge.getDestination();
        final VertexColor nextColor =
            currentColor == VertexColor.BLACK ? VertexColor.WHITE : VertexColor.BLACK;

        if (!colorMap.containsKey(neighbor)) {
          colorMap.put(neighbor, nextColor);
          queue.offer(neighbor);
        } else if (colorMap.get(neighbor) == currentColor) {
          return false;
        }
      }
    }

    return true;
  }

  private enum VertexColor {
    BLACK,
    WHITE
  }
}
