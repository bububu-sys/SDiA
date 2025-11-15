# Бинарный поиск

def binary_search(arr, target):
    left = 0
    right = len(arr) - 1
    
    while left <= right:
        mid = (left + right) // 2
        if arr[mid] == target:
            return mid  # элемент найден
        elif arr[mid] < target:
            left = mid + 1  # искать в правой части
        else:
            right = mid - 1  # искать в левой части
    return -1  # элемент не найден

# пример использования
array = [11, 12, 22, 25, 34, 64, 90]
target_value = 22

index = binary_search(array, target_value)

if index != -1:
    print(f"Элемент {target_value} найден на позиции: {index}")
else:
    print(f"Элемент {target_value} не найден в массиве.")