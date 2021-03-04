package com.rikglover.graphs;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@Tag("fast")
class BipartiteCheckerTest {

  private static Stream<Arguments> getIsBipartiteArguments() {
    final Graph<Integer> bipartite1 =
        Graph.builder(0, 1, 2, 3, 4).edge(0, 3).edge(1, 3).edge(2, 4).build();

    final Graph<Character> bipartite2 =
        Graph.builder('A', 'B', 'C', 'D')
            .edge('A', 'B')
            .edge('B', 'A')
            .edge('B', 'C')
            .edge('C', 'B')
            .edge('C', 'D')
            .edge('D', 'C')
            .edge('D', 'A')
            .edge('A', 'D')
            .build();

    final Graph<Character> bipartite3 =
        Graph.builder('A', 'B', 'C', 'D')
            .edge('A', 'B')
            .edge('B', 'A')
            .edge('A', 'D')
            .edge('D', 'A')
            .edge('C', 'D')
            .edge('D', 'C')
            .edge('C', 'B')
            .edge('B', 'C')
            .build();

    final Graph<Character> bipartite4 =
        Graph.builder('A', 'B', 'C', 'D', 'P', 'Q', 'R')
            .edge('A', 'P')
            .edge('A', 'Q')
            .edge('A', 'R')
            .edge('B', 'P')
            .edge('B', 'Q')
            .edge('B', 'R')
            .edge('C', 'P')
            .edge('C', 'Q')
            .edge('C', 'R')
            .edge('D', 'P')
            .edge('D', 'Q')
            .edge('D', 'R')
            .edge('P', 'A')
            .edge('Q', 'A')
            .edge('R', 'A')
            .edge('P', 'B')
            .edge('Q', 'B')
            .edge('R', 'B')
            .edge('P', 'C')
            .edge('Q', 'C')
            .edge('R', 'C')
            .edge('P', 'D')
            .edge('Q', 'D')
            .edge('R', 'D')
            .build();

    final Graph<Integer> bipartite5 = Graph.builder(1).build();

    final Graph<?> bipartite6 = Graph.builder().build();
    final Graph<Integer> bipartite7 = Graph.builder(1, 2).build();

    final Graph<Integer> nonBipartite1 =
        Graph.builder(1, 2, 3, 4, 5)
            .edge(1, 2)
            .edge(2, 1)
            .edge(2, 3)
            .edge(3, 2)
            .edge(3, 4)
            .edge(4, 3)
            .edge(4, 5)
            .edge(5, 4)
            .edge(5, 3)
            .edge(3, 5)
            .build();

    final Graph<Integer> nonBipartite2 = Graph.builder(1).edge(1, 1).build();
    final Graph<Integer> nonBipartite3 =
        Graph.builder(1, 2, 3).edge(1, 2).edge(2, 3).edge(3, 1).build();

    return Stream.of(
        Arguments.of(bipartite1, true),
        Arguments.of(bipartite2, true),
        Arguments.of(bipartite3, true),
        Arguments.of(bipartite4, true),
        Arguments.of(bipartite5, true),
        Arguments.of(bipartite6, true),
        Arguments.of(bipartite7, true),
        Arguments.of(nonBipartite1, false),
        Arguments.of(nonBipartite2, false),
        Arguments.of(nonBipartite3, false));
  }

  @ParameterizedTest
  @MethodSource("getIsBipartiteArguments")
  void whenIsBipartiteIsCalled_theCorrectAnswerIsReturned(
      final Graph<Object> graph, final boolean expectedResult) {

    assertThat(BipartiteChecker.of(graph).isBipartite()).isEqualTo(expectedResult);
  }
}
