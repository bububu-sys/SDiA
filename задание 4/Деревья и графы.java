//Структуры данных. Работа со структурами данных «деревья/графы»



import java.util.*;

// Класс узла дерева
class Node {
    int key;
    Node left, right;

    public Node(int key) {
        this.key = key;
        this.left = this.right = null;
    }
}

// Класс дерева
class Tree {
    private Node root;

    public Tree() {
        this.root = null;
    }

    // Вставка ключа рекурсивно
    public void insert(int key) {
        root = doInsert(root, key);
    }

    private Node doInsert(Node node, int key) {
        if (node == null) {
            return new Node(key);
        }
        if (key < node.key) {
            node.left = doInsert(node.left, key);
        } else if (key > node.key) {
            node.right = doInsert(node.right, key);
        }
        return node;
    }

    public Node getRoot() {
        return root;
    }

    // Метод поиска пути, чтобы исправить, делаем его не статическим
    public boolean findPath(Node root, int target, List<Integer> path) {
        if (root == null) return false;
        path.add(root.key);
        if (root.key == target)
            return true;
        if (findPath(root.left, target, path) || findPath(root.right, target, path))
            return true;
        path.remove(path.size() - 1); // удаление последнего элемента при возврате
        return false;
    }
}


//Графы. Алгоритм Дейкстры
import java.util.*;

class Edge {
    int destination;
    int weight;
    public Edge(int d, int w) {
        destination = d;
        weight = w;
    }
}

public class Dijkstra {
    public static int[] dijkstra(final List<List<Edge>> graph, int start) {
        int n = graph.size();
        int[] distances = new int[n];
        boolean[] visited = new boolean[n];

        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[start] = 0;

        // Очередь по расстоянию
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        pq.add(new int[]{0, start});  // {расстояние, вершина}

        while (!pq.isEmpty()) {
            int[] top = pq.poll(); // извлекаем минимальную
            int dist = top[0];
            int v = top[1];

            if (visited[v]) continue; // пропускаем обработанную
            visited[v] = true;

            // Обходим соседей
            for (Edge edge : graph.get(v)) {
                int u = edge.destination;
                int w = edge.weight;
                if (dist + w < distances[u]) {
                    distances[u] = dist + w;
                    pq.add(new int[]{distances[u], u});
                }
            }
        }
        return distances;
    }

    public static void main(String[] args) {
        List<List<Edge>> graph = new ArrayList<>();
        for (int i=0; i<6; i++) graph.add(new ArrayList<>());
        // Добавляем ребра
        graph.get(0).add(new Edge(1,4));
        graph.get(0).add(new Edge(2,7));
        graph.get(1).add(new Edge(2,1));
        graph.get(1).add(new Edge(3,2));
        graph.get(2).add(new Edge(3,3));
        graph.get(3).add(new Edge(4,1));
        graph.get(4).add(new Edge(5,2));

        int[] dist = dijkstra(graph,0);
        System.out.println("Кратчайшие расстояния от вершины 0:");
        for (int i=0; i<dist.length; i++) {
            System.out.println("До " + i + " = " + dist[i]);
        }
    }
}
