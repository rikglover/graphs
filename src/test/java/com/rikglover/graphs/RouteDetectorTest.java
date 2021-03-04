package com.rikglover.graphs;

import java.util.stream.Stream;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@Tag("fast")
class RouteDetectorTest {
  private static Stream<Arguments> getRouteArguments() {
    final Graph singleNodeWithCycle = Graph.builder(0).edge(0, 0).build();

    final Graph linearLinkedListGraph =
        Graph.builder(0, 1, 2, 3).edge(0, 1).edge(1, 2).edge(2, 3).build();

    final Graph graphWithNoEdges = Graph.builder(0).build();

    final Graph twoNodesNoEdges = Graph.builder(0, 1).build();

    final Graph graphWithCycle =
        Graph.builder(0, 1, 2, 3, 4).edge(0, 1).edge(1, 2).edge(2, 3).edge(3, 1).edge(3, 4).build();

    final Graph twoComponentGraph =
        Graph.builder(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            .edge(0, 1)
            .edge(0, 3)
            .edge(0, 4)
            .edge(1, 3)
            .edge(1, 4)
            .edge(3, 4)
            .edge(0, 2)
            .edge(2, 5)
            .edge(2, 6)
            .edge(5, 6)
            .edge(7, 8)
            .edge(8, 9)
            .edge(9, 7)
            .build();

    return Stream.<Arguments>builder()
        .add(Arguments.of(singleNodeWithCycle, 0, 0, true))
        .add(Arguments.of(linearLinkedListGraph, 0, 3, true))
        .add(Arguments.of(linearLinkedListGraph, 3, 0, false))
        .add(Arguments.of(graphWithNoEdges, 0, 0, true))
        .add(Arguments.of(twoNodesNoEdges, 0, 1, false))
        .add(Arguments.of(graphWithCycle, 0, 4, true))
        .add(Arguments.of(twoComponentGraph, 0, 5, true))
        .add(Arguments.of(twoComponentGraph, 0, 7, false))
        .add(Arguments.of(twoComponentGraph, 7, 9, true))
        .build();
  }

  @ParameterizedTest
  @MethodSource("getRouteArguments")
  void givenAGraph_routeExistsReturnsCorrectAnswer(
      final Graph<Integer> graph, final int source, final int dest, final boolean expected) {
    final boolean routeExists = RouteDetector.of(graph).hasRoute(source, dest);

    AssertionsForClassTypes.assertThat(routeExists).isEqualTo(expected);
  }
}
