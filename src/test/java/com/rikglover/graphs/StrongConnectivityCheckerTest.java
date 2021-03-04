package com.rikglover.graphs;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@Tag("fast")
class StrongConnectivityCheckerTest {

  private static Stream<Arguments> getStronglyConnectedTestArguments() {
    final Graph<Integer> weak1 = Graph.builder(0, 1).edge(0, 1).build();

    final Graph<Integer> weak2 =
        Graph.builder(0, 1, 2, 3, 4).edge(0, 1).edge(1, 3).edge(0, 2).edge(2, 3).edge(3, 4).build();

    final Graph<Character> weak3 =
        Graph.builder('A', 'B', 'C').edge('A', 'B').edge('C', 'B').build();

    final Graph<Integer> strong1 =
        Graph.builder(0, 1, 2, 3).edge(0, 1).edge(1, 2).edge(2, 3).edge(3, 0).build();

    final Graph<Integer> strong2 =
        Graph.builder(0, 1, 2, 3, 4)
            .edge(0, 1)
            .edge(1, 2)
            .edge(2, 4)
            .edge(2, 3)
            .edge(3, 0)
            .edge(4, 2)
            .build();

    final Graph<?> strong3 = Graph.builder().build();
    final Graph<Integer> strong4 = Graph.builder(1).build();
    final Graph<Integer> strong5 = Graph.builder(1).edge(1, 1).build();

    final Graph<Integer> disconnected1 = Graph.builder(1, 2).build();
    final Graph<Integer> disconnected2 = Graph.builder(1, 2, 3).edge(1, 2).build();

    return Stream.of(
        Arguments.of(weak1, false),
        Arguments.of(weak2, false),
        Arguments.of(weak3, false),
        Arguments.of(strong1, true),
        Arguments.of(strong2, true),
        Arguments.of(strong3, true),
        Arguments.of(strong4, true),
        Arguments.of(strong5, true),
        Arguments.of(disconnected1, false),
        Arguments.of(disconnected2, false));
  }

  @ParameterizedTest
  @MethodSource("getStronglyConnectedTestArguments")
  void whenIsStronglyConnectedIsCalled_theCorrectResultIsReturned(
      final Graph<Integer> graph, final boolean expectedResult) {

    assertThat(StrongConnectivityChecker.<Integer>of().isStronglyConnected(graph))
        .isEqualTo(expectedResult);
  }
}
