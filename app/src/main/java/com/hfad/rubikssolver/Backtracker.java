package com.hfad.rubikssolver;


/**
 * This class represents the classic recursive backtracking algorithm.
 * It has a solver that can take a valid configuration and return a
 * solution, if one exists.
 *
 * This file comes from the backtracking lab. It should be useful
 * in this project. A second method has been added that you should
 * implement.
 *
 * @author Sean Strout @ RIT CS
 * @author James Heliotis @ RIT CS
 * @author Patrick Ly & Daniel Roy Barman
 */
public class Backtracker {


    /**
     * Initialize a new backtracker.
     */
    public Backtracker() {

    }


    /**
     * Try find a solution, if one exists, for a given configuration.
     *
     * @param config A valid configuration
     * @return A solution config, or null if no solution
     */
    public Configuration solve(Configuration config) {
        //config.display();
        //System.out.println("Valid: "+config.isValid());
        if (config.isGoal()) {
            return config;
        } else {

            for (Configuration child : config.getSuccessors()) {
                if (child.isValid()) {

                    Configuration sol = solve(child);

                    if (sol != null) {
                        return sol;
                    }
                } else {
                }
            }
            // implicit backtracking happens here
        }
        return null;
    }
}