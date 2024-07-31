import java.util.*;

public class FlightPathDrawer {
    static class Point {
        int x, y;
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static class Flight {
        List<Point> path;
        Flight() {
            path = new ArrayList<>();
        }
    }

    public static void main(String[] args) {
        List<Flight> flights = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        // Input flight paths
        for (int i = 1; i <= 3; i++) {
            System.out.println("Enter coordinates for Flight " + i + " (x,y format, enter -1,-1 to finish):");
            Flight flight = new Flight();
            while (true) {
                String input = scanner.nextLine();
                if (input.equals("-1,-1")) break;
                String[] coords = input.split(",");
                flight.path.add(new Point(Integer.parseInt(coords[0]), Integer.parseInt(coords[1])));
            }
            flights.add(flight);
        }

        // Draw flight paths
        drawFlightPaths(flights);
    }

    private static void drawFlightPaths(List<Flight> flights) {
        int maxX = 0, maxY = 0;
        for (Flight flight : flights) {
            for (Point p : flight.path) {
                maxX = Math.max(maxX, p.x);
                maxY = Math.max(maxY, p.y);
            }
        }

        char[][] grid = new char[maxY + 1][maxX + 1];
        for (char[] row : grid) {
            Arrays.fill(row, '.');
        }

        char flightSymbol = 'A';
        for (Flight flight : flights) {
            for (int i = 0; i < flight.path.size() - 1; i++) {
                Point start = flight.path.get(i);
                Point end = flight.path.get(i + 1);
                drawLine(grid, start, end, flightSymbol);
            }
            flightSymbol++;
        }

        // Print the grid
        for (int y = maxY; y >= 0; y--) {
            for (int x = 0; x <= maxX; x++) {
                System.out.print(grid[y][x] + " ");
            }
            System.out.println();
        }
    }

    private static void drawLine(char[][] grid, Point start, Point end, char symbol) {
        int dx = Math.abs(end.x - start.x);
        int dy = Math.abs(end.y - start.y);
        int sx = start.x < end.x ? 1 : -1;
        int sy = start.y < end.y ? 1 : -1;
        int err = dx - dy;

        while (true) {
            grid[start.y][start.x] = symbol;
            if (start.x == end.x && start.y == end.y) break;
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                start.x += sx;
            }
            if (e2 < dx) {
                err += dx;
                start.y += sy;
            }
        }
    }
}