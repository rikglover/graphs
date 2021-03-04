package com.rikglover.graphs;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("fast")
class MatrixPainterTest {

  @Test
  void whenMatrixPainterGetPaintedMatrixIsCalled_thePaintedMatrixIsReturned() {
    final int[][] matrix = { { 0, 0, 1, 0 }, { 0, 0, 1, 0}, { 0, 0, 1, 0 }, { 0, 0, 1, 0 } };
    final int[][] paintedMatrix = { { 1, 1, 1, 0 }, { 1, 1, 1, 0}, { 1, 1, 1, 0 }, { 1, 1, 1, 0 } };

    assertThat(MatrixPainter.of(matrix, 0, 0, 1).getPaintedMatrix()).isEqualTo(paintedMatrix);
  }
}