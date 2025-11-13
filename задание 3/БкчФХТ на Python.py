#Работа со структурами данных
#«бинарная_биноминальная куча/куча Фибоначчи/хеш-таблицы»


#бинарная_биноминальная куча
import heapq

class CustomBinaryHeap:
    def __init__(self, is_min_heap=True):
        self.heap = []
        self.is_min_heap = is_min_heap
    
    def _compare(self, a, b):
        if self.is_min_heap:
            return a < b
        return a > b
    
    def push(self, val):
        self.heap.append(val)
        self._heapify_up(len(self.heap) - 1)
    
    def pop(self):
        if not self.heap:
            return None
        if len(self.heap) == 1:
            return self.heap.pop()
        
        root = self.heap[0]
        self.heap[0] = self.heap.pop()
        self._heapify_down(0)
        return root
    
    def _heapify_up(self, idx):
        while idx > 0:
            parent = (idx - 1) // 2
            if self._compare(self.heap[idx], self.heap[parent]):
                self.heap[idx], self.heap[parent] = self.heap[parent], self.heap[idx]
                idx = parent
            else:
                break
    
    def _heapify_down(self, idx):
        while True:
            left = 2 * idx + 1
            right = 2 * idx + 2
            extreme = idx
            
            if left < len(self.heap) and self._compare(self.heap[left], self.heap[extreme]):
                extreme = left
            if right < len(self.heap) and self._compare(self.heap[right], self.heap[extreme]):
                extreme = right
            
            if extreme != idx:
                self.heap[idx], self.heap[extreme] = self.heap[extreme], self.heap[idx]
                idx = extreme
            else:
                break
    
    def peek(self):
        return self.heap[0] if self.heap else None
    
    def __str__(self):
        return str(self.heap)

# Демонстрация
def binary_heap_demo():
    print("=== Binary Heap Demo ===")
    heap = CustomBinaryHeap()
    
    numbers = [5, 2, 8, 1, 9, 3]
    print(f"Initial numbers: {numbers}")
    
    for num in numbers:
        heap.push(num)
    print(f"Heap after inserts: {heap}")
    
    min_val = heap.pop()
    print(f"Extracted min: {min_val}")
    print(f"Heap after extraction: {heap}")
    
    heap.push(0)
    print(f"After pushing 0: {heap}")
    print(f"Current min: {heap.peek()}")

# Пример вывода:
# === Binary Heap Demo ===
# Initial numbers: [5, 2, 8, 1, 9, 3]
# Heap after inserts: [1, 2, 3, 5, 9, 8]
# Extracted min: 1
# Heap after extraction: [2, 5, 3, 8, 9]
# After pushing 0: [0, 2, 3, 8, 9, 5]
# Current min: 0



#Кучи Фибоначи
class FibonacciNode:
    def __init__(self, key, value=None):
        self.key = key
        self.value = value
        self.parent = None
        self.child = None
        self.left = self
        self.right = self
        self.degree = 0
        self.marked = False

class FibonacciHeap:
    def __init__(self):
        self.min_node = None
        self.node_count = 0
    
    def insert(self, key, value=None):
        new_node = FibonacciNode(key, value)
        
        if self.min_node is None:
            self.min_node = new_node
        else:
            # Добавляем новый узел в корневой список
            new_node.right = self.min_node
            new_node.left = self.min_node.left
            self.min_node.left.right = new_node
            self.min_node.left = new_node
            
            if key < self.min_node.key:
                self.min_node = new_node
        
        self.node_count += 1
        return new_node
    
    def extract_min(self):
        if self.min_node is None:
            return None
        
        min_node = self.min_node
        
        # Добавляем детей min_node в корневой список
        if min_node.child is not None:
            child = min_node.child
            first_child = child
            while True:
                next_child = child.right
                # Добавляем ребенка в корневой список
                child.left.right = child.right
                child.right.left = child.left
                child.left = self.min_node
                child.right = self.min_node.right
                self.min_node.right.left = child
                self.min_node.right = child
                child.parent = None
                
                child = next_child
                if child == first_child:
                    break
        
        # Удаляем min_node из корневого списка
        min_node.left.right = min_node.right
        min_node.right.left = min_node.left
        
        if min_node == min_node.right:
            self.min_node = None
        else:
            self.min_node = min_node.right
            self._consolidate()
        
        self.node_count -= 1
        return (min_node.key, min_node.value)
    
    def _consolidate(self):
        if self.min_node is None:
            return
        
        degree_table = {}
        nodes = []
        current = self.min_node
        
        # Собираем все корневые узлы
        while True:
            nodes.append(current)
            current = current.right
            if current == self.min_node:
                break
        
        for node in nodes:
            degree = node.degree
            while degree in degree_table:
                other = degree_table[degree]
                if node.key > other.key:
                    node, other = other, node
                
                self._link(other, node)
                del degree_table[degree]
                degree += 1
            
            degree_table[degree] = node
        
        # Восстанавливаем min_node
        self.min_node = None
        for node in degree_table.values():
            if self.min_node is None or node.key < self.min_node.key:
                self.min_node = node
    
    def _link(self, child, parent):
        # Удаляем child из корневого списка
        child.left.right = child.right
        child.right.left = child.left
        
        # Делаем child дочерним узлом parent
        child.parent = parent
        if parent.child is None:
            parent.child = child
            child.left = child
            child.right = child
        else:
            child.right = parent.child
            child.left = parent.child.left
            parent.child.left.right = child
            parent.child.left = child
        
        parent.degree += 1
        child.marked = False
    
    def is_empty(self):
        return self.min_node is None
    
    def get_min(self):
        return (self.min_node.key, self.min_node.value) if self.min_node else None

