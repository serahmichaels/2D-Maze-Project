package com.javamaze.maze;

import java.util.Random;

public class Maze {
    //Group 5: Written by Riley Ringer
    private char[][] grid;
    private int rows, cols;
    private Point start, end;

    public Maze(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new char[rows][cols];
        generateMaze();
    }

    public void regenerateMaze() {
        generateMaze();
    }

    private void generateMaze() {
        // Clear the existing com.javamaze.yes.com.javamaze.maze.gui.maze
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = '#';
            }
        }

        // Define new random start and end points
        Random random = new Random();
        int startRow, startCol, endRow, endCol;
        do {
            startRow = 1 + random.nextInt(rows - 2);
            startCol = 1 + random.nextInt(cols - 2);
            endRow = 1 + random.nextInt(rows - 2);
            endCol = 1 + random.nextInt(cols - 2);
        } while (startRow == endRow && startCol == endCol);

        start = new Point(startRow, startCol);
        end = new Point(endRow, endCol);

        // Ensure there's always a path from start to end
        dfs(start.getRow(), start.getCol());
        carvePathToEnd(end.getRow(), end.getCol());

        grid[start.getRow()][start.getCol()] = 'S';
        grid[end.getRow()][end.getCol()] = 'E';
    }

    private void dfs(int row, int col) {
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        Random random = new Random();
        shuffleArray(directions, random);

        for (int[] direction : directions) {
            int newRow = row + direction[0] * 2;
            int newCol = col + direction[1] * 2;
            if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && grid[newRow][newCol] == '#') {
                grid[row + direction[0]][col + direction[1]] = ' ';
                grid[newRow][newCol] = ' ';
                dfs(newRow, newCol);
            }
        }
    }

    // Helper method to shuffle the directions array
    private void shuffleArray(int[][] a, Random random) {
        for (int i = a.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int[] temp = a[index];
            a[index] = a[i];
            a[i] = temp;
        }
    }

    private void carvePathToEnd(int endRow, int endCol) {
        Point current = new Point(start.getRow(), start.getCol());
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        Random random = new Random();

        while (!current.equals(end)) {
            int dx = endCol - current.getCol();
            int dy = endRow - current.getRow();

            // Choose the direction that brings us closer to the end
            int[] chosenDirection = null;
            if (dx != 0 && Math.abs(dx) > Math.abs(dy)) {
                chosenDirection = dx > 0 ? directions[0] : directions[2]; // right or left
            } else if (dy != 0) {
                chosenDirection = dy > 0 ? directions[1] : directions[3]; // down or up
            }

            if (chosenDirection != null) {
                int newRow = current.getRow() + chosenDirection[0] * 2;
                int newCol = current.getCol() + chosenDirection[1] * 2;
                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
                    grid[newRow - chosenDirection[0]][newCol - chosenDirection[1]] = ' ';
                    grid[newRow][newCol] = ' ';
                    current = new Point(newRow, newCol);
                }
            } else {
                // If we can't move directly towards the end, try a random direction
                do {
                    chosenDirection = directions[random.nextInt(directions.length)];
                    int newRow = current.getRow() + chosenDirection[0] * 2;
                    int newCol = current.getCol() + chosenDirection[1] * 2;
                    if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && grid[newRow][newCol] == '#') {
                        grid[newRow - chosenDirection[0]][newCol - chosenDirection[1]] = ' ';
                        grid[newRow][newCol] = ' ';
                        current = new Point(newRow, newCol);
                        break;
                    }
                } while (true);
            }
        }
    }

    public boolean canMove(Point pos) {
        return pos.getRow() >= 0 && pos.getRow() < rows && pos.getCol() >= 0 && pos.getCol() < cols && grid[pos.getRow()][pos.getCol()] != '#';
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public char[][] getGrid() {
        return grid;
    }

    public static class Point {
        public int row, col;

        public Point(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }

        @Override
        public String toString() {
            return "(" + row + ", " + col + ")";
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Point point = (Point) obj;
            return row == point.row && col == point.col;
        }

        @Override
        public int hashCode() {
            return 31 * row + col;
        }
    }
}