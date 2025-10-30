# Поиск по Фибоначчи

def fibonacci_search(arr, target):
    n = len(arr)
    
    # Генерируем последовательность Фибоначчи, пока не превысим длину массива
    fib2, fib1 = 0, 1
    while fib1 < n:
        fib2, fib1 = fib1, fib2 + fib1
    
    offset = -1  # смещение маяка
    
    while fib1 > 1:
        # Проверяем индекс, который на `fib2` позиций от смещения
        i = min(offset + fib2, n - 1)
        
        if arr[i] == target:
            return i  # нашли
        elif arr[i] < target:
            # Двигаемся к правой части
            fib1, fib2 = fib2, fib1 - fib2
            offset = i
        else:
            # Двигаемся к левой части
            fib1, fib2 = fib2, fib1 - fib2
    
    # Проверка последнего элемента
    if fib2 and offset + 1 < n and arr[offset + 1] == target:
        return offset + 1
    return -1  # не нашли

# пример использования
array = [1, 3, 5, 7, 9, 11, 13, 15, 17, 19]
target_value = 7

index = fibonacci_search(array, target_value)

if index != -1:
    print(f"Элемент {target_value} найден на позиции: {index}")
else:
    print(f"Элемент {target_value} не найден в массиве.")