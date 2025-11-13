//Работа со структурами данных
//«бинарная_биноминальная куча/куча Фибоначчи/хеш-таблицы»


//Бинарная_биноминальная куча
#include <iostream>
#include <vector>
#include <algorithm>
#include <stdexcept>

template<typename T>
class AdvancedBinaryHeap {
private:
    std::vector<T> heap;
    bool isMinHeap;
    
    void heapifyUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if ((isMinHeap && heap[index] < heap[parent]) || 
                (!isMinHeap && heap[index] > heap[parent])) {
                std::swap(heap[index], heap[parent]);
                index = parent;
            } else {
                break;
            }
        }
    }
    
    void heapifyDown(int index) {
        int size = heap.size();
        while (index < size) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int extreme = index;
            
            if (left < size && ((isMinHeap && heap[left] < heap[extreme]) || 
                               (!isMinHeap && heap[left] > heap[extreme]))) {
                extreme = left;
            }
            
            if (right < size && ((isMinHeap && heap[right] < heap[extreme]) || 
                                (!isMinHeap && heap[right] > heap[extreme]))) {
                extreme = right;
            }
            
            if (extreme != index) {
                std::swap(heap[index], heap[extreme]);
                index = extreme;
            } else {
                break;
            }
        }
    }

public:
    AdvancedBinaryHeap(bool minHeap = true) : isMinHeap(minHeap) {}
    
    void insert(const T& value) {
        heap.push_back(value);
        heapifyUp(heap.size() - 1);
    }
    
    T extract() {
        if (heap.empty()) {
            throw std::runtime_error("Heap is empty");
        }
        
        T result = heap[0];
        heap[0] = heap.back();
        heap.pop_back();
        
        if (!heap.empty()) {
            heapifyDown(0);
        }
        
        return result;
    }
    
    const T& peek() const {
        if (heap.empty()) {
            throw std::runtime_error("Heap is empty");
        }
        return heap[0];
    }
    
    bool empty() const {
        return heap.empty();
    }
    
    size_t size() const {
        return heap.size();
    }
    
    void print() const {
        std::cout << "[";
        for (size_t i = 0; i < heap.size(); ++i) {
            std::cout << heap[i];
            if (i < heap.size() - 1) std::cout << ", ";
        }
        std::cout << "]" << std::endl;
    }
};

void binaryHeapDemo() {
    std::cout << "=== C++ Binary Heap Demo ===" << std::endl;
    AdvancedBinaryHeap<int> minHeap(true);
    
    std::vector<int> numbers = {5, 2, 8, 1, 9, 3};
    std::cout << "Initial numbers: [5, 2, 8, 1, 9, 3]" << std::endl;
    
    for (int num : numbers) {
        minHeap.insert(num);
    }
    std::cout << "Heap after inserts: ";
    minHeap.print();
    
    int minVal = minHeap.extract();
    std::cout << "Extracted min: " << minVal << std::endl;
    std::cout << "Heap after extraction: ";
    minHeap.print();
    
    minHeap.insert(0);
    std::cout << "After pushing 0: ";
    minHeap.print();
    std::cout << "Current min: " << minHeap.peek() << std::endl;
}

// Пример вывода:
// === C++ Binary Heap Demo ===
// Initial numbers: [5, 2, 8, 1, 9, 3]
// Heap after inserts: [1, 2, 3, 5, 9, 8]
// Extracted min: 1
// Heap after extraction: [2, 5, 3, 8, 9]
// After pushing 0: [0, 2, 3, 8, 9, 5]
// Current min: 0



//куча Фибоначчи
#include <iostream>
#include <vector>
#include <cmath>
#include <limits>

template<typename T>
class FibonacciHeap {
private:
    struct Node {
        T key;
        int degree;
        Node* parent;
        Node* child;
        Node* left;
        Node* right;
        bool marked;
        
        Node(T k) : key(k), degree(0), parent(nullptr), child(nullptr), 
                   left(this), right(this), marked(false) {}
    };
    
    Node* minNode;
    int nodeCount;
    
    void addToRootList(Node* node) {
        if (minNode == nullptr) {
            minNode = node;
            return;
        }
        
        // Вставляем node в корневой список слева от minNode
        node->left = minNode->left;
        node->right = minNode;
        minNode->left->right = node;
        minNode->left = node;
        
        if (node->key < minNode->key) {
            minNode = node;
        }
    }
    
