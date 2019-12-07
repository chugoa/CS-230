
import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

/**
 * Class which defines a level which can be played by the user
 *
 * @author Sean Beck, George Manning
 * @version 3.0
 */
public class Level {

    private static final String themeFile = "themes" + File.separator;
    private Board board;
    private Player player;
    private Enemy[] enemies;
    private int levelNum;
    private UserProfile user;
    
    /**
     * Constructor for a level object.
     * @param board The level's board.
     * @param player The player character for the level.
     * @param enemies An array of the enemies.
     * @param user The user playing the level.
     */
    public Level(Board board, Player player, Enemy[] enemies, UserProfile user) {
        this.board = board;
        this.player = player;
        this.enemies = enemies;
        this.user = user;
    }

        /**
     * loads a level from a string as generated by toString(), used to load data
     * from persistent storage.
     *
     * @param levelData the string generated by toString().
     */
    public Level(String levelData) {
        String[] splitData = levelData.split(":");
        String[] enemyData;
        EnemyType enemyType;

        levelNum = Integer.parseInt(splitData[0]);

        if (splitData.length >= 3) {
            board = new Board(splitData[1]);
            player = new Player(splitData[2]);

        } else {
            System.out.println("ERROR - level construction failure");
        }

        if (splitData.length > 3) {
            enemies = new Enemy[splitData.length - 3];
            for (int i = 3; i < splitData.length; i++) {
                enemyData = splitData[i].split(";");
                enemyType = EnemyType.valueOf(enemyData[0]);
                switch (enemyType) {
                    case STRAIGHT_LINE:
                        enemies[i - 3] = new StraightLineEnemy(splitData[i]);
                        break;

                    case WALL_FOLLOWING:
                        enemies[i - 3] = new WallFollowingEnemy(splitData[i]);
                        break;

                    case DUMB_TARGETING:
                        enemies[i - 3] = new DumbTargetingEnemy(splitData[i], player);
                        break;

                    case SMART_TARGETING:
                        enemies[i - 3] = new SmartTargetingEnemy(splitData[i], player);
                        break;

                    default:
                        System.out.println("ERROR - enemy construction failure");
                }
            }
        }
    }


    



    /**
     * Plays one rotation of the level. Using the user inputed direction.
     *
     * @param event The key key pressed by the user to move the player.
     */
    public void play(KeyEvent event) {
        switch (event.getCode()) {

            case RIGHT:
                play(Direction.RIGHT);
                break;
            case LEFT:
                play(Direction.LEFT);
                break;
            case UP:
                play(Direction.UP);
                break;
            case DOWN:
                play(Direction.DOWN);
                break;
            default:
                // Do nothing
                break;
        }
    }

    /**
     * Works out if the has been killed.
     *
     * @return True is player has been killed, false otherwise.
     */
    public boolean isPlayerDead() {
        //enemies
    	if (enemies != null) {
            for (Enemy elem : enemies) {
                if (elem.getXCoord() == player.getXCoord()
                        && elem.getYCoord() == player.getYCoord()) {
                    return true;
                }
            }
        }
        //water or fire
        if (this.board.getCell(player.getXCoord(), player.getYCoord()).getType().equals(CellType.FIRE)
        		&& !this.player.hasItem(Item.FIREBOOTS)){
        	return true;
        } else if (this.board.getCell(player.getXCoord(), player.getYCoord()).getType().equals(CellType.WATER)
        		&& !this.player.hasItem(Item.FLIPPERS)){
        	return true;
        } else {
        	return false;
        }
    }

    /**
     * Works out if the player has reached the goal.
     *
     * @return True if the player has reached the goal false otherwise.
     */
    public boolean hasPlayerWon() {
        return (player.getXCoord() == board.getGoalX()
                && player.getYCoord() == board.getGoalY());
    }
    
    /**
     * Sets the value of the user.
     * @param user The user profile being set.
     */
    public void setUser(UserProfile user) {
        this.user = user;
    }

    /**
     * Sets the theme for each level.
     *
     * @param theme The selected theme.
     */
    public void setTheme(String theme) {
        board.setTheme(themeFile + theme);
        player.setImage(new Image(themeFile + theme + File.separator + "PLAYER.png"));
        if (enemies != null) {
            for (int i = 0; i < enemies.length; i++) {
                enemies[i].setImage(new Image(themeFile + theme + File.separator + enemies[i].getType() + ".png"));
            }
        }
    }
    
    /**
     * Get method for the level num.
     * @return The level number.
     */
    public int getLevelNum() {
        return this.levelNum;
    }

    /**
     * Get method for the level's board.
     * @return The level's board.
     */
    public Board getBoard() {
        return this.board;
    }
    
    /**
     * Get method for the level's playable character.
     * @return The level's player.
     */
    public Player getPlayer() {
        return this.player;
    }
    
    /**
     * Get method for the level's enemies.
     * @return An array containing all the level's enemies.
     */
    public Enemy[] getEnemies() {
        return this.enemies;
    }
    
    /**
     * saves the level as a string from which a copy can later be loaded using
     * the Level(String) constructor, used to save the level to a text file.
     *
     * @return a string containing all information needed to load a copy of this level.
     */
    @Override
    public String toString() {
        String levelData = Integer.toString(levelNum) + ":";
        if (board != null) {
            levelData += board.toString() + ":";
        } else {
            return null;
        }

        if (player != null) {
            levelData += player.toString() + ":";
        } else {
            return null;
        }

        if (enemies != null) {
            for (int i = 0; i < enemies.length; i++) {
                if (enemies[i] != null) {
                    levelData += enemies[i].toString();
                }
                if (i < enemies.length - 1) {
                    levelData += ":";
                }
            }
        }
        return levelData;
    }

    /**
     * Preforms one turn of the level
     *
     * @param playerDirection The direction the player will move in
     */
    private void play(Direction playerDirection) {
        player.move(playerDirection, board);
        if (enemies != null && !isPlayerDead()) {
            for (Enemy elem : enemies) {
                Direction direction = elem.calculateDirection(board);
                elem.move(direction);
            }
        }

        updateBoard();
    }

    /**
     * Updates the board to turn the cell the player is standing on to a ground
     * cell, where apllicable
     */
    private void updateBoard() {
        CellType playerCell = board.getCell(player.getXCoord(), player.getYCoord()).getType();
        switch (playerCell) {
            case RED_DOOR:
            case BLUE_DOOR:
            case GREEN_DOOR:
            case TOKEN_DOOR:
            case TOKEN:
            case RED_KEY:
            case BLUE_KEY:
            case GREEN_KEY:
            case FLIPPERS:
            case FIREBOOTS:
                board.updateCell(player.getXCoord(), player.getYCoord(), (themeFile + user.getTheme()));
                break;
            default:
                break; //no nothing
        }

    }
}
