package com.wangpos.datastructure.leetcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * 353. 贪吃蛇
 * <p>
 * 给定 width = 3, height = 2, 食物序列为 food = [[1,2],[0,1]]。
 * <p>
 * Snake snake = new Snake(width, height, food);
 * <p>
 * 初始时，蛇的位置在 (0,0) 且第一个食物在 (1,2)。
 * <p>
 * 3,3,[[0,1],[0,2],[1,2],[2,2],[2,1],[2,0],[1,0]]],["R"],["R"],["D"],["D"],["L"],["L"],["U"],["U"],["R"],["R"],["D"],["D"],["L"],["L"],["U"],["R"],["U"],["L"],["D"]]
 */
public class SnakeGame353 {

    Deque<int[]> snake = new ArrayDeque<>();

    List<int[]> bodys = new ArrayList<>();

    Set<int[]> snakePositionSet = new HashSet<>();
    int[][] food;
    int width;
    int height;

    int newfoodIndex = 0;

    int[] lastPosition = new int[2];

    public static void main(String args[]) {
//        int[][] food = new int[][]{new int[]{1, 2}, new int[]{0, 1}};
//        int[][] emptyFood = new int[0][0];
//        SnakeGame353 mainPanel = new SnakeGame353(3, 2, emptyFood);
//        int[][] food = new int[][]{new int[]{2, 0}, new int[]{0, 0}, new int[]{0, 2}, new int[]{2, 2}};

        int[][] food = new int[][]{new int[]{0, 1}, new int[]{0, 2}, new int[]{1, 2}, new int[]{2, 2}, new int[]{2, 1}
                , new int[]{2, 0}, new int[]{1, 0}
        };
        SnakeGame353 mainPanel = new SnakeGame353(3, 3, food);

        startMove(mainPanel, "R");
        startMove(mainPanel, "R");

        startMove(mainPanel, "D");
        startMove(mainPanel, "D");
        startMove(mainPanel, "L");
        startMove(mainPanel, "L");

        startMove(mainPanel, "U");
        startMove(mainPanel, "U");
//
//
        startMove(mainPanel, "R");
        startMove(mainPanel, "R");
//
//        startMove(mainPanel, "D");
//        startMove(mainPanel, "D");
//        startMove(mainPanel, "L");
//        startMove(mainPanel, "L");
//        startMove(mainPanel, "U");
//        startMove(mainPanel, "R");
//
//        startMove(mainPanel,"U");
//        startMove(mainPanel,"L");
//        startMove(mainPanel,"D");
        mainPanel.printSnake();
    }

    private static void startMove(SnakeGame353 leetCode353, String command) {
        System.out.println("结果：" + leetCode353.move(command));
    }

    public SnakeGame353(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        this.food = food;

        int[] startPosition = getNewPosition(0, 0);
//        snake.add(startPosition);
    }

    private int[] getNewPosition(int x, int y) {
        int[] position = new int[2];
        position[0] = x;
        position[1] = y;
        return position;
    }

    /**
     * Moves the snake.
     *
     * @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down
     * @return The game's score after the move. Return -1 if game over.
     * Game over when snake crosses the screen boundary or bites its body.
     */
    public int move(String direction) {
        switch (direction) {
            case "U":
                if (moveTop(lastPosition)) return -1;
                break;
            case "L":
                if (moveLeft(lastPosition)) return -1;
                break;
            case "R":
                if (moveRight(lastPosition)) return -1;
                break;
            case "D":
                if (moveDown(lastPosition)) return -1;
                break;
        }

        return bodys.size();
    }

    private boolean moveDown(int[] lastPosition) {
        int x = lastPosition[0];
        int y = lastPosition[1] + 1;
        if (y < height) {
            return move(x, y);
        } else {
            return true;
        }
    }

    private boolean checkCrashBody(int x, int y) {
        //遍历蛇判断,可以通过HashSet保存蛇位置，然后进行O(1)判断
        for (int[] item : snake) {
            if (item[0] == x && item[1] == y) {
//                System.out.println("撞到自己身体 Game Over!");
//                System.exit(0);
                return true;
            }
        }
        return false;
    }


    private boolean moveRight(int[] lastPosition) {
        int x = lastPosition[0] + 1;
        int y = lastPosition[1];
        if (x < width) {
            return move(x, y);
        } else {
            return true;
        }
    }

    private boolean moveLeft(int[] lastPosition) {
        int x = lastPosition[0] - 1;
        int y = lastPosition[1];
        if (x >= 0) {
            return move(x, y);
        } else {
            return true;
        }
    }

    private boolean moveTop(int[] lastPosition) {
        int x = lastPosition[0];
        int y = lastPosition[1] - 1;
        if (y >= 0) {
            return move(x, y);
        } else {
            return true;
        }
    }

    private boolean move(int x, int y) {
        if (checkCrashBody(x, y)) {
            return true;
        }
        int[] position = getNewPosition(x, y);
        lastPosition = position;
//        snake.addLast(position);//等于 addLast
        checkFood(x, y, position);
//        printSnake();
        return false;
    }

    private void checkFood(int x, int y, int[] position) {

        if (newfoodIndex >= food.length) {
            System.out.println("没有食物了");
            snake.addLast(position);//添加头部
            snake.removeFirst();//去掉尾部 空什么也不做
            return;
        }
        if (food[newfoodIndex][0] == y && food[newfoodIndex][1] == x) {
            bodys.add(position);
            newfoodIndex++;
            snake.addLast(position);
        } else {
            if (!snake.isEmpty()) {
                snake.addLast(position);//添加头部
                snake.removeFirst();//去掉尾部 空什么也不做
            }
        }

    }

    private void printSnake() {
        System.out.println("snake: ");
        for (int[] ints : snake) {
            System.out.println(Arrays.toString(ints));
        }
    }

}
