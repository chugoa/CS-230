
import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

/**
 * Class which defines a level which can be played by the user
 *
 * @author Sean Beck, George Manning
 * @version 1.0
 */
public class Level {

    private static final String themeFile = "themes" + File.separator;
    private Board board;
    private Player player;
    private Enemy[] enemies;
    private int levelNum;
    private UserProfile user;

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
     * generates a level for testing of normal play, has no enemies.
     */
    public Level() {
        Cell[][] cells = Board.blankBoard(11, 9);

        cells[2][2] = new Cell(CellType.FIREBOOTS);
        cells[2][4] = new Cell(CellType.FIRE);

        cells[4][2] = new Cell(CellType.FLIPPERS);
        cells[4][4] = new Cell(CellType.WATER);

        cells[6][2] = new Cell(CellType.RED_KEY);
        cells[6][4] = new Cell(CellType.RED_DOOR);

        cells[8][2] = new Teleporter(8, 4);
        cells[8][4] = new Teleporter(8, 2);

        cells[9][3] = new Cell(CellType.GOAL);

        cells[2][6] = new Cell(CellType.TOKEN);
        cells[3][6] = new Cell(CellType.TOKEN);
        cells[4][6] = new Cell(CellType.TOKEN);
        cells[5][6] = new Cell(CellType.TOKEN);
        cells[6][6] = new Cell(CellType.TOKEN);

        cells[7][6] = new TokenDoor(3);
        cells[8][6] = new TokenDoor(1);

        board = new Board(cells, 11, 9, 9, 3);
        player = new Player(1, 3);
        enemies = new Enemy[1];
        enemies[0] = new DumbTargetingEnemy(1, 7, player);
        setTheme("dev");
    }

