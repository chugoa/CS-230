
/**
 * A teleporter cell. Moves a player to the connected teleporter.
 *
 * @author Liam Date
 * @version 1.0
 *
 */
public class Teleporter extends Cell {

    private int xLinkedCoord;
    private int yLinkedCoord;

    /**
     * Constructor for a teleporter Cell.
     *
     * @param xLinkedCoord The x co-ordinate of the linked Teleporter.
     * @param yLinkedCoord The y co-ordinate of the linked Teleporter.
     */
    public Teleporter(int xLinkedCoord, int yLinkedCoord) {
        super(CellType.TELEPORTER);
        this.xLinkedCoord = xLinkedCoord;
        this.yLinkedCoord = yLinkedCoord;
    }

    @Override
    public String toString() {
        String tokenDoorData = super.toString();
        tokenDoorData += "#" + Integer.toString(getLinkedX())
                + "#" + Integer.toString(getLinkedY());
        return tokenDoorData;
    }

    /**
     * Get the x co-ordinate of the linked teleporter.
     *
     * @return The x co-ordinate of the linked teleporter.
     */
    public int getLinkedX() {
        return this.xLinkedCoord;
    }

    /**
     * Get the y co-ordinate of the linked teleporter.
     *
     * @return The y co-ordinate of the linked teleporter.
     */
    public int getLinkedY() {
        return this.yLinkedCoord;
    }
}
