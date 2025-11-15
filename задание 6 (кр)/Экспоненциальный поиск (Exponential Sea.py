#Экспоненциальный поиск (Exponential Search)

def exponential_search(arr, target):
    if len(arr) == 0:
        return -1
    # Начинаем с первого элемента
    if arr[0] == target:
        return 0
    
    # Экспоненциальный рост индекса
    index = 1
    while index < len(arr) and arr[index] <= target:
        index *= 2
    
    # Используем бинарный поиск в диапазоне от предыдущего до текущего
    left = index // 2
    right = min(index, len(arr) - 1)

    return binary_search(arr, left, right, target)

def binary_search(arr, left, right, target):
    while left <= right:
        mid = left + (right - left) // 2
        if arr[mid] == target:
            return mid
        elif arr[mid] < target:
            left = mid + 1
        else:
            right = mid - 1
    return -1

# Пример использования
array = [1, 3, 5, 7, 9, 11, 13, 15]
index = exponential_search(array, 9)
print(f"Индекс элемента 9: {index}")