#Блинная сортировка

import random

def pancake_sort(arr):
    n = len(arr)
    for current_size in range(n, 1, -1):
        # Находим индекс максимального элемента в текущем диапазоне
        max_idx = arr.index(max(arr[:current_size]))
        # Переворачиваем подмассив, чтобы элемент оказался в нужной позиции
        if max_idx != current_size - 1:
            # Переворачиваем до max_idx
            arr[:max_idx + 1] = arr[:max_idx + 1][::-1]
            # Переворачиваем весь текущий сегмент
            arr[:current_size] = arr[:current_size][::-1]
    return arr

# Пример использования
array = [3, 6, 1, 5, 2, 4]
print(pancake_sort(array))