import java.util.Scanner;

public class Main {
    private final static Scanner SCAN = new Scanner(System.in);

    public static void main(String[] args) {
        char[][] ocean = new char[10][10];
        Ship[] ships = {new Ship("Aircraft Carrier", 5), new Ship("Battleship", 4),
                new Ship("Submarine", 3), new Ship("Cruiser", 3), new Ship("Destroyer", 2)};

        initializeOcean(ocean);
        printOceanField(ocean);
        placeShips(ocean, ships);
    }

    private static void initializeOcean(char[][] ocean) {
        for (int i = 0; i < 10; i++) {
            ocean[i] = "~".repeat(10).toCharArray();
        }
    }

    private static void printOceanField(char[][] ocean) {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < 10; i++) {
            print((char) (i + 65) + " ");
            for (int j = 0; j < 10; j++) {
                print(j == 9 ? ocean[i][j] + "\n" : ocean[i][j] + " ");
            }
        }
    }

    private static void placeShips(char[][] ocean, Ship[] ships) {
        for (Ship ship : ships) {
            setCoordinate(ship, ocean);
            print("\n");
            printOceanField(ocean);
        }
    }

    private static void setCoordinate(Ship ship, char[][] ocean) {
        String message = String.format("%nEnter the coordinates of the %s (%d cells):%n%n", ship.name, ship.size);
        String[] coordinates;
        int[] positions;
        boolean shipNear = true;

        while (shipNear) {
            coordinates = getString(message).toUpperCase().split(" ");
            if (coordinates.length != 2 || (positions = getPositions(coordinates)).length != 4) {
                message = "\nError! Please enter valid coordinates:\n\n";
            } else if (notRange(positions)) {
                message = "\nError! Please enter coordinates within range. Try again:\n\n";
            } else if (positions[0] != positions[2] && positions[1] != positions[3]) {
                message = "\nError! Wrong ship location! Try again:\n\n";
            } else if (Math.abs(positions[0] - positions[2]) + 1 != ship.size
                    && Math.abs(positions[1] - positions[3]) + 1 != ship.size) {
                message = String.format("\nError! Wrong length of the %s! Try again:\n\n", ship.name);
            } else {
                if (positions[0] == positions[2]) {
                    shipNear = anyShipAround(ocean, Math.max(positions[0] - 1, 0),
                            Math.min(positions[0] + 1, 9), positions[1], positions[3])
                            || ocean[positions[0]][Math.max(positions[1] - 1, 0)] != '~'
                            || ocean[positions[0]][Math.min(positions[3] + 1, 9)] != '~';

                    if (!shipNear) placeShip(ocean, true, positions[1], positions[3], positions[0]);
                } else {
                    shipNear = anyShipAround(ocean, positions[0], positions[2], Math.max(positions[1] - 1, 0),
                            Math.min(positions[1] + 1, 9))
                            || ocean[Math.max(positions[0] - 1, 0)][positions[1]] != '~'
                            || ocean[Math.min(positions[2] + 1, 9)][positions[1]] != '~';

                    if (!shipNear) placeShip(ocean, false, positions[0], positions[2], positions[1]);
                }
                if (shipNear) message = "\nError! You placed it too close to another one. Try again:\n\n";
            }
        }
    }

    private static int[] getPositions(String[] coordinates) {
        try {
            int num0 = coordinates[0].charAt(0) - 65;
            int num1 = Integer.parseInt(coordinates[0].substring(1)) - 1;
            int num2 = coordinates[1].charAt(0) - 65;
            int num3 = Integer.parseInt(coordinates[1].substring(1)) - 1;

            return new int[]{Math.min(num0, num2), Math.min(num1, num3), Math.max(num0, num2), Math.max(num1, num3)};
        } catch (Exception e) {
            return new int[]{};
        }
    }

    private static boolean anyShipAround(char[][] ocean, int iMin, int iMax, int jMin, int jMax) {
        for (int i = iMin; i <= iMax; i++) {
            for (int j = jMin; j <= jMax; j++) {
                if (ocean[i][j] != '~') return true;
            }
        }
        return false;
    }

    private static void placeShip(char[][] ocean, boolean flip, int iMin, int iMax, int j) {
        for (int i = iMin; i <= iMax; i++) {
            ocean[flip ? j : i][flip ? i : j] = 'O';
        }
    }

    private static boolean notRange(int[] numbers) {
        for (int number : numbers) {
            if (number < 0 || number > 9) return true;
        }
        return false;
    }

    private static void print(String string) {
        System.out.print(string);
    }

    private static String getString(String message) {
        print(message);
        return SCAN.nextLine();
    }
}