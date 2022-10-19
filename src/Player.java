import java.util.*;

public class Player {
    private String name;
    private int score;
    private ArrayList<String> rack;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.rack = new ArrayList<>();
    }

    /**
     * Add to player score
     */
    public void addPoints(int points) {
        this.score += points;
    }

    /**
     * Add tile to player's rack
     * @param tile  Tile to be added
     */
    public void addTiles(String tile) {
        this.rack.add(tile);
    }

    /**
     * Return the number of tiles in the players rack
     * @return int representing rack size
     */
    public int rackSize() {
        return this.rack.size();
    }

}
