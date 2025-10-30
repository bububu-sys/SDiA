#Поиск скачками (Jump Search)

import math

def jump_search(arr, target):
    length = len(arr)
    step = int(math.sqrt(length))
    
    prev = 0
    # Ищем блок, в котором может находиться элемент
    while prev < length and arr[min(step, length) - 1] < target:
        prev = step
        step += int(math.sqrt(length))
        if prev >= length:
            return -1  # не найден
    
    # Линейный поиск внутри найденного блока
    while prev < length and arr[prev] < target:
        prev += 1
        if prev == min(step, length):
            return -1  # не найден
    
    if prev < length and arr[prev] == target:
        return prev
    return -1

# Пример использования
array = [1, 3, 5, 7, 9, 11, 13, 15]
index = jump_search(array, 9)
print(f"Индекс элемента 9: {index}")