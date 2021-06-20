import java.util.HashMap;
import java.util.List;

public class Ship {
    public final String name;
    public final int size;
    private final HashMap<Integer, Integer> coordinates = new HashMap<>();

    public Ship(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public void addCoordinate(int i, int j) {
        if (coordinates.size() < size) {
            coordinates.put(i, j);
        } else throw new RuntimeException("Ship size exceeded.");
    }

    public void hitShip(int i, int j, boolean vertical) {
        if (coordinates.size() != 0) {
            coordinates.remove(vertical ? i : j, vertical ? j : i);
        } else throw new RuntimeException("Ship is already sunk.");
    }

    public boolean isSunk() {
        return coordinates.isEmpty();
    }

    public static Ship findShip(int i, int j, List<Ship> ships, boolean vertical) {
        for (Ship ship : ships) {
            if (ship.coordinates.containsKey(vertical ? i : j)) {
                if (ship.coordinates.get(vertical ? i : j).equals(vertical ? j : i)) {
                    return ship;
                }
            }
        }
        throw new RuntimeException("There was no ship.");
    }
}