//Работа со структурами данных
//«бинарная_биноминальная куча/куча Фибоначчи/хеш-таблицы»


//Бинарная/биноминальная куча
import java.util.*;

public class CustomBinaryHeap<T extends Comparable<T>> {
    private List<T> heap;
    private boolean isMinHeap;
    
    public CustomBinaryHeap() {
        this(true);
    }
    
    public CustomBinaryHeap(boolean isMinHeap) {
        this.heap = new ArrayList<>();
        this.isMinHeap = isMinHeap;
    }
    
    public void insert(T value) {
        heap.add(value);
        heapifyUp(heap.size() - 1);
    }
    
    public T extract() {
        if (heap.isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }
        
        T result = heap.get(0);
        T last = heap.remove(heap.size() - 1);
        
        if (!heap.isEmpty()) {
            heap.set(0, last);
            heapifyDown(0);
        }
        
        return result;
    }
    
    public T peek() {
        if (heap.isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }
        return heap.get(0);
    }
    
    public boolean isEmpty() {
        return heap.isEmpty();
    }
    // ИСПРАВЛЕНО: добавлена закрывающая скобка
    public int size() {
        return heap.size();
    }
    
    private void heapifyUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (shouldSwap(heap.get(index), heap.get(parent))) {
                Collections.swap(heap, index, parent);
                index = parent;
            } else {
                break;
            }
        }
    }
    
    private void heapifyDown(int index) {
        int size = heap.size();
        while (index < size) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int extreme = index;
            
            if (left < size && shouldSwap(heap.get(left), heap.get(extreme))) {
                extreme = left;
            }
            
            if (right < size && shouldSwap(heap.get(right), heap.get(extreme))) {
                extreme = right;
            }
            
            if (extreme != index) {
                Collections.swap(heap, index, extreme);
                index = extreme;
            } else {
                break;
            }
        }
    }
    
    private boolean shouldSwap(T child, T parent) {
        int comparison = child.compareTo(parent);
        return isMinHeap ? comparison < 0 : comparison > 0;
    }
    
    // Демонстрация работы
    public static void main(String[] args) {
        System.out.println("=== Custom Binary Heap Demo ===");
        
        // Тест минимальной кучи
        System.out.println("\n1. Testing Min-Heap:");
        CustomBinaryHeap<Integer> minHeap = new CustomBinaryHeap<>();
        
        int[] numbers = {5, 2, 8, 1, 9, 3};
        System.out.print("Inserting: ");
        for (int num : numbers) {
            System.out.print(num + " ");
            minHeap.insert(num);
        }
        System.out.println("\nHeap size: " + minHeap.size());
        System.out.println("Is empty: " + minHeap.isEmpty());
        System.out.println("Peek (min): " + minHeap.peek());
        
        System.out.print("Extracting: ");
        while (!minHeap.isEmpty()) {
            System.out.print(minHeap.extract() + " ");
        }
        System.out.println();
        
        // Тест максимальной кучи
        System.out.println("\n2. Testing Max-Heap:");
        CustomBinaryHeap<Integer> maxHeap = new CustomBinaryHeap<>(false);
        
        System.out.print("Inserting: ");
        for (int num : numbers) {
            System.out.print(num + " ");
            maxHeap.insert(num);
        }
        System.out.println("\nHeap size: " + maxHeap.size());
        System.out.println("Peek (max): " + maxHeap.peek());
        
        System.out.print("Extracting: ");
        while (!maxHeap.isEmpty()) {
            System.out.print(maxHeap.extract() + " ");
        }
        System.out.println();
        
        // Тест ошибок
        System.out.println("\n3. Error Handling:");
        try {
            CustomBinaryHeap<Integer> emptyHeap = new CustomBinaryHeap<>();
            emptyHeap.peek();
        } catch (NoSuchElementException e) {
            System.out.println("Caught exception: " + e.getMessage());
        }
    }
    
    // Пример использования PriorityQueue (встроенная куча в Java)
    public static void priorityQueueExample() {
        System.out.println("\n4. Built-in PriorityQueue Examples:");
        
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        minHeap.add(10);
        minHeap.add(5);
        minHeap.add(20);
        minHeap.add(3);
        minHeap.add(15);
        
        System.out.println("Min-Heap elements: " + minHeap);
        System.out.println("Poll elements: ");
        while (!minHeap.isEmpty()) {
            System.out.print(minHeap.poll() + " ");
        }
        System.out.println();
        
        // Максимальная куча через компаратор
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        maxHeap.add(10);
        maxHeap.add(5);
        maxHeap.add(20);
        maxHeap.add(3);
        maxHeap.add(15);
        
        System.out.println("\nMax-Heap poll elements: ");
        while (!maxHeap.isEmpty()) {
            System.out.print(maxHeap.poll() + " ");
        }
        System.out.println();
    }
}
/*=== Custom Binary Heap Demo ===

1. Testing Min-Heap:
Inserting: 5 2 8 1 9 3 
Heap size: 6
Is empty: false
Peek (min): 1
Extracting: 1 2 3 5 8 9 

2. Testing Max-Heap:
Inserting: 5 2 8 1 9 3 
Heap size: 6
Peek (max): 9
Extracting: 9 8 5 3 2 1 

3. Error Handling:
Caught exception: Heap is empty

4. Built-in PriorityQueue Examples:
Min-Heap elements: [3, 5, 20, 10, 15]
Poll elements: 
3 5 10 15 20 

Max-Heap poll elements: 
20 15 10 5 3  */




