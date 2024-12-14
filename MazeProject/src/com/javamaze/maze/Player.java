package com.javamaze.maze;

import java.util.ArrayList;
import java.util.List;

public class Player {
    //Group 5: Written by Serah Michaels
    private Maze.Point position;
    private Maze maze;
    private List<Maze.Point> path;

    public Player(Maze maze) {
        this.maze = maze;
        this.position = maze.getStart();
        this.path = new ArrayList<>();
        path.add(position);
    }

    public void reset() {
        this.position = maze.getStart();
        this.path.clear();
        path.add(position);
    }

    public boolean move(char direction) {
        Maze.Point newPosition = new Maze.Point(position.getRow(), position.getCol());
        switch (direction) {
            case 'U': newPosition.row--; break;
            case 'D': newPosition.row++; break;
            case 'L': newPosition.col--; break;
            case 'R': newPosition.col++; break;
            default: return false;
        }
        if (maze.canMove(newPosition)) {
            position = newPosition;
            path.add(position);
            return true;
        }
        return false;
    }

    public Maze.Point getPosition() {
        return new Maze.Point(position.getRow(), position.getCol());
    }

    public List<Maze.Point> getPath() {
        return path;
    }

    public boolean hasReachedEnd() {
        return position.equals(maze.getEnd());
    }
}