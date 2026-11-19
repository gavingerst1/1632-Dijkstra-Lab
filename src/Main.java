import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Graph graph = new Graph();
        Scanner input = new Scanner(System.in);

        System.out.print("Graph file to read from: ");
        String filename = input.nextLine();

        Scanner scanner = new Scanner(new File(filename));

        while (scanner.hasNext()) {
            String vertex1 = scanner.next();
            String vertex2 = scanner.next();
            double weight = scanner.nextDouble();

            graph.addVertex(vertex1);
            graph.addVertex(vertex2);
            graph.addEdge(vertex1, vertex2, weight);
        }

        scanner.close();

        System.out.println();
        System.out.println("List of nodes: " +
                String.join(", ", graph.getVertices()));

        while (true) {
            System.out.print("\nNode to start at: ");
            String start = input.nextLine().trim();

            if (start.isEmpty()) {
                break;
            }

            graph.dijkstra(start);
        }

        input.close();
    }
}
