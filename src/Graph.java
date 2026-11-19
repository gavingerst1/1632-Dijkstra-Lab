import java.util.*;

public class Graph {
    private Map<Vertex, List<Edge>> adjVertices;

    public Graph() {
        adjVertices = new HashMap<>();
    }

    public void addVertex(String label) {
        adjVertices.putIfAbsent(new Vertex(label), new ArrayList<>());
    }

    public void addEdge(String label1, String label2, double weight) {
        Vertex v1 = new Vertex(label1);
        Vertex v2 = new Vertex(label2);

        adjVertices.get(v1).add(new Edge(v2, weight));
        adjVertices.get(v2).add(new Edge(v1, weight));
    }

    public Set<String> getVertices() {
        Set<String> vertices = new TreeSet<>();

        for (Vertex v : adjVertices.keySet()) {
            vertices.add(v.label);
        }

        return vertices;
    }

    public void dijkstra(String start) {
        if (!getVertices().contains(start)) {
            System.out.println("Node not found.");
            return;
        }

        Map<String, Double> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();

        PriorityQueue<String> queue = new PriorityQueue<>(
                Comparator.comparingDouble(distances::get)
        );

        for (String vertex : getVertices()) {
            distances.put(vertex, Double.POSITIVE_INFINITY);
        }

        distances.put(start, 0.0);
        queue.add(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();

            for (Edge edge : adjVertices.get(new Vertex(current))) {
                String neighbor = edge.destination.label;

                double newDistance = distances.get(current) + edge.weight;

                if (newDistance < distances.get(neighbor)) {
                    distances.put(neighbor, newDistance);
                    previous.put(neighbor, current);

                    queue.remove(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        for (String vertex : getVertices()) {
            if (distances.get(vertex) == Double.POSITIVE_INFINITY) {
                System.out.println(vertex + " unreachable");
                continue;
            }

            List<String> path = new ArrayList<>();
            String current = vertex;

            while (current != null) {
                path.add(current);
                current = previous.get(current);
            }

            Collections.reverse(path);

            System.out.println(
                    String.join(" -> ", path) +
                            " dist: " + distances.get(vertex)
            );
        }
    }
}