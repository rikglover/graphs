package com.rikglover.graphs;

import com.rikglover.graphs.Graph.Edge;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class RouteDetector<T> {
  private final Graph<T> graph;
  private final Set<T> visited = new HashSet<>();

  public boolean hasRoute(final T v1, final T v2) {
    final Queue<T> queue = new LinkedList<>();

    queue.offer(v1);
    visited.add(v1);

    while(!queue.isEmpty()) {
      final T current = queue.remove();

      if(current.equals(v2)) {
        return true;
      }

      for(final Edge<T> edge : graph.getEdges(current)) {
        final T neighbor = edge.getDestination();

        if(!visited.contains(neighbor)) {
          visited.add(neighbor);
          queue.offer(neighbor);
        }
      }
    }

    return false;
  }
}
