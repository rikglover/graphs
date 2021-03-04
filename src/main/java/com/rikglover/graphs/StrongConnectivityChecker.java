package com.rikglover.graphs;

import com.rikglover.graphs.Graph.Edge;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class StrongConnectivityChecker<T> {
  public boolean isStronglyConnected(final Graph<T> graph) {
    if (graph.getVertices().isEmpty()) {
      return true;
    }

    final T startNode = graph.getVertices().iterator().next();
    final Graph<T> reverseGraph = getReverseGraph(graph);

    return isConnected(graph, startNode) && isConnected(reverseGraph, startNode);
  }

  private boolean isConnected(final Graph<T> graph, final T startNode) {
    final Queue<T> queue = new LinkedList<>();
    final Set<T> visited = new HashSet<>();

    queue.offer(startNode);
    visited.add(startNode);

    while (!queue.isEmpty()) {
      final T current = queue.remove();

      for (final Edge<T> edge : graph.getEdges(current)) {
        final T neighbor = edge.getDestination();

        if (!visited.contains(neighbor)) {
          queue.offer(neighbor);
          visited.add(neighbor);
        }
      }
    }

    return visited.size() == graph.getVertices().size();
  }

  private Graph<T> getReverseGraph(final Graph<T> graph) {
    final Graph<T> reverseGraph = new Graph<>();

    graph.getVertices().forEach(reverseGraph::addVertex);

    for (final T source : graph.getVertices()) {
      for (final Edge<T> edge : graph.getEdges(source)) {
        reverseGraph.addEdge(Edge.reverse(edge));
      }
    }

    return reverseGraph;
  }
}
