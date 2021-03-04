package com.rikglover.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

public class Graph<T> {

  private final Map<T, Map<T, Edge<T>>> graphMap = new HashMap<>();

  public static <T> GraphBuilder<T> builder(final T... vertices) {
    return GraphBuilder.builder(vertices);
  }

  public Set<T> getVertices() {
    return graphMap.keySet();
  }

  public void addVertex(final T vertex) {
    graphMap.put(vertex, new LinkedHashMap<>());
  }

  public Collection<Edge<T>> getEdges(final T source) {
    assertVerticesExists(source);

    return graphMap.get(source).values();
  }

  public Iterator<Edge<T>> edgeIterator(final T source) {
    return graphMap.get(source).values().iterator();
  }

  public Edge<T> getEdge(final T source, final T destination) {
    assertVerticesExists(source, destination);

    return graphMap.get(source).get(destination);
  }

  public void addEdge(final Edge<T> edge) {
    final T source = edge.getSource();
    final T dest = edge.getDestination();
    final Edge<T> existingEdge = getEdge(source, dest);

    if (existingEdge != null) {
      throw new IllegalArgumentException("Edge already exists in graph");
    }

    graphMap.get(source).put(dest, edge);
  }

  @SafeVarargs
  private void assertVerticesExists(final T... vertices) {
    Arrays.stream(vertices)
        .forEachOrdered(
            vertex -> {
              if (!graphMap.containsKey(vertex)) {
                throw new NoSuchElementException("vertex " + vertex + " not found in graph");
              }
            });
  }

  @Getter
  @EqualsAndHashCode(onlyExplicitlyIncluded = true)
  @RequiredArgsConstructor(staticName = "of")
  public static class Edge<T> {
    @EqualsAndHashCode.Include private final T source;
    @EqualsAndHashCode.Include private final T destination;
    private final double weight;

    public static <T> Edge<T> of(final T source, final T destination) {
      return Edge.of(source, destination, 1.0);
    }

    public static <T> Edge<T> reverse(final Edge<T> edge) {
      return Edge.of(edge.destination, edge.source, edge.weight);
    }
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class GraphBuilder<T> {
    private final List<T> vertices = new ArrayList<>();
    private final List<Edge<T>> edges = new ArrayList<>();

    @SafeVarargs
    public static <T> GraphBuilder<T> builder(T... vertices) {
      final GraphBuilder<T> graphBuilder = new GraphBuilder<>();

      return graphBuilder.vertices(vertices);
    }

    @SafeVarargs
    public final GraphBuilder<T> vertices(T... vertices) {
      this.vertices.addAll(Arrays.asList(vertices));

      return this;
    }

    public GraphBuilder<T> edge(final T source, final T dest, final double weight) {
      edges.add(Edge.of(source, dest, weight));

      return this;
    }

    public GraphBuilder<T> edge(final T source, final T dest) {
      edges.add(Edge.of(source, dest));

      return this;
    }

    public Graph<T> build() {
      final Graph<T> graph = new Graph<>();

      vertices.forEach(graph::addVertex);
      edges.forEach(graph::addEdge);

      return graph;
    }
  }
}
