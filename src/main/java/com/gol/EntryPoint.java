package com.gol;

import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;

public class EntryPoint {

    /**
     * Notable points
     * -----------
     * Alive Cells = 1, Dead Cells = 0
     * Orthogonal Grid Structure (nXn)
     * Added Glider pattern as Initial state
     * For printing the board DEAD cells are printed as 0 and ALIVE cells are printed as *
     */

    public static void main(String[] args) {
        /* Define initial state of the cell */
        List<Pair<Integer, Integer>> aliveCells = new ArrayList<>();
        aliveCells.add(Pair.with(11, 14));
        aliveCells.add(Pair.with(12, 15));
        aliveCells.add(Pair.with(13, 13));
        aliveCells.add(Pair.with(13, 14));
        aliveCells.add(Pair.with(13, 15));

        final GameOfLife gameOfLife = new GameOfLife(25, aliveCells);

        /* Total iterations for forwarding to next generation */
        int totalCount = 100;

        /* Generate Stages max up to 100 or if Equilibrium is achieved*/
        for (int c = 0; c < totalCount; c++) {
            System.out.println("------------------------------------------------------------------");
            if (gameOfLife.isEquilibriumAchieved()) {
                System.out.printf("Equilibrium achieved after %s iterations. Stopping now!", (c+1));
                break;
            }
            gameOfLife.stepIntoNextGeneration();
            gameOfLife.printBoard();
        }
    }
}
