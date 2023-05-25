package com.gol;

import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class GameOfLife {
    private final int[][] lifeBoard;

    /* This parameter is used to track state of cells after Each Generation.
    If not state change then Equilibrium is achieved. */
    private boolean isEquilibriumAchieved = false;
    private final List<Pair<Integer, Integer>> aliveCells;
    public GameOfLife(int boardSize, final List<Pair<Integer, Integer>> aliveCells) {
        this.lifeBoard = new int[boardSize][boardSize];
        this.aliveCells = aliveCells;
        this.initializeLifeBoard();
    }
    private void initializeLifeBoard() {
        aliveCells.forEach(x -> lifeBoard[x.getValue0()][x.getValue1()] = 1);
    }
    private boolean aliveDeadCellStateModification(int originalState, int noOfAliveNeighbours) {

        /* If cell state is DEAD */
        if (originalState == 0) {
            return noOfAliveNeighbours == 3;
        }

        /* If cell state is ALIVE */
        if (originalState == 1) {
            return noOfAliveNeighbours < 2 || noOfAliveNeighbours > 3;
        }

        return false;
    }

    /**
     * Increment the generation stepWise
     */
    public void stepIntoNextGeneration() {
        List<Pair<Integer, Integer>> captureChanges = new ArrayList<>();
        for(int row=0; row < lifeBoard.length; row++) {
            for(int col=0; col < lifeBoard[row].length; col++) {
                if(aliveDeadCellStateModification(lifeBoard[row][col], getAliveOrDeadCellsNeighbourCount(row, col))) {
                    captureChanges.add(Pair.with(row, col));
                }
            }
        }
        modifyLifeboardForNextGeneration(captureChanges);
    }

    /**
     * Check all the neighbours if ALIVE or DEAD
     * @param row
     * @param col
     * @return Integer
     */
    private Integer getAliveOrDeadCellsNeighbourCount(int row, int col) {

        /* checkOverFlowCondition - To check the boundary conditions for co-ordinates. */
        BiFunction<Integer, Integer, Boolean> checkOverFlowCondition =
                (rowInt, colInt) -> (rowInt - 1 < 0 || colInt - 1 < 0 || rowInt + 1 >= lifeBoard.length || colInt + 1 >= lifeBoard.length);

        int countOfAliveNeighbours = 0;

        countOfAliveNeighbours += checkOverFlowCondition.apply(row, col) ? 0 : lifeBoard[row-1][col-1];
        countOfAliveNeighbours += checkOverFlowCondition.apply(row, col) ? 0 : lifeBoard[row-1][col];
        countOfAliveNeighbours += checkOverFlowCondition.apply(row, col) ? 0 : lifeBoard[row-1][col+1];

        countOfAliveNeighbours += checkOverFlowCondition.apply(row, col) ? 0 : lifeBoard[row][col-1];
        countOfAliveNeighbours += checkOverFlowCondition.apply(row, col) ? 0 : lifeBoard[row][col+1];

        countOfAliveNeighbours += checkOverFlowCondition.apply(row, col) ? 0 : lifeBoard[row+1][col-1];
        countOfAliveNeighbours += checkOverFlowCondition.apply(row, col) ? 0:  lifeBoard[row+1][col];
        countOfAliveNeighbours += checkOverFlowCondition.apply(row, col) ? 0 : lifeBoard[row+1][col+1];

        return countOfAliveNeighbours;
    }
    public void printBoard() {
        for (int[] rows : lifeBoard) {
            for (int item : rows) {
                String placeHolder = String.valueOf(item).equalsIgnoreCase("0") ? "0" : "*";
                System.out.print(placeHolder + " ");
            }
            System.out.println();
        }
    }

    /**
     * Modify cell state based on the changes given as Input.
     * If cellState is DEAD, make is ALIVE and vice-versa.
     * @param captureChanges
     */
    private void modifyLifeboardForNextGeneration(List<Pair<Integer, Integer>> captureChanges) {
        if(captureChanges.size() == 0) {
            isEquilibriumAchieved = true;
            return;
        }
        captureChanges.forEach(item -> lifeBoard[item.getValue0()][item.getValue1()] = lifeBoard[item.getValue0()][item.getValue1()] == 0 ? 1 : 0);
    }

    public boolean isEquilibriumAchieved() {
        return isEquilibriumAchieved;
    }
}