    public static void main(String[] args) {

        Level level;
        Board board;
        Player player;
        Enemy[] enemies;
		
	Cell[][] cells = Board.blankBoard(32, 20);
        
        cells[4][1] = new Cell(CellType.WALL);
	cells[4][2] = new Cell(CellType.WALL);
	cells[4][3] = new Cell(CellType.WALL);
	cells[4][4] = new Cell(CellType.WALL);
	cells[4][5] = new Cell(CellType.WALL);
	cells[4][6] = new Cell(CellType.WALL);
	cells[4][7] = new Cell(CellType.WALL);
	cells[4][8] = new Cell(CellType.WALL);
	cells[5][8] = new Cell(CellType.WALL);
	cells[6][8] = new Cell(CellType.WALL);
	cells[8][8] = new Cell(CellType.WALL);
	cells[9][8] = new Cell(CellType.WALL);
	cells[10][8] = new Cell(CellType.WALL);
	cells[4][9] = new Cell(CellType.WALL);
	cells[4][9] = new Cell(CellType.WALL);
	cells[4][11] = new Cell(CellType.WALL);
	cells[4][12] = new Cell(CellType.WALL);
	cells[4][13] = new Cell(CellType.WALL);
	cells[5][13] = new Cell(CellType.WALL);
	cells[6][13] = new Cell(CellType.WALL);
	cells[8][13] = new Cell(CellType.WALL);
	cells[9][13] = new Cell(CellType.WALL);
	cells[10][13] = new Cell(CellType.WALL);
        
	cells[11][5] = new Cell(CellType.WALL);
	cells[11][6] = new Cell(CellType.WALL);
	cells[11][7] = new Cell(CellType.WALL);
	cells[11][8] = new Cell(CellType.WALL);
	cells[11][9] = new Cell(CellType.WALL);
	cells[11][10] = new Cell(CellType.WALL);
	cells[11][11] = new Cell(CellType.WALL);
	cells[11][12] = new Cell(CellType.WALL);
	cells[11][13] = new Cell(CellType.WALL);
	cells[11][14] = new Cell(CellType.WALL);
	cells[11][15] = new Cell(CellType.WALL);
	cells[12][15] = new Cell(CellType.WALL);
	cells[13][15] = new Cell(CellType.WALL);
	cells[14][15] = new Cell(CellType.WALL);			
	cells[15][15] = new Cell(CellType.WALL);
	cells[16][15] = new Cell(CellType.WALL);
	cells[17][15] = new Cell(CellType.WALL);
	cells[18][15] = new Cell(CellType.WALL);
		
	cells[14][16] = new Cell(CellType.WALL);	
	cells[14][17] = new Cell(CellType.WALL);
	cells[14][19] = new Cell(CellType.WALL);
	cells[14][20] = new Cell(CellType.WALL);
	cells[14][21] = new Cell(CellType.WALL);
	cells[15][21] = new Cell(CellType.WALL);
	cells[16][21] = new Cell(CellType.WALL);
	cells[17][21] = new Cell(CellType.WALL);
	cells[18][21] = new Cell(CellType.WALL);
	cells[14][22] = new Cell(CellType.WALL);
	cells[14][23] = new Cell(CellType.WALL);
	cells[14][24] = new Cell(CellType.WALL);
	cells[14][25] = new Cell(CellType.WALL);
	cells[14][26] = new Cell(CellType.WALL);
	cells[14][27] = new Cell(CellType.WALL);
	cells[15][27] = new Cell(CellType.WALL);
	cells[16][27] = new Cell(CellType.WALL);
	cells[17][27] = new Cell(CellType.WALL);
	cells[18][27] = new Cell(CellType.WALL);
	cells[14][28] = new Cell(CellType.WALL);
	cells[14][30] = new Cell(CellType.WALL);
		
	cells[15][1] = new Cell(CellType.WALL);
	cells[15][2] = new Cell(CellType.WALL);
	cells[15][3] = new Cell(CellType.WALL);
	cells[15][4] = new Cell(CellType.WALL);
	cells[15][5] = new Cell(CellType.WALL);
		
	cells[1][27] = new Cell(CellType.WALL);
	cells[2][27] = new Cell(CellType.WALL);
	cells[3][27] = new Cell(CellType.WALL);
	cells[4][27] = new Cell(CellType.WALL);
	cells[5][27] = new Cell(CellType.WALL);
	cells[6][27] = new Cell(CellType.WALL);
	cells[6][28] = new Cell(CellType.WALL);
	cells[6][30] = new Cell(CellType.WALL);
		
	//cells[23][1] = new Cell(CellType.WALL);
	//cells[23][2] = new Cell(CellType.WALL);
	//cells[23][3] = new Cell(CellType.WALL);
	//cells[24][3] = new Cell(CellType.WALL);
	//cells[25][3] = new Cell(CellType.WALL);
	//cells[26][3] = new Cell(CellType.WALL);
		
		
        cells[18][14] = new Cell(CellType.FIREBOOTS);
        cells[16][6] = new Cell(CellType.FIRE);
	cells[17][6] = new Cell(CellType.FIRE);
	cells[18][6] = new Cell(CellType.FIRE);
		
	cells[16][3] = new Cell(CellType.FIRE);
	cells[17][3] = new Cell(CellType.FIRE);
	cells[18][3] = new Cell(CellType.FIRE);
		

        cells[7][1] = new Cell(CellType.FLIPPERS);
        cells[11][1] = new Cell(CellType.WATER);
	cells[11][2] = new Cell(CellType.WATER);
	cells[11][3] = new Cell(CellType.WATER);
	cells[11][4] = new Cell(CellType.WATER);
		
	cells[12][13] = new Cell(CellType.WATER);
	cells[13][13] = new Cell(CellType.WATER);
	cells[12][14] = new Cell(CellType.WATER);
	cells[13][14] = new Cell(CellType.WATER);
        

        cells[18][29] = new Cell(CellType.RED_KEY);
        cells[6][29] = new Cell(CellType.RED_DOOR);
        
        cells[6][26] = new Cell(CellType.BLUE_KEY);
        cells[14][29] = new Cell(CellType.BLUE_DOOR);

        cells[1][29] = new Teleporter(22, 15);
        cells[15][22] = new Teleporter(29, 1);
		
	cells[18][26] = new Teleporter(16, 18);
        cells[18][16] = new Teleporter(26, 18);
		
	//cells[1][16] = new Teleporter(1, 24);
        //cells[24][1] = new Teleporter(16, 1);

        cells[17][1] = new Cell(CellType.GOAL);
	//cells[26][1] = new Cell(CellType.GOAL);

        cells[15][20] = new Cell(CellType.TOKEN);
        cells[9][9] = new Cell(CellType.TOKEN);

        cells[14][18] = new TokenDoor(1);
	cells[7][13] = new TokenDoor(1);
	cells[7][8] = new TokenDoor(2);

        board = new Board(cells, 32, 20, 1, 18);
        player = new Player(1, 2);
        enemies = new Enemy[0];
		
	level = new Level(board, player, enemies, new UserProfile("liam", 2, "dev"));
        String levelData = level.toString();
    
        System.out.print(level.toString());
    }

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
        if (enemies != null) {
            for (Enemy elem : enemies) {
                if (elem.getXCoord() == player.getXCoord()
                        && elem.getYCoord() == player.getYCoord()) {
                    return true;
                }
            }
        }

        return false;
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

    public void setLevelNum(int num) {
        this.levelNum = num;
    }

    public int getLevelNum() {
        return this.levelNum;
    }

    /**
     *
     * @return
     */
    public Board getBoard() {
        return this.board;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Enemy[] getEnemies() {
        return this.enemies;
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