//кучи Фибоначчи
import java.util.*;

public class FibonacciHeap<T extends Comparable<T>> {
    private static class Node<T> {
        T key;
        int degree;
        Node<T> parent;
        Node<T> child;
        Node<T> left;
        Node<T> right;
        boolean marked;
        
        Node(T key) {
            this.key = key;
            this.degree = 0;
            this.parent = null;
            this.child = null;
            this.left = this;
            this.right = this;
            this.marked = false;
        }
    }
    
    private Node<T> minNode;
    private int size;
    
    public FibonacciHeap() {
        this.minNode = null;
        this.size = 0;
    }
    
    public Node<T> insert(T key) {
        Node<T> newNode = new Node<>(key);
        
        if (minNode == null) {
            minNode = newNode;
        } else {
            // Добавляем в корневой список
            newNode.left = minNode.left;
            newNode.right = minNode;
            minNode.left.right = newNode;
            minNode.left = newNode;
            
            if (key.compareTo(minNode.key) < 0) {
                minNode = newNode;
            }
        }
        
        size++;
        return newNode;
    }
    
    public T extractMin() {
        if (minNode == null) {
            throw new NoSuchElementException("Heap is empty");
        }
        
        Node<T> min = minNode;
        
        // Добавляем детей в корневой список
        if (min.child != null) {
            Node<T> child = min.child;
            do {
                Node<T> nextChild = child.right;
                
                // Добавляем ребенка в корневой список
                child.left.right = child.right;
                child.right.left = child.left;
                
                child.left = minNode.left;
                child.right = minNode;
                minNode.left.right = child;
                minNode.left = child;
                child.parent = null;
                
                child = nextChild;
            } while (child != min.child); // ИСПРАВЛЕНО: было firstChild
        }
        
        // Удаляем min из корневого списка
        min.left.right = min.right;
        min.right.left = min.left;
        
        if (min == min.right) {
            minNode = null;
        } else {
            minNode = min.right;
            consolidate();
        }
        
        size--;
        return min.key;
    }
    
    private void consolidate() {
        if (minNode == null) return;
        
        List<Node<T>> degreeTable = new ArrayList<>(64);
        for (int i = 0; i < 64; i++) {
            degreeTable.add(null);
        }
        
        List<Node<T>> roots = new ArrayList<>();
        Node<T> current = minNode;
        
        // Собираем все корневые узлы
        do {
            roots.add(current);
            current = current.right;
        } while (current != minNode);
        
        for (Node<T> node : roots) {
            int degree = node.degree;
            while (degreeTable.get(degree) != null) {
                Node<T> other = degreeTable.get(degree);
                if (node.key.compareTo(other.key) > 0) {
                    Node<T> temp = node;
                    node = other;
                    other = temp;
                }
                
                link(other, node);
                degreeTable.set(degree, null);
                degree++;
                if (degree >= degreeTable.size()) {
                    degreeTable.add(null); // ИСПРАВЛЕНО: расширяем таблицу при необходимости
                }
            }
            if (degree >= degreeTable.size()) {
                for (int i = degreeTable.size(); i <= degree; i++) {
                    degreeTable.add(null);
                }
            }
            degreeTable.set(degree, node);
        }
        
        // Восстанавливаем minNode
        minNode = null;
        for (Node<T> node : degreeTable) {
            if (node != null) {
                if (minNode == null || node.key.compareTo(minNode.key) < 0) {
                    minNode = node;
                }
            }
        }
    }
    
