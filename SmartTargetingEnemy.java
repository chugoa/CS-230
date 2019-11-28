
import javafx.scene.image.Image;

/**
 * A smart targeting enemy. An enemy that always moves in the shortest route
 * towards the player.
 *
 * @author Ahmed Ajaj
 * @version 1.0
 */
public class SmartTargetingEnemy extends TargetingEnemy {

    private int xDifference;
    private int yDifference;

    /**
     * This method constructs a smart targeting enemy using the constructor in
     * the super class
     *
     * @param x The initial x co-ordinate.
     * @param y The initial y co-ordinate.
     * @param image The image used to present the moveable object
     * @param player Reference to the player
     */
    public SmartTargetingEnemy(int x, int y, Image image, Player player) {
        super(x, y, image, player);
    }

    /**
     * constructs from a string of data containing all information about the
     * SmartTargetingEnemy (including superclass data).
     *
     * @param smartData SmartTargetingEnemy data and TargetingEnemy data.
     */
    public SmartTargetingEnemy(String smartData) {
        super(smartData);
    }

    /**
     * generates a string from which a duplicate SmartTargetingEnemy can be
     * constructed, containing all information about the SmartTargetingEnemy and
     * its super classes.
     *
     * @return the information as a string.
     */
    @Override
    public String toString() {
        return EnemyType.SMART_TARGETING + ";" + super.toString();
    }

    /**
     * This method calculates the difference between the x coordinates of the
     * enemy and the player
     *
     * @return the difference between the x coordinates of the enemy and the
     * player
     */
    public int xDifference() {
        if (player.getXCoord() < super.getXCoord()) {
            xDifference = super.getXCoord() - player.getXCoord();
        } else {
            xDifference = player.getXCoord() - super.getXCoord();
        }
        return xDifference;
    }

    /**
     * This method calculates the difference between the y coordinates of the
     * enemy and the player
     *
     * @return the difference between the y coordinates of the enemy and the
     * player
     */
    public int yDifference() {
        if (player.getYCoord() < super.getYCoord()) {
            yDifference = super.getXCoord() - player.getYCoord();
        } else {
            yDifference = player.getYCoord() - super.getYCoord();
        }
        return yDifference;
    }

    /**
     * This method calculates the direction the smart targeting enemy will move
     * in while the move is valid and meets the criteria in the isMoveValid
     * method
     *
     * @param board Reference to the board class
     * @return The direction the enemy will move in
     */
    public Direction calcDirection(Board board) {
        while (super.isMoveValid()) { //!!!work this out
            if (xDifference >= yDifference) {
                if (player.getXCoord() > super.getXCoord()) {
                    return Direction.RIGHT;
                } else if (player.getXCoord() < super.getXCoord()) {
                    return Direction.LEFT;
                }
            } else if (xDifference < yDifference) {
                if (player.getYCoord() > super.getYCoord()) {
                    return Direction.DOWN;
                } else if (player.getYCoord() < super.getYCoord()) {
                    return Direction.UP;
                }
            }
        }
        return null;
    }

    /**
     * !!!implement
     * @param board
     * @return 
     */
    @Override
    public Direction calculateDirection(Board board) {
        return null;
    }
}