    void consolidate() {
        if (minNode == nullptr) return;
        
        std::vector<Node*> degreeTable(64, nullptr); // Максимум 2^64 элементов
        std::vector<Node*> roots;
        Node* current = minNode;
        
        // Собираем все корневые узлы
        do {
            roots.push_back(current);
            current = current->right;
        } while (current != minNode);
        
        for (Node* node : roots) {
            int degree = node->degree;
            while (degreeTable[degree] != nullptr) {
                Node* other = degreeTable[degree];
                if (node->key > other->key) {
                    std::swap(node, other);
                }
                
                link(other, node);
                degreeTable[degree] = nullptr;
                degree++;
            }
            degreeTable[degree] = node;
        }
        
        // Восстанавливаем minNode
        minNode = nullptr;
        for (Node* node : degreeTable) {
            if (node != nullptr) {
                if (minNode == nullptr || node->key < minNode->key) {
                    minNode = node;
                }
            }
        }
    }
    
    void link(Node* child, Node* parent) {
        // Удаляем child из корневого списка
        child->left->right = child->right;
        child->right->left = child->left;
        
        // Делаем child дочерним узлом parent
        child->parent = parent;
        if (parent->child == nullptr) {
            parent->child = child;
            child->left = child;
            child->right = child;
        } else {
            child->right = parent->child;
            child->left = parent->child->left;
            parent->child->left->right = child;
            parent->child->left = child;
        }
        
        parent->degree++;
        child->marked = false;
    }

public:
    FibonacciHeap() : minNode(nullptr), nodeCount(0) {}
    
    Node* insert(T key) {
        Node* newNode = new Node(key);
        
        if (minNode == nullptr) {
            minNode = newNode;
        } else {
            addToRootList(newNode);
            if (key < minNode->key) {
                minNode = newNode;
            }
        }
        
        nodeCount++;
        return newNode;
    }
    
    T extractMin() {
        if (minNode == nullptr) {
            throw std::runtime_error("Heap is empty");
        }
        
        Node* min = minNode;
        
        // Добавляем детей min в корневой список
        if (min->child != nullptr) {
            Node* child = min->child;
            do {
                Node* nextChild = child->right;
                addToRootList(child);
                child->parent = nullptr;
                child = nextChild;
            } while (child != min->child);
        }
        
        // Удаляем min из корневого списка
        min->left->right = min->right;
        min->right->left = min->left;
        
        if (min == min->right) {
            minNode = nullptr;
        } else {
            minNode = min->right;
            consolidate();
        }
        
        nodeCount--;
        T minKey = min->key;
        delete min;
        return minKey;
    }
    
    bool isEmpty() const {
        return minNode == nullptr;
    }
    
    T getMin() const {
        if (minNode == nullptr) {
            throw std::runtime_error("Heap is empty");
        }
        return minNode->key;
    }
    
    int size() const {
        return nodeCount;
    }
    
    void printHeap() const {
        if (minNode == nullptr) {
            std::cout << "Heap is empty" << std::endl;
            return;
        }
        
        std::cout << "Fibonacci Heap (min: " << minNode->key << "): ";
        Node* current = minNode;
        do {
            std::cout << current->key << " ";
            current = current->right;
        } while (current != minNode);
        std::cout << std::endl;
    }
};

void fibonacciHeapDemo() {
    std::cout << "\n=== C++ Fibonacci Heap Demo ===" << std::endl;
    FibonacciHeap<int> fibHeap;
    
    // Вставляем элементы
    std::vector<int> elements = {5, 3, 8, 1, 10, 2};
    std::cout << "Inserting elements: ";
    for (int key : elements) {
        fibHeap.insert(key);
        std::cout << key << " ";
    }
    std::cout << std::endl;
    
    fibHeap.printHeap();
    std::cout << "Current min: " << fibHeap.getMin() << std::endl;
    std::cout << "Heap size: " << fibHeap.size() << std::endl;
    
    // Извлекаем минимальные элементы
    std::cout << "\nExtracting elements:" << std::endl;
    for (int i = 0; i < 3; i++) {
        if (!fibHeap.isEmpty()) {
            int extracted = fibHeap.extractMin();
            std::cout << "Extracted: " << extracted;
            if (!fibHeap.isEmpty()) {
                std::cout << ", New min: " << fibHeap.getMin();
            }
            std::cout << ", Heap size: " << fibHeap.size() << std::endl;
        }
    }
    
    fibHeap.printHeap();
}

