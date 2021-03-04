package com.rikglover.graphs;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("fast")
class TopologicalSorterTest {

  @Test
  void whenTopologicalSorterGetSortedVerticesIsCalled_theCorrectTopologicalSortIsReturned() {
    final Graph<Integer> graph =
        Graph.builder(0, 1, 2, 3, 4, 5, 6, 7, 8)
            .edge(0, 1)
            .edge(0, 3)
            .edge(0, 4)
            .edge(1, 2)
            .edge(1, 4)
            .edge(1, 5)
            .edge(2, 5)
            .edge(3, 6)
            .edge(4, 6)
            .edge(4, 7)
            .edge(5, 7)
            .edge(6, 8)
            .edge(7, 8)
            .build();

    assertThat(TopologicalSorter.of(graph).sort()).containsExactly(0, 3, 1, 4, 6, 2, 5, 7, 8);
  }

  @Test
  void whenGraphsTopologicalSortIsCalledWithNoCycles_aValidOrderingIsReturned() {
    final Graph<Character> graph =
        Graph.builder('a', 'b', 'c', 'd', 'e', 'f')
            .edge('a', 'd')
            .edge('f', 'b')
            .edge('b', 'd')
            .edge('f', 'a')
            .edge('d', 'c')
            .build();

    final List<Character> order = Graphs.topologicalSort(graph);

    assertThat(order.indexOf('a')).isLessThan(order.indexOf('d'));
    assertThat(order.indexOf('f')).isLessThan(order.indexOf('b'));
    assertThat(order.indexOf('b')).isLessThan(order.indexOf('d'));
    assertThat(order.indexOf('f')).isLessThan(order.indexOf('a'));
    assertThat(order.indexOf('d')).isLessThan(order.indexOf('c'));
  }

  @Test
  void whenGraphsTopologicalSortIsCalledWithACyle_anEmptyListIsReturned() {
    final Graph<Character> graph =
        Graph.builder('a', 'b', 'c').edge('a', 'b').edge('b', 'c').edge('c', 'a').build();

    assertThat(Graphs.topologicalSort(graph)).isEmpty();
  }
}
