#Структуры данных. Работа со структурами данных «деревья/графы»



# Класс узла дерева
class Node:
    def __init__(self, key):
        self.key = key
        self.left = None
        self.right = None

# Функция вставки в дерево
def doInsert(node, key):
    if node is None:
        return Node(key)
    if key < node.key:
        node.left = doInsert(node.left, key)
    elif key > node.key:
        node.right = doInsert(node.right, key)
    # Если ключ уже есть - ничего не делаем
    return node

# Класс дерева
class Tree:
    def __init__(self):
        self.root = None

    def insert(self, key):
        self.root = doInsert(self.root, key)

    def getRoot(self):
        return self.root

    # Поиск пути от корня к целевому узлу
    @staticmethod
    def find_path(root, target):
        path = []

        def dfs(node):
            if node is None:
                return False
            path.append(node.key)
            if node.key == target:
                return True
            if dfs(node.left) or dfs(node.right):
                return True
            path.pop()
            return False

        found = dfs(root)
        if found:
            return path
        else:
            return None


#Графы. Алгоритм Дейкстры
import heapq

def dijkstra(graph, start):
    # Инициализация расстояний до всех вершин как бесконечность
    distances = {vertex: float('infinity') for vertex in graph}
    distances[start] = 0
    # Очередь с приоритетом
    queue = [(0, start)]

    while queue:
        current_distance, current_vertex = heapq.heappop(queue)
        # Если нашли более короткий путь — пропускаем
        if current_distance > distances[current_vertex]:
            continue
        # Проверяем всех соседей
        for neighbor, weight in graph[current_vertex].items():
            # Рассчитываем расстояние через текущую вершину
            distance = current_distance + weight
            # Обновляем, если нашли лучше
            if distance < distances[neighbor]:
                distances[neighbor] = distance
                heapq.heappush(queue, (distance, neighbor))
    return distances

# Пример вызыва
graph = {
    'A': {'B': 4, 'C': 7},
    'B': {'A': 4, 'C': 1, 'D': 2},
    'C': {'A': 7, 'B': 1, 'D': 3},
    'D': {'B': 2, 'C': 3, 'E': 1},
    'E': {'D': 1},
}

distances = dijkstra(graph, 'A')
print("Кратчайшие расстояния от A:", distances)
