import java.util.*;

public class SpecialCastle {
    private static final int BOARD_SIZE = 9;
    private boolean[][] soldiers;
    private int castleX, castleY;
    private List<List<String>> allPaths;

    public SpecialCastle() {
        soldiers = new boolean[BOARD_SIZE][BOARD_SIZE];
        allPaths = new ArrayList<>();
    }

    public void placeSoldiers(Scanner scanner, int numSoldiers) {
        for (int i = 0; i < numSoldiers; i++) {
            System.out.print("Enter coordinates for soldier " + (i + 1) + ": ");
            String[] coords = scanner.nextLine().split(",");
            int x = Integer.parseInt(coords[0]) - 1;
            int y = Integer.parseInt(coords[1]) - 1;
            soldiers[x][y] = true;
        }
    }

    public void placeCastle(Scanner scanner) {
        System.out.print("Enter the coordinates for your \"special\" castle: ");
        String[] coords = scanner.nextLine().split(",");
        castleX = Integer.parseInt(coords[0]) - 1;
        castleY = Integer.parseInt(coords[1]) - 1;
    }

    public void findPaths() {
        List<String> currentPath = new ArrayList<>();
        currentPath.add("Start (" + (castleX + 1) + "," + (castleY + 1) + ")");
        findPathsRecursive(castleX, castleY, 0, currentPath);
    }

    private void findPathsRecursive(int x, int y, int direction, List<String> currentPath) {
        // Check if we're back home
        if (x == castleX && y == castleY && currentPath.size() > 1) {
            currentPath.add("Arrive (" + (x + 1) + "," + (y + 1) + ")");
            allPaths.add(new ArrayList<>(currentPath));
            currentPath.remove(currentPath.size() - 1);
            return;
        }

        // Try moving forward
        int newX = x, newY = y;
        switch (direction) {
            case 0: newY++; break; // North
            case 1: newX++; break; // East
            case 2: newY--; break; // South
            case 3: newX--; break; // West
        }

        if (newX >= 0 && newX < BOARD_SIZE && newY >= 0 && newY < BOARD_SIZE) {
            if (soldiers[newX][newY]) {
                // Kill and turn left
                soldiers[newX][newY] = false;
                currentPath.add("Kill (" + (newX + 1) + "," + (newY + 1) + "). Turn Left");
                findPathsRecursive(newX, newY, (direction + 3) % 4, currentPath);
                soldiers[newX][newY] = true;
                currentPath.remove(currentPath.size() - 1);
            } else {
                // Move forward
                currentPath.add("Move to (" + (newX + 1) + "," + (newY + 1) + ")");
                findPathsRecursive(newX, newY, direction, currentPath);
                currentPath.remove(currentPath.size() - 1);
            }
        }

        // Try jumping
        newX = x;
        newY = y;
        do {
            switch (direction) {
                case 0: newY++; break;
                case 1: newX++; break;
                case 2: newY--; break;
                case 3: newX--; break;
            }
        } while (newX >= 0 && newX < BOARD_SIZE && newY >= 0 && newY < BOARD_SIZE && !soldiers[newX][newY]);

        if (newX >= 0 && newX < BOARD_SIZE && newY >= 0 && newY < BOARD_SIZE) {
            currentPath.add("Jump to (" + (newX + 1) + "," + (newY + 1) + ")");
            findPathsRecursive(newX, newY, direction, currentPath);
            currentPath.remove(currentPath.size() - 1);
        }
    }

    public void printPaths() {
        for (int i = 0; i < allPaths.size(); i++) {
            System.out.println("Path " + (i + 1) + ":");
            System.out.println("=======");
            for (String step : allPaths.get(i)) {
                System.out.println(step);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SpecialCastle game = new SpecialCastle();

        System.out.print("Enter number of soldiers: ");
        int numSoldiers = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        game.placeSoldiers(scanner, numSoldiers);
        game.placeCastle(scanner);

        game.findPaths();
        game.printPaths();

        scanner.close();
    }
}
