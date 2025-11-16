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