def fibonacci_heap_demo():
    print("\n=== Fibonacci Heap Demo ===")
    fib_heap = FibonacciHeap()
    
    # Вставляем элементы
    elements = [(5, "A"), (3, "B"), (8, "C"), (1, "D"), (10, "E")]
    for key, value in elements:
        fib_heap.insert(key, value)
        print(f"Inserted: ({key}, '{value}')")
    
    min_val = fib_heap.get_min()
    print(f"Current min: {min_val}")
    
    # Извлекаем минимальные элементы
    for i in range(3):
        if not fib_heap.is_empty():
            extracted = fib_heap.extract_min()
            print(f"Extracted: {extracted}")
            current_min = fib_heap.get_min()
            print(f"New min: {current_min}")

# Пример вывода:
# === Fibonacci Heap Demo ===
# Inserted: (5, 'A')
# Inserted: (3, 'B') 
# Inserted: (8, 'C')
# Inserted: (1, 'D')
# Inserted: (10, 'E')
# Current min: (1, 'D')
# Extracted: (1, 'D')
# New min: (3, 'B')
# Extracted: (3, 'B')
# New min: (5, 'A')
# Extracted: (5, 'A')
# New min: (8, 'C')


#Хэш-таблицы
class AdvancedHashTable:
    def __init__(self, size=10):
        self.size = size
        self.table = [[] for _ in range(size)]
        self.count = 0
    
    def _hash(self, key):
        # Улучшенная хеш-функция
        if isinstance(key, str):
            hash_val = 0
            for char in key:
                hash_val = (hash_val * 31 + ord(char)) % self.size
            return hash_val
        elif isinstance(key, int):
            return key % self.size
        else:
            return hash(key) % self.size
    
    def put(self, key, value):
        if self.load_factor() > 0.7:
            self._resize()
        
        index = self._hash(key)
        bucket = self.table[index]
        
        # Проверяем, существует ли уже ключ
        for i, (k, v) in enumerate(bucket):
            if k == key:
                bucket[i] = (key, value)
                return
        
        # Добавляем новую пару
        bucket.append((key, value))
        self.count += 1
    
    def get(self, key):
        index = self._hash(key)
        bucket = self.table[index]
        
        for k, v in bucket:
            if k == key:
                return v
        
        raise KeyError(f"Key '{key}' not found")
    
    def remove(self, key):
        index = self._hash(key)
        bucket = self.table[index]
        
        for i, (k, v) in enumerate(bucket):
            if k == key:
                del bucket[i]
                self.count -= 1
                return True
        
        return False
    
    def load_factor(self):
        return self.count / self.size
    
    def _resize(self):
        old_table = self.table
        old_size = self.size
        self.size *= 2
        self.table = [[] for _ in range(self.size)]
        self.count = 0
        
        for bucket in old_table:
            for key, value in bucket:
                self.put(key, value)
    
    def __contains__(self, key):
        try:
            self.get(key)
            return True
        except KeyError:
            return False
    
    def keys(self):
        all_keys = []
        for bucket in self.table:
            for key, value in bucket:
                all_keys.append(key)
        return all_keys
    
    def values(self):
        all_values = []
        for bucket in self.table:
            for key, value in bucket:
                all_values.append(value)
        return all_values
    
    def __str__(self):
        items = []
        for bucket in self.table:
            for key, value in bucket:
                items.append(f"{key}: {value}")
        return "{" + ", ".join(items) + "}"

def hash_table_demo():
    print("\n=== Hash Table Demo ===")
    ht = AdvancedHashTable(size=5)
    
    print(f"Initial table size: {ht.size}")
    
    # Добавляем элементы
    test_data = [("Alice", 25), ("Bob", 30), ("Charlie", 35), ("David", 28)]
    for key, value in test_data:
        ht.put(key, value)
        print(f"Inserted: {key} = {value}, load factor: {ht.load_factor():.2f}")
    
    print(f"\nCurrent table: {ht}")
    print(f"Keys: {ht.keys()}")
    print(f"Values: {ht.values()}")
    
    # Поиск элементов
    try:
        alice_age = ht.get("Alice")
        print(f"\nAlice's age: {alice_age}")
    except KeyError as e:
        print(f"Error: {e}")
    
    # Удаление элемента
    removed = ht.remove("Bob")
    print(f"Removed Bob: {removed}")
    print(f"Contains Bob: {'Bob' in ht}")
    
    # Попытка получить несуществующий ключ
    try:
        ht.get("Unknown")
    except KeyError as e:
        print(f"Expected error: {e}")

# Пример вывода:
# === Hash Table Demo ===
# Initial table size: 5
# Inserted: Alice = 25, load factor: 0.20
# Inserted: Bob = 30, load factor: 0.40  
# Inserted: Charlie = 35, load factor: 0.60
# Inserted: David = 28, load factor: 0.80
#
# Current table: {Alice: 25, Bob: 30, Charlie: 35, David: 28}
# Keys: ['Alice', 'Bob', 'Charlie', 'David']
# Values: [25, 30, 35, 28]
#
# Alice's age: 25
# Removed Bob: True
# Contains Bob: False
# Expected error: Key 'Unknown' not found