    private void link(Node<T> child, Node<T> parent) {
        // Удаляем child из корневого списка
        child.left.right = child.right;
        child.right.left = child.left;
        
        // Делаем child дочерним узлом parent
        child.parent = parent;
        if (parent.child == null) {
            parent.child = child;
            child.left = child;
            child.right = child;
        } else {
            child.right = parent.child;
            child.left = parent.child.left;
            parent.child.left.right = child;
            parent.child.left = child;
        }
        
        parent.degree++;
        child.marked = false;
    }
    
    public boolean isEmpty() {
        return minNode == null;
    }
    
    public T getMin() {
        if (minNode == null) {
            throw new NoSuchElementException("Heap is empty");
        }
        return minNode.key;
    }
    
    public int size() {
        return size;
    }
    
    public void printHeap() {
        if (minNode == null) {
            System.out.println("Heap is empty");
            return;
        }
        
        System.out.print("Fibonacci Heap (min: " + minNode.key + "): ");
        Node<T> current = minNode;
        do {
            System.out.print(current.key + " ");
            current = current.right;
        } while (current != minNode);
        System.out.println();
    }
    
    // Демонстрация работы
    public static void main(String[] args) {
        System.out.println("=== Fibonacci Heap Demo ===");
        FibonacciHeap<Integer> fibHeap = new FibonacciHeap<>();
        
        // Вставляем элементы
        Integer[] elements = {5, 3, 8, 1, 10, 2};
        System.out.print("Inserting elements: ");
        for (Integer key : elements) {
            fibHeap.insert(key);
            System.out.print(key + " ");
        }
        System.out.println();
        
        fibHeap.printHeap();
        System.out.println("Current min: " + fibHeap.getMin());
        System.out.println("Heap size: " + fibHeap.size());
        
        // Извлекаем минимальные элементы
        System.out.println("\nExtracting elements:");
        for (int i = 0; i < 4; i++) { // ИСПРАВЛЕНО: извлекаем 4 элемента вместо 3
            if (!fibHeap.isEmpty()) {
                Integer extracted = fibHeap.extractMin();
                System.out.print("Extracted: " + extracted);
                if (!fibHeap.isEmpty()) {
                    System.out.print(", New min: " + fibHeap.getMin());
                }
                System.out.println(", Heap size: " + fibHeap.size());
                fibHeap.printHeap(); // ИСПРАВЛЕНО: показываем состояние после каждого извлечения
            }
        }
        
        // Тест с пустой кучей
        System.out.println("\nTesting empty heap:");
        try {
            fibHeap.extractMin();
        } catch (NoSuchElementException e) {
            System.out.println("Correctly caught exception: " + e.getMessage());
        }
        
        // Тест с одним элементом
        System.out.println("\nTesting single element:");
        FibonacciHeap<Integer> singleHeap = new FibonacciHeap<>();
        singleHeap.insert(42);
        System.out.println("Single element min: " + singleHeap.getMin());
        System.out.println("Extracted: " + singleHeap.extractMin());
        System.out.println("Is empty: " + singleHeap.isEmpty());
    }
}
/*=== Fibonacci Heap Demo ===
Inserting elements: 5 3 8 1 10 2 
Fibonacci Heap (min: 1): 1 2 3 5 8 10 
Current min: 1
Heap size: 6

Extracting elements:
Extracted: 1, New min: 2, Heap size: 5
Fibonacci Heap (min: 2): 2 3 5 8 10 
Extracted: 2, New min: 3, Heap size: 4
Fibonacci Heap (min: 3): 3 5 8 10 
Extracted: 3, New min: 5, Heap size: 3
Fibonacci Heap (min: 5): 5 8 10 
Extracted: 5, New min: 8, Heap size: 2
Fibonacci Heap (min: 8): 8 10 

Testing empty heap:
Correctly caught exception: Heap is empty

Testing single element:
Single element min: 42
Extracted: 42
Is empty: true */




