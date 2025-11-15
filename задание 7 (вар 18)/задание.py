import random
from collections import deque

# Входные данные (Adjacency List)
# Граф с 12 вершинами, как указано в варианте 
V = 12
# Список ребер (произвольный, но соответствующий заданию)
EDGES = [
    (0, 1), (0, 4), (0, 5), (1, 2), (1, 6), (2, 3), (2, 7), 
    (3, 4), (3, 8), (4, 9), (5, 7), (5, 10), (6, 8), (6, 11), 
    (7, 9), (8, 10), (9, 11), (10, 11)
]

# Параметры из варианта 18
TABU_SIZE = 10         # 
MAX_ITERATIONS = 500   # 

def build_adj_list(v_count, edges):
    """Строит список смежности из списка ребер."""
    adj = [[] for _ in range(v_count)]
    for u, v in edges:
        adj[u].append(v)
        adj[v].append(u)
    return adj

def count_conflicts(adj, coloring):
    """Подсчитывает количество конфликтов (ребер с одинаковыми цветами)."""
    conflicts = 0
    for u in range(len(adj)):
        for v in adj[u]:
            # u < v чтобы не считать ребра дважды
            if u < v and coloring[u] == coloring[v]:
                conflicts += 1
    return conflicts

def tabu_search_k_coloring(adj, k):
    """
    Выполняет Табу-поиск для k-раскраски.
    """
    v_count = len(adj)
    
    # 1. Инициализация: случайная раскраска
    current_coloring = [random.randint(0, k - 1) for _ in range(v_count)]
    current_conflicts = count_conflicts(adj, current_coloring)
    
    best_coloring = list(current_coloring)
    best_conflicts = current_conflicts
    
    # Табу-лист хранит (vertex, old_color)
    tabu_list = deque(maxlen=TABU_SIZE) 

    for _ in range(MAX_ITERATIONS):
        
        # Критерий остановки: решение найдено
        if best_conflicts == 0:
            break 

        best_move = None
        best_move_conflicts = float('inf')
        
        best_aspirating_move = None
        best_aspirating_conflicts = float('inf')

        # 2. Поиск в окрестности (все V * (k-1) ходов)
        for v in range(v_count):
            old_color = current_coloring[v]
            for new_color in range(k):
                if new_color == old_color:
                    continue

                # Оценить ход (выполнить и откатить)
                current_coloring[v] = new_color
                new_conflicts = count_conflicts(adj, current_coloring)
                current_coloring[v] = old_color # Вернуть обратно

                is_tabu = (v, new_color) in tabu_list
                
                # 3. Выбор лучшего хода
                
                # Критерий аспирации 
                if new_conflicts < best_conflicts:
                    if new_conflicts < best_aspirating_conflicts:
                        best_aspirating_move = (v, new_color, old_color)
                        best_aspirating_conflicts = new_conflicts

                # Выбор лучшего не-табу хода
                if not is_tabu and new_conflicts < best_move_conflicts:
                    best_move = (v, new_color, old_color)
                    best_move_conflicts = new_conflicts

        # 4. Совершение хода
        if best_aspirating_move:
            # Приоритет аспирации
            move_to_make = best_aspirating_move
            current_conflicts = best_aspirating_conflicts
        elif best_move:
            # Иначе берем лучший не-табу
            move_to_make = best_move
            current_conflicts = best_move_conflicts
        else:
            # Если все ходы табу и нет аспирации, пропускаем (или берем случайный)
            # В данном случае, если best_move == None, мы застряли
            break 
            
        v, new_c, old_c = move_to_make
        
        # Применить ход
        current_coloring[v] = new_c
        
        # Добавить запрет на ОБРАТНЫЙ ход в табу-лист 
        tabu_list.append((v, old_c))

        # 5. Обновить лучшее решение
        if current_conflicts < best_conflicts:
            best_conflicts = current_conflicts
            best_coloring = list(current_coloring)

    return best_coloring, best_conflicts

def find_min_coloring(v_count, edges):
    """
    Главная функция: ищет минимальное k, уменьшая его с V до 1.
    """
    adj = build_adj_list(v_count, edges)
    
    final_coloring = []
    final_k = v_count

    # Начинаем с k = V и уменьшаем
    for k in range(v_count, 0, -1):
        print(f"Попытка с k = {k}...")
        coloring, conflicts = tabu_search_k_coloring(adj, k)
        
        if conflicts == 0:
            print(f"Успех! Найдена раскраска с 0 конфликтов.")
            final_coloring = coloring
            final_k = k
        else:
            print(f"Неудача. Мин. конфликтов: {conflicts}. Останавливаемся.")
            # Если не удалось найти с k, то k+1 было последним успешным
            break
            
    return final_k, final_coloring

# --- Запуск ---
print(f"Запуск Табу-поиска для графа V={V}, E={len(EDGES)}")
print(f"Итерации: {MAX_ITERATIONS}, Размер Табу: {TABU_SIZE}\n") # 

k_found, coloring_found = find_min_coloring(V, EDGES)

print("\n--- Результат ---")
print(f"Финальное число цветов (k): {k_found}") # 
print("Распределение цветов по вершинам:") # 
for i, color in enumerate(coloring_found):
    print(f"  Вершина {i}: Цвет {color}")