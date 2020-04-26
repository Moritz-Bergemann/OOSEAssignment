package Controller;

import java.util.Random;
import java.lang.Math;

/**
 * Class containing convenience methods for handling random chance throughout program.
 */
public class Chance {
    public static int randBetween(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("Lower bound cannot be greater than upper bound");
        }

        Random rand = new Random();

        //Getting number by setting lower bound as min & upper bound as max (standard bound is between 0 & import - 1)
        return rand.nextInt(max - min) + min + 1;
    }

    public static boolean chance(double trueChance) {
        if (trueChance <= 0.0) {
            throw new IllegalArgumentException("Chance cannot be less than 0");
        }

        /*Returns whether random number between 0.0 & 1.0 is less than imported chance double
            (probability of input chance)*/
        return (trueChance >= Math.random());
    }
}