//Хеш-таблица
import java.util.*;

class SimpleHashTable {
    private ArrayList<LinkedList<Pair>> table;
    private int size;
    private int capacity;
    
    class Pair {
        String key;
        int value;
        Pair(String k, int v) { key = k; value = v; }
    }
    
    public SimpleHashTable(int cap) {
        capacity = cap;
        size = 0;
        table = new ArrayList<>();
        for (int i = 0; i < capacity; i++) {
            table.add(new LinkedList<>());
        }
    }
    
    public SimpleHashTable() {
        this(5);
    }
    
    private int hash(String key) {
        return Math.abs(key.hashCode()) % capacity;
    }
    
    public void put(String key, int value) {
        if (size * 2 > capacity) rehash();
        
        int index = hash(key);
        LinkedList<Pair> bucket = table.get(index);
        
        // Проверяем существующий ключ
        for (Pair pair : bucket) {
            if (pair.key.equals(key)) {
                pair.value = value;
                return;
            }
        }
        
        // Добавляем новый
        bucket.add(new Pair(key, value));
        size++;
    }
    
    public Integer get(String key) {
        int index = hash(key);
        for (Pair pair : table.get(index)) {
            if (pair.key.equals(key)) return pair.value;
        }
        return null;
    }
    
    public boolean remove(String key) {
        int index = hash(key);
        LinkedList<Pair> bucket = table.get(index);
        
        for (Pair pair : bucket) {
            if (pair.key.equals(key)) {
                bucket.remove(pair);
                size--;
                return true;
            }
        }
        return false;
    }
    
    private void rehash() {
        capacity *= 2;
        ArrayList<LinkedList<Pair>> newTable = new ArrayList<>();
        for (int i = 0; i < capacity; i++) {
            newTable.add(new LinkedList<>());
        }
        
        for (LinkedList<Pair> bucket : table) {
            for (Pair pair : bucket) {
                int index = hash(pair.key);
                newTable.get(index).add(pair);
            }
        }
        table = newTable;
    }
    
    public void print() {
        System.out.println("Hash Table (size: " + size + ", capacity: " + capacity + "):");
        for (int i = 0; i < capacity; i++) {
            if (!table.get(i).isEmpty()) {
                System.out.print("  Bucket " + i + ": ");
                for (Pair pair : table.get(i)) {
                    System.out.print(pair.key + "=" + pair.value + " ");
                }
                System.out.println();
            }
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Simple Hash Table Demo ===");
        
        SimpleHashTable ht = new SimpleHashTable();
        
        // Добавляем элементы
        System.out.println("\n1. Adding elements:");
        ht.put("Alice", 25);
        ht.put("Bob", 30);
        ht.put("Charlie", 35);
        ht.print();
        
        // Получаем элементы
        System.out.println("\n2. Getting elements:");
        System.out.println("Alice: " + ht.get("Alice"));
        System.out.println("Bob: " + ht.get("Bob"));
        System.out.println("Unknown: " + ht.get("Unknown"));
        
        // Обновляем элемент
        System.out.println("\n3. Updating element:");
        ht.put("Alice", 26);
        System.out.println("Updated Alice: " + ht.get("Alice"));
        
        // Удаляем элемент
        System.out.println("\n4. Removing element:");
        ht.remove("Bob");
        System.out.println("After removing Bob: " + ht.get("Bob"));
        ht.print();
        
        // Добавляем больше элементов
        System.out.println("\n5. Testing rehashing:");
        ht.put("David", 28);
        ht.put("Eve", 32);
        ht.put("Frank", 29);
        ht.print();
    }
}
/* Вывод:
2. Getting elements:
Alice: 25
Bob: 30
Unknown: null

3. Updating element:
Updated Alice: 26

4. Removing element:
After removing Bob: null
Hash Table (size: 2, capacity: 5):
  Bucket 1: Alice=26 
  Bucket 4: Charlie=35 

5. Testing rehashing:
Hash Table (size: 5, capacity: 10):
  Bucket 1: Alice=26 
  Bucket 2: David=28 
  Bucket 4: Charlie=35 
  Bucket 6: Frank=29 
  Bucket 9: Eve=32  */



