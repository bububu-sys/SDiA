//Структуры данных. Работа со структурами данных «деревья/графы»



#include <iostream>
#include <vector>
#include <list>
#include <limits>
#include <queue>

using namespace std;

// Узел дерева
struct Node {
    int key;
    Node* left;
    Node* right;

    Node(int k) : key(k), left(nullptr), right(nullptr) {}
};

// Класс дерева
class Tree {
private:
    Node* root;

    Node* doInsert(Node* node, int key) {
        if (node == nullptr) {
            return new Node(key);
        }
        if (key < node->key) {
            node->left = doInsert(node->left, key);
        } else if (key > node->key) {
            node->right = doInsert(node->right, key);
        }
        return node;
    }
public:
    Tree() : root(nullptr) {}

    void insert(int key) {
        root = doInsert(root, key);
    }

    Node* getRoot() {
        return root;
    }

    // Поиск пути от корня до целевого узла
    static bool findPath(Node* root, int target, vector<int>& path) {
        if (root == nullptr) return false;
        path.push_back(root->key);
        if (root->key == target)
            return true;
        if (findPath(root->left, target, path) || findPath(root->right, target, path))
            return true;
        path.pop_back();
        return false;
    }
};




#include <iostream>
#include <vector>
#include <limits>
#include <queue>

using namespace std;

// Структура для ребра графа
struct Edge {
    int destination; // вершина назначения
    int weight;      // вес ребра
    Edge(int d, int w) : destination(d), weight(w) {}
};

// Алгоритм Дейкстры
vector<int> dijkstra(const vector<vector<Edge>>& graph, int start) {
    int n = graph.size();
    vector<int> distances(n, numeric_limits<int>::max());
    vector<bool> visited(n, false);

    distances[start] = 0;
    // Очередь с приоритетом
    priority_queue<pair<int, int>, vector<pair<int, int>>, greater<>> pq;
    pq.emplace(0, start);

    while (!pq.empty()) {
        auto [dist, v] = pq.top();
        pq.pop();
        if (visited[v]) continue;
        visited[v] = true;

        // Обход соседей
        for (const auto& edge : graph[v]) {
            int u = edge.destination;
            int w = edge.weight;
            if (dist + w < distances[u]) {
                distances[u] = dist + w;
                pq.emplace(distances[u], u);
            }
        }
    }
    return distances;
}

int main() {
    // Создание графа
    vector<vector<Edge>> graph(6);
    // Добавление ребер
    graph[0].push_back(Edge(1, 4));
    graph[0].push_back(Edge(2, 7));
    graph[1].push_back(Edge(2, 1));
    graph[1].push_back(Edge(3, 2));
    graph[2].push_back(Edge(3, 3));
    graph[3].push_back(Edge(4, 1));
    graph[4].push_back(Edge(5, 2));

    vector<int> distances = dijkstra(graph, 0);
    cout << "Кратчайшие расстояния от вершины 0:" << endl;
    for (int i = 0; i < distances.size(); ++i) {
        cout << "До " << i << " = " << distances[i] << endl;
    }
    return 0;
}
