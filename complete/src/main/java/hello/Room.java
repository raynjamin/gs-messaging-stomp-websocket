package hello;

/**
 * Created by Ben on 2/16/17.
 */
public class Room {
    private Room east, west, north, south;
    private int id = ++INC_ID;
    private static int INC_ID = 0;


    public Room getEast() {
        return east;
    }

    public int getId() {
        return id;
    }

    public void setEast(Room east) {
        this.east = east;
    }

    public Room getWest() {
        return west;
    }

    public void setWest(Room west) {
        this.west = west;
    }

    public Room getNorth() {
        return north;
    }

    public void setNorth(Room north) {
        this.north = north;
    }

    public Room getSouth() {
        return south;
    }

    public void setSouth(Room south) {
        this.south = south;
    }
}
