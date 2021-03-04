package com.rikglover.graphs;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@Tag("fast")
class CycleFinderTest {
  private static Stream<Arguments> getGraphArguments() {
    final Graph<Integer> dcg1 = Graph.builder(1, 2, 3).edge(1, 2).edge(2, 3).edge(3, 1).build();

    final Graph<Integer> dcg2 =
        Graph.builder(1, 2, 3, 4, 5).edge(1, 2).edge(2, 3).edge(3, 4).edge(4, 2).edge(4, 5).build();

    final Graph<Integer> dcg3 =
        Graph.builder(1, 2, 3, 4, 5).edge(1, 2).edge(2, 3).edge(3, 4).edge(4, 3).edge(4, 5).build();

    final Graph<Integer> dcg4 =
        Graph.builder(1, 2, 3, 4).edge(1, 2).edge(2, 3).edge(3, 4).edge(4, 1).build();

    final Graph<Integer> dcg5 = Graph.builder(1).edge(1, 1).build();

    final Graph<Integer> dcg6 =
        Graph.builder(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
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

    final Graph<Integer> dag1 = Graph.builder(1).build();

    final Graph<Integer> dag2 = Graph.builder(1, 2, 3).edge(1, 2).edge(2, 3).build();

    final Graph<Integer> dag3 =
        Graph.builder(0, 1, 2, 3, 4)
            .edge(0, 1)
            .edge(0, 4)
            .edge(1, 2)
            .edge(1, 3)
            .edge(1, 4)
            .edge(3, 4)
            .edge(2, 3)
            .build();

    final Graph<Integer> dag4 =
        Graph.<Integer>builder()
            .vertices(0, 1, 2, 3, 4)
            .edge(0, 1)
            .edge(0, 4)
            .edge(1, 2)
            .edge(1, 3)
            .edge(1, 4)
            .edge(3, 4)
            .edge(2, 3)
            .build();

    final Graph<Character> dag5 =
        Graph.<Character>builder()
            .vertices('a', 'b', 'c', 'd', 'e', 'f')
            .edge('a', 'd')
            .edge('f', 'b')
            .edge('b', 'd')
            .edge('f', 'a')
            .edge('d', 'c')
            .build();

    return Stream.<Arguments>builder()
        .add(Arguments.of(dcg1, true))
        .add(Arguments.of(dcg2, true))
        .add(Arguments.of(dcg3, true))
        .add(Arguments.of(dcg4, true))
        .add(Arguments.of(dcg5, true))
        .add(Arguments.of(dcg6, true))
        .add(Arguments.of(dag1, false))
        .add(Arguments.of(dag2, false))
        .add(Arguments.of(dag3, false))
        .add(Arguments.of(dag4, false))
        .add(Arguments.of(dag5, false))
        .build();
  }

  @ParameterizedTest
  @MethodSource("getGraphArguments")
  void whenHasCyclesIsCalledOnGraph_methodReturnsCorrectValue(
      final Graph<Object> graph, final boolean hasCycle) {
    assertThat(CycleFinder.of(graph).hasCycle()).isEqualTo(hasCycle);
  }
}