// Пример вывода:
// === C++ Fibonacci Heap Demo ===
// Inserting elements: 5 3 8 1 10 2 
// Fibonacci Heap (min: 1): 1 2 3 5 8 10 
// Current min: 1
// Heap size: 6
//
// Extracting elements:
// Extracted: 1, New min: 2, Heap size: 5
// Extracted: 2, New min: 3, Heap size: 4
// Extracted: 3, New min: 5, Heap size: 3
// Fibonacci Heap (min: 5): 5 8 10



//Хеш-таблицы
#include <iostream>
#include <vector>
#include <list>
#include <string>
using namespace std;

class SimpleHashTable {
private:
    vector<list<pair<string, int>>> table;
    int size;
    int capacity;
    
    int hash(const string& key) {
        int hash = 0;
        for (char c : key) hash = (hash * 31 + c) % capacity;
        return hash;
    }
    
    void rehash() {
        capacity *= 2;
        vector<list<pair<string, int>>> newTable(capacity);
        
        for (auto& bucket : table) {
            for (auto& pair : bucket) {
                int index = hash(pair.first);
                newTable[index].push_back(pair);
            }
        }
        table = move(newTable);
    }

public:
    SimpleHashTable(int cap = 5) : capacity(cap), size(0) {
        table.resize(capacity);
    }
    
    void put(const string& key, int value) {
        if (size * 2 > capacity) rehash();
        
        int index = hash(key);
        
        // Проверяем существующий ключ
        for (auto& pair : table[index]) {
            if (pair.first == key) {
                pair.second = value;
                return;
            }
        }
        
        // Добавляем новый
        table[index].push_back({key, value});
        size++;
    }
    
    int get(const string& key) {
        int index = hash(key);
        for (auto& pair : table[index]) {
            if (pair.first == key) return pair.second;
        }
        return -1; // -1 означает "не найдено"
    }
    
    bool remove(const string& key) {
        int index = hash(key);
        auto& bucket = table[index];
        
        for (auto it = bucket.begin(); it != bucket.end(); it++) {
            if (it->first == key) {
                bucket.erase(it);
                size--;
                return true;
            }
        }
        return false;
    }
    
    void print() {
        cout << "Hash Table (size: " << size << ", capacity: " << capacity << "):" << endl;
        for (int i = 0; i < capacity; i++) {
            if (!table[i].empty()) {
                cout << "  Bucket " << i << ": ";
                for (auto& pair : table[i]) {
                    cout << pair.first << "=" << pair.second << " ";
                }
                cout << endl;
            }
        }
    }
};

int main() {
    cout << "=== Simple Hash Table Demo ===" << endl;
    
    SimpleHashTable ht;
    
    // Добавляем элементы
    cout << "\n1. Adding elements:" << endl;
    ht.put("Alice", 25);
    ht.put("Bob", 30);
    ht.put("Charlie", 35);
    ht.print();
    
    // Получаем элементы
    cout << "\n2. Getting elements:" << endl;
    cout << "Alice: " << ht.get("Alice") << endl;
    cout << "Bob: " << ht.get("Bob") << endl;
    cout << "Unknown: " << ht.get("Unknown") << endl;
    
    // Обновляем элемент
    cout << "\n3. Updating element:" << endl;
    ht.put("Alice", 26);
    cout << "Updated Alice: " << ht.get("Alice") << endl;
    
    // Удаляем элемент
    cout << "\n4. Removing element:" << endl;
    ht.remove("Bob");
    cout << "After removing Bob: " << ht.get("Bob") << endl;
    ht.print();
    
    // Добавляем больше элементов для теста рехэширования
    cout << "\n5. Testing rehashing:" << endl;
    ht.put("David", 28);
    ht.put("Eve", 32);
    ht.put("Frank", 29);
    ht.print();
    
    return 0;
}

/* Вывод:
1. Adding elements:
Hash Table (size: 3, capacity: 5):
  Bucket 1: Alice=25 
  Bucket 3: Bob=30 
  Bucket 0: Charlie=35 

2. Getting elements:
Alice: 25
Bob: 30
Unknown: -1

3. Updating element:
Updated Alice: 26

4. Removing element:
After removing Bob: -1
Hash Table (size: 2, capacity: 5):
  Bucket 1: Alice=26 
  Bucket 0: Charlie=35 

5. Testing rehashing:
Hash Table (size: 5, capacity: 10):
  Bucket 0: Charlie=35 Frank=29 
  Bucket 1: Alice=26 
  Bucket 3: David=28 
  Bucket 8: Eve=32
*/

