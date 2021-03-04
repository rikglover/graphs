package com.rikglover.graphs;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class LeetCodeMazeSolver {
  private final int[][] maze;

  public boolean hasPath(final int[] start, final int[] end) {
    final Set<MazeCell> visited = new HashSet<>();
    final MazeCell startCell = MazeCell.of(start[0], start[1]);
    final MazeCell endCell = MazeCell.of(end[0], end[1]);

    return hasPath(startCell, endCell, visited);
  }

  private boolean hasPath(
      final MazeCell currentCell, final MazeCell endCell, final Set<MazeCell> visited) {
    if (currentCell.equals(endCell)) {
      return true;
    }

    visited.add(currentCell);

    for (final MazeCell nextMazeCell : getPossibleNextMazeCells(currentCell)) {
      if (!visited.contains(nextMazeCell) && hasPath(nextMazeCell, endCell, visited)) {
        return true;
      }
    }

    return false;
  }

  private List<MazeCell> getPossibleNextMazeCells(final MazeCell currentMazeCell) {
    final MazeCell leftChoice = getLeftChoice(currentMazeCell);
    final MazeCell rightChoice = getRightChoice(currentMazeCell);
    final MazeCell topChoice = getTopChoice(currentMazeCell);
    final MazeCell bottomChoice = getBottomChoice(currentMazeCell);

    return List.of(leftChoice, rightChoice, topChoice, bottomChoice);
  }

  private MazeCell getTopChoice(final MazeCell currentMazeCell) {
    final int column = currentMazeCell.getColumn();
    int row = currentMazeCell.getRow();

    while (row - 1 >= 0 && maze[row - 1][column] == 0) {
      row -= 1;
    }

    return MazeCell.of(row, column);
  }

  private MazeCell getBottomChoice(final MazeCell currentMazeCell) {
    final int column = currentMazeCell.getColumn();
    int row = currentMazeCell.getRow();

    while (row + 1 < maze.length && maze[row + 1][column] == 0) {
      row += 1;
    }

    return MazeCell.of(row, column);
  }

  private MazeCell getLeftChoice(final MazeCell currentMazeCell) {
    final int row = currentMazeCell.getRow();
    int column = currentMazeCell.getColumn();

    while (column - 1 >= 0 && maze[row][column - 1] == 0) {
      column -= 1;
    }

    return MazeCell.of(row, column);
  }

  private MazeCell getRightChoice(final MazeCell currentMazeCell) {
    final int row = currentMazeCell.getRow();
    int column = currentMazeCell.getColumn();

    while (column + 1 < maze[0].length && maze[row][column + 1] == 0) {
      column += 1;
    }

    return MazeCell.of(row, column);
  }

  @Getter
  @EqualsAndHashCode
  @RequiredArgsConstructor(staticName = "of")
  private static class MazeCell {
    private final int row;
    private final int column;
  }
}
