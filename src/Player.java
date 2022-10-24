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

    public String getName() {
        return this.name;
    }

    public int getScore() {
        return this.score;
    }

    public ArrayList<String> getRack() {
        return this.rack;
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
    public void addTile(String tile) {
        this.rack.add(tile);
    }

    public void removeTile(String tile) {
        this.rack.remove(tile);
    }

    /**
     * Return the number of tiles in the players rack
     * @return int representing rack size
     */
    public int rackSize() {
        return this.rack.size();
    }

    /**
     * String representation of Player's current rack
     * @return String representation of rack
     */
    public String stringRack() {
        String stringRack = "[";
        for (String s: rack) {
            stringRack += " " + s;
        }
        stringRack += " ]\n";
        return stringRack;
    }

}
