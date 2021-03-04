package com.rikglover.graphs;

import com.rikglover.graphs.Graph.Edge;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class WeakConnectivityChecker<T> {

  public boolean isWeaklyConnected(final Graph<T> graph) {
    final Graph<T> equivalentUndirectedGraph = getEquivalentUndirectedGraph(graph);

    return isConnected(equivalentUndirectedGraph);
  }

  private boolean isConnected(final Graph<T> graph) {
    if (graph.getVertices().isEmpty()) {
      return true;
    }

    final Queue<T> queue = new LinkedList<>();
    final Set<T> visited = new HashSet<>();
    final T startVertex = graph.getVertices().iterator().next();

    visited.add(startVertex);
    queue.offer(startVertex);

    while (!queue.isEmpty()) {
      final T current = queue.remove();

      for (final Edge<T> edge : graph.getEdges(current)) {
        final T neighbor = edge.getDestination();

        if (!visited.contains(neighbor)) {
          visited.add(neighbor);
          queue.offer(neighbor);
        }
      }
    }

    return visited.size() == graph.getVertices().size();
  }

  private Graph<T> getEquivalentUndirectedGraph(final Graph<T> graph) {
    final Graph<T> equivalent = new Graph<>();

    graph.getVertices().forEach(equivalent::addVertex);

    for (final T source : graph.getVertices()) {
      for (final Edge<T> edge : graph.getEdges(source)) {
        final T destination = edge.getDestination();

        if (equivalent.getEdge(source, destination) == null) {
          equivalent.addEdge(edge);
        }

        if (equivalent.getEdge(destination, source) == null) {
          equivalent.addEdge(Edge.reverse(edge));
        }
      }
    }

    return equivalent;
  }
}
