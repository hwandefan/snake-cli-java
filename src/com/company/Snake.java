package com.company;

import java.util.ArrayList;
import java.util.List;

/**
 * Snake class
 */
public class Snake {
    // Snake direction
    private SnakeDirection direction;
    // life of snake
    private boolean isAlive;
    //Snake's partition
    private ArrayList<SnakeSection> sections;

    public Snake(int x, int y) {
        sections = new ArrayList<SnakeSection>();
        sections.add(new SnakeSection(x, y));
        isAlive = true;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public int getX() {
        return sections.get(0).getX();
    }

    public int getY() {
        return sections.get(0).getY();
    }

    public SnakeDirection getDirection() {
        return direction;
    }

    public void setDirection(SnakeDirection direction) {
        this.direction = direction;
    }

    public ArrayList<SnakeSection> getSections() {
        return sections;
    }

    public void move() {
        if (!isAlive) return;

        if (direction == SnakeDirection.UP)
            move(0, -1);
        else if (direction == SnakeDirection.RIGHT)
            move(1, 0);
        else if (direction == SnakeDirection.DOWN)
            move(0, 1);
        else if (direction == SnakeDirection.LEFT)
            move(-1, 0);
    }

    private void move(int dx, int dy) {
        // Create snake's head
        SnakeSection head = sections.get(0);
        head = new SnakeSection(head.getX() + dx, head.getY() + dy);

        // Check borders
        checkBorders(head);
        if (!isAlive) return;

        // Check Body
        checkBody(head);
        if (!isAlive) return;

        // Check eat mouse
        Mouse mouse = Room.game.getMouse();
        if (head.getX() == mouse.getX() && head.getY() == mouse.getY()) // mouse was eatten
        {
            sections.add(0, head);                  // Add new head
            Room.game.eatMouse();                   // Create new mouse
        } else // Just mvoe
        {
            sections.add(0, head);                  // Add new head
            sections.remove(sections.size() - 1);   // Delete last part of snake
        }
    }

    /**
     * Head in borders of room
     */
    private void checkBorders(SnakeSection head) {
        if ((head.getX() < 0 || head.getX() >= Room.game.getWidth()) || head.getY() < 0 || head.getY() >= Room.game.getHeight()) {
            isAlive = false;
        }
    }

    /**
     * Check head in body
     */
    private void checkBody(SnakeSection head) {
        if (sections.contains(head)) {
            isAlive = false;
        }
    }
}
