package com.rikglover.graphs;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MatrixPainter {
  private final int[][] image;

  private final int startRow;
  private final int startColumn;
  private final int newColor;

  private MatrixPainter(int[][] image, int row, int col, int newColor) {
    this.image = image;
    this.startRow = row;
    this.startColumn = col;
    this.newColor = newColor;
  }

  public static MatrixPainter of(int[][] image, int row, int col, int newColor) {
    return new MatrixPainter(image, row, col, newColor);
  }

  private List<List<Integer>> getPossibleNextPoints(final int row, final int col) {
    return Stream.of(
        List.of(row, col + 1),
        List.of(row + 1, col),
        List.of(row, col - 1),
        List.of(row - 1, col))
        .filter(point -> point.get(0) >= 0 && point.get(0) < image.length)
        .filter(point -> point.get(1) >= 0 && point.get(1) < image[0].length)
        .collect(Collectors.toList());
  }

  public int[][] getPaintedMatrix() {
    paintMatrix(startRow, startColumn);

    return image;
  }

  private void paintMatrix(final int row, final int column) {
    image[row][column] = newColor;

    for (final List<Integer> point : getPossibleNextPoints(row, column)) {
      if (image[point.get(0)][point.get(1)] != newColor) {
        paintMatrix(point.get(0), point.get(1));
      }
    }
  }
}
