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