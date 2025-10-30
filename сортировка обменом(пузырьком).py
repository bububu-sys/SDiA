#сортировка обменом(пузырьком)

def bubble_sort(arr):
    n = len(arr)
    for i in range(n):
        # Внутренний цикл проходит по массиву, "всплывая" на одну позицию к концу
        for j in range(0, n - i - 1):
            # Если текущий элемент больше следующего, меняем местами
            if arr[j] > arr[j + 1]:
                arr[j], arr[j + 1] = arr[j + 1], arr[j]
    return arr

# пример использования
array = [63, 58, 34, 42, 17]
print(bubble_sort(array))
# вывод = 17, 34, 42, 58, 63