# Последовательный(линейный) поиск

def linear_search(arr, target):
    for index, value in enumerate(arr):
        if value == target:
            return index  # нашли, возвращаем индекс
    return -1  # не нашли, возвращаем -1

# пример использования
array = [64, 34, 25, 12, 22, 11, 90]
target_value = 22

index = linear_search(array, target_value)

if index != -1:
    print(f"Элемент {target_value} найден на позиции: {index}")
else:
    print(f"Элемент {target_value} не найден в массиве.")