#Тернарный поиск (Ternary Search)

def ternary_search(arr, target, left=0, right=None):
    if right is None:
        right = len(arr) - 1
    
    if left > right:
        return -1
    
    # Делим диапазон на три части
    third = (right - left) // 3
    mid1 = left + third
    mid2 = right - third
    
    # Проверяем середины
    if arr[mid1] == target:
        return mid1
    if arr[mid2] == target:
        return mid2
    
    # Рекурсивно ищем в нужной части
    if target < arr[mid1]:
        return ternary_search(arr, target, left, mid1 - 1)
    elif target > arr[mid2]:
        return ternary_search(arr, target, mid2 + 1, right)
    else:
        return ternary_search(arr, target, mid1 + 1, mid2 - 1)

# Пример использования
array = [1, 3, 5, 7, 9, 11, 13, 15]
index = ternary_search(array, 9)
print(f"Индекс элемента 9: {index}")
#вывод = Индекс элемента 9: 